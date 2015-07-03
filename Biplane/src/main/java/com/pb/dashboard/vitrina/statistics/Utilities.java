package com.pb.dashboard.vitrina.statistics;

import com.pb.dashboard.vitrina.core.types.Period;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.byday.DayHourMetrics;
import com.pb.dashboard.vitrina.statistics.byday.DayMetrics;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;


public class Utilities {

    public static final int TABLE_COLUMNS = 6;
    public static StatEnum[] types = {StatEnum.KASSA, StatEnum.BASS, StatEnum.P24, StatEnum.P3700};

    private static final Logger LOG = Logger.getLogger(Utilities.class);
    private static final int[] KEYS = {1, 2, 3, 10, 11, 12, 17, 18, 23, 24, 39, 40, 41, 42, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72,
            73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99};

    public static Map<Period, List<DayHourMetrics>> sortMetricsByHour(List<DayHourMetrics> list) {
        Map<Period, List<DayHourMetrics>> metricsByHour = new HashMap<Period, List<DayHourMetrics>>();
        for (Period period : Period.values()) {
            for (DayHourMetrics metrics : list) {
                if (metrics.getMetricHour() == period.getId() - 1) {
                    if (metricsByHour.get(period) == null) metricsByHour.put(period, new ArrayList<DayHourMetrics>());
                    metricsByHour.get(period).add(metrics);
                }
            }
        }
        return metricsByHour;
    }

    public static DataCollector collectAllTypes(List<DayHourMetrics> dayHourMetrics, List<int[]> index) {
        return collectAllTypes(sortMetricsByHour(dayHourMetrics), index);
    }

    public static DataCollector collectAllTypes(Map<Period, List<DayHourMetrics>> metricsByHour, List<int[]> index) {
        int[] kassaTypes = index.get(0);
        int[] bassTypes = index.get(1);
        int[] p24Types = index.get(2);
        int[] l3700Types = index.get(3);

        Map<StatEnum, List<Integer[]>> map = initZeroMap();
        Integer[] totalsByType = new Integer[types.length];

        DataCollector kassaCollector = collectBiplaneData(metricsByHour, kassaTypes);
        List<Integer[]> kassa = (List<Integer[]>) kassaCollector.getData();
        totalsByType[0] = (Integer) kassaCollector.getTotal();

        DataCollector bassCollector = collectBiplaneData(metricsByHour, bassTypes);
        List<Integer[]> bass = (List<Integer[]>) bassCollector.getData();
        totalsByType[1] = (Integer) bassCollector.getTotal();

        DataCollector p24Collector = collectBiplaneData(metricsByHour, p24Types);
        List<Integer[]> p24 = (List<Integer[]>) p24Collector.getData();
        totalsByType[2] = (Integer) p24Collector.getTotal();

        DataCollector l3700Collector = collectBiplaneData(metricsByHour, l3700Types);
        List<Integer[]> l3700 = (List<Integer[]>) l3700Collector.getData();
        totalsByType[3] = (Integer) l3700Collector.getTotal();

        mergeLists(map.get(StatEnum.KASSA), kassa);
        mergeLists(map.get(StatEnum.BASS), bass);
        mergeLists(map.get(StatEnum.P24), p24);
        mergeLists(map.get(StatEnum.P3700), l3700);

        return new DataCollector(map, totalsByType);
    }

    public static DataCollector collectBiplaneData(Map<Period, List<DayHourMetrics>> metricsByHour, int[] types) {
        int cash = types[0],
                noCash = types[1],
                debt = types[2],
                phys = types[3],
                jur = types[4];
        List<Integer[]> list = new ArrayList<Integer[]>();
        Integer total = 0;
        for (Period period : Period.values()) {
            List<DayHourMetrics> dayHourMetrics = metricsByHour.get(period);
            int cashTotal = 0,
                    noCashTotal = 0,
                    debtTotal = 0,
                    physTotal = 0,
                    jurTotal = 0;
            if (dayHourMetrics != null) {
                for (DayHourMetrics metrics : dayHourMetrics) {
                    if (metrics.getMetricKey() == cash) cashTotal += metrics.getMetricValue();
                    if (metrics.getMetricKey() == noCash) noCashTotal += metrics.getMetricValue();
                    if (metrics.getMetricKey() == debt) debtTotal += metrics.getMetricValue();
                    if (metrics.getMetricKey() == phys) physTotal += metrics.getMetricValue();
                    if (metrics.getMetricKey() == jur) jurTotal += metrics.getMetricValue();
                }
            }
            list.add(new Integer[]{cashTotal + noCashTotal, cashTotal, noCashTotal, debtTotal, physTotal, jurTotal});
            total += cashTotal + noCashTotal;
        }
        return new DataCollector(list, total);
    }

    public static List<Integer[]> mergeAllTypes(Map<StatEnum, List<Integer[]>> map) {
        List<Integer[]> result = initZeroList();
        for (StatEnum type : types) {
            mergeLists(result, map.get(type));
        }
        return result;
    }

    public static void mergeLists(List<Integer[]> first, List<Integer[]> second) {
        if (first.size() != second.size()) {
            LOG.error("error in mergeLists() " + first.size() + " : " + second.size());
            return;
        }
        for (int i = 0; i < first.size(); i++) {
            mergeArrays(first.get(i), second.get(i));
        }
    }

    private static void mergeArrays(Integer[] first, Integer[] second) {
        if (first.length != second.length) {
            LOG.error("error in mergeArrays()");
            return;
        }
        for (int i = 0; i < first.length; i++) {
            first[i] += second[i];
        }
    }

    private static Map<StatEnum, List<Integer[]>> initZeroMap() {
        Map<StatEnum, List<Integer[]>> map = new HashMap<StatEnum, List<Integer[]>>();
        for (StatEnum type : types) {
            map.put(type, initZeroList());
        }
        return map;
    }

    private static List<Integer[]> initZeroList() {
        List<Integer[]> list = new ArrayList<Integer[]>();
        for (int j = 0; j < Period.values().length; j++) {
            list.add(initZeroArr());
        }
        return list;
    }

    private static Integer[] initZeroArr() {
        Integer[] arr = new Integer[TABLE_COLUMNS];
        Arrays.fill(arr, 0);
        return arr;
    }

    public static List<Object[]> convertToTableData(List<Integer[]> list) {
        List<Object[]> result = new ArrayList<Object[]>();
        int i = 0;
        for (Period period : Period.values()) {
            Integer[] ints = list.get(i);
            Object[] strings = new Object[TABLE_COLUMNS + 1]; // +1 column for time period
            strings[0] = period.getPeriod();
            for (int j = 0; j < ints.length; j++) {
                strings[j + 1] = String.format("%,d", ints[j]);
            }
            result.add(strings);
            i++;
        }
        return result;
    }

    public static List<Object[]> convertToTableData(List<Integer[]> main, List<Integer[]> compared) {
        List<Object[]> result = new ArrayList<Object[]>();
        int i = 0;
        for (Period period : Period.values()) {
            Integer[] ints = main.get(i);
            Integer[] intsToCompare = compared.get(i);
            Object[] strings = new Object[TABLE_COLUMNS + 1]; // +1 column for time period
            strings[0] = period.getPeriod();
            for (int j = 0; j < ints.length; j++) {
                String value;
                double diff = (double) ints[j] - intsToCompare[j];
                if (diff < 0) {
                    double percentage = (Math.abs(diff) / intsToCompare[j]) * 100;
                    value = String.format("%,d (-%.2f%%)", ints[j], percentage);
                } else {
                    value = String.format("%,d", ints[j]);
                }
                strings[j + 1] = value;
            }
            result.add(strings);
            i++;
        }
        return result;
    }

    public static String convertComparativeTotal(int main, int compared) {
        double diff = (double) main - compared;
        double divisor = (diff < 0) ? compared : main;
        double percentage = (Math.abs(diff) / divisor) * 100;
        String sign = (main > compared) ? "+" : (main < compared) ? "-" : "";
        return String.format("%,d (%s%.2f%%)", main, sign, percentage);
    }


    public static List<DayHourMetrics> getZeroDayHourMetrics() {
        List<DayHourMetrics> list = new ArrayList<DayHourMetrics>();
        for (Period period : Period.values()) {
            for (int key : KEYS) {
                DayHourMetrics dayHourMetrics = new DayHourMetrics(key, period.getId() - 1, 0);
                list.add(dayHourMetrics);
            }
        }
        return list;
    }

    /* Random data generators */

    public static List<DayHourMetrics> getRandDayHourMetrics(int inter) {
        List<DayHourMetrics> list = new ArrayList<DayHourMetrics>();
        Random rand = new Random();
        for (int i = 0; i < inter; i++) {
            for (Period period : Period.values()) {
                //if (period == Period.H2 || period == Period.H23) continue;
                for (int key : KEYS) {
                    DayHourMetrics dayHourMetrics = new DayHourMetrics(key, period.getId() - 1, rand.nextInt(1000));
                    list.add(dayHourMetrics);
                }
            }
        }
        Collections.shuffle(list);
        return list;
    }

    public static List<DayMetrics> getRandDayMetrics(int iter) {
        List<DayMetrics> list = new ArrayList<DayMetrics>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Random rand = new Random();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date today = new Date();

        while (cal.getTime().before(today)) {
            for (int i = 0; i < iter; i++) {
                for (int key : KEYS) {
                    String date = sdf.format(cal.getTime());
                    DayMetrics metrics = new DayMetrics(key, "", rand.nextInt(100000), Integer.parseInt(date));
                    list.add(metrics);
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Collections.shuffle(list);
        return list;
    }

    public static List<Metrics> getRandMetrics(int iter) {
        List<Metrics> list = new ArrayList<Metrics>();
        Random rand = new Random();
        for (int i = 0; i < iter; i++) {
            for (int key : KEYS) {
                Metrics metrics = new Metrics(key, "", rand.nextInt(1000));
                list.add(metrics);
            }
        }
        Collections.shuffle(list);
        return list;
    }

}
