package com.pb.dashboard.vitrina.trends;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.core.country.CountryUA;
import com.pb.dashboard.vitrina.core.types.Period;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.statistics.DataCollector;
import com.pb.dashboard.vitrina.statistics.Utilities;
import com.pb.dashboard.vitrina.statistics.byday.DayHourMetrics;
import com.pb.dashboard.vitrina.statistics.byday.SelectionDate;
import com.pb.dashboard.vitrina.trends.charts.ChartEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrendsManager {

    private Date date;
    private int prevHour;
    private CountryI country;
    private ConfigManager manager;
    private ASEDBManager dm = ASEDBManager.getInstance();

    private TrendsInfoPanelManager infoPanelManager;

    private SelectionDate currentDate;
    private SelectionDate yesterdayDate;

    public TrendsManager(TrendsInfoPanelManager infoPanelManager) {
        this.infoPanelManager = infoPanelManager;
    }

    public void setConfigManager(ConfigManager manager) {
        this.manager = manager;
    }

    public void collectMetrics() {
        date = new Date();
        prevHour = getPrevHour();
        //List<DayHourMetrics> todayMetrics = randMainData();        //test
        //List<DayHourMetrics> yesterdayMetrics = randCompData();    //test
        List<DayHourMetrics> todayMetrics = generateData(date);
        List<DayHourMetrics> yesterdayMetrics = generateData(getYesterdayDate(date));
        currentDate = buildSelectionDate(date, todayMetrics);
        yesterdayDate = buildSelectionDate(getYesterdayDate(date), yesterdayMetrics);
        countMetrics();
    }

    private void countMetrics() {
        Map<ChartEnum, Integer[]> map = new HashMap<ChartEnum, Integer[]>();
        List<Integer[]> list = new ArrayList<Integer[]>();
        for (StatEnum type : Utilities.types) {

            currentDate.setByType(type);
            yesterdayDate.setByType(type);

            List<Integer[]> currentDateValues = currentDate.getData();

            Integer[] currentHourValues = currentDateValues.get(currentDateValues.size() - prevHour);
            Integer[] prevHourValues = currentDateValues.get(currentDateValues.size() - (prevHour - 1));

            /* SubPanel Data */

            Integer[] subPanelHour = new Integer[3];
            subPanelHour[0] = currentHourValues[0];
            subPanelHour[1] = prevHourValues[0];
            list.add(subPanelHour);
            Integer[] subPanelDay = new Integer[3];
            int first = countTotal(currentDateValues, 0);
            int second = countTotal(yesterdayDate.getData(), 0);
            subPanelDay[0] = first;
            subPanelDay[1] = second;
            list.add(subPanelDay);

            /* Charts Data */

            Integer[] firstHourChart = {currentHourValues[1], currentHourValues[2], currentHourValues[3]};
            Integer[] secondHourChart = {currentHourValues[5], currentHourValues[4]};
            Integer[] firstDayChart = {countTotal(currentDateValues, 1), countTotal(currentDateValues, 2),
                    countTotal(currentDateValues, 3)};
            Integer[] secondDayChart = {countTotal(currentDateValues, 5), countTotal(currentDateValues, 4)};

            if (type == StatEnum.KASSA) {
                map.put(ChartEnum.PAYDESK_HOUR_CASH, firstHourChart);
                map.put(ChartEnum.PAYDESK_HOUR_PHYS, secondHourChart);
                map.put(ChartEnum.PAYDESK_DAY_CASH, firstDayChart);
                map.put(ChartEnum.PAYDESK_DAY_PHYS, secondDayChart);
            } else if (type == StatEnum.BASS) {
                map.put(ChartEnum.TERMINAL_HOUR_CASH, firstHourChart);
                map.put(ChartEnum.TERMINAL_HOUR_PHYS, secondHourChart);
                map.put(ChartEnum.TERMINAL_DAY_CASH, firstDayChart);
                map.put(ChartEnum.TERMINAL_DAY_PHYS, secondDayChart);
            } else if (type == StatEnum.P24) {
                map.put(ChartEnum.P24_HOUR_CASH, firstHourChart);
                map.put(ChartEnum.P24_HOUR_PHYS, secondHourChart);
                map.put(ChartEnum.P24_DAY_CASH, firstDayChart);
                map.put(ChartEnum.P24_DAY_PHYS, secondDayChart);
            } else if (type == StatEnum.P3700) {
                map.put(ChartEnum.L3700_HOUR_CASH, firstHourChart);
                map.put(ChartEnum.L3700_HOUR_PHYS, secondHourChart);
                map.put(ChartEnum.L3700_DAY_CASH, firstDayChart);
                map.put(ChartEnum.L3700_DAY_PHYS, secondDayChart);
            }
        }
        infoPanelManager.setChartSeries(map);
        infoPanelManager.setSubPanelsData(list);
    }

    private Integer countTotal(List<Integer[]> totals, int index) {
        int count = 0;
        for (Integer[] ints : totals) {
            count += ints[index];
        }
        return count;
    }

    private SelectionDate buildSelectionDate(Date date, List<DayHourMetrics> metrics) {
        DataCollector collector = collectAllTypes(sortByHour(metrics));
        Map<StatEnum, List<Integer[]>> allTypes = (Map<StatEnum, List<Integer[]>>) collector.getData();
        Integer[] totalsByType = (Integer[]) collector.getTotal();
        return new SelectionDate(date, allTypes, totalsByType);
    }

    private Map<Period, List<DayHourMetrics>> sortByHour(List<DayHourMetrics> list) {
        Map<Period, List<DayHourMetrics>> metricsByHour = new HashMap<Period, List<DayHourMetrics>>();
        for (Period period : Period.values()) {
            if (period.getId() > prevHour) continue;
            for (DayHourMetrics metrics : list) {
                if (metrics.getMetricHour() == period.getId() - 1) {
                    if (metricsByHour.get(period) == null) metricsByHour.put(period, new ArrayList<DayHourMetrics>());
                    metricsByHour.get(period).add(metrics);
                }
            }
        }
        return metricsByHour;
    }

    private int getPrevHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("k");
        return Integer.parseInt(sdf.format(date)) - 1;
    }

    private DataCollector collectAllTypes(Map<Period, List<DayHourMetrics>> metricsByHour) {

        int[] kassaTypes = {country.getKassaCash(), country.getKassaNonCash(), country.getKassaDebt(),
                country.getKassaFizLic(), country.getKassaUrLic()};
        int[] bassTypes = {country.getBassCash(), country.getBassNonCash(), country.getBassDebt(),
                country.getBassFizLic(), country.getBassUrLic()};
        int[] p24Types = {-1, country.getP24NonCash(), country.getP24Debt(), country.getP24FizLic(),
                country.getP24UrLic()};
        int[] l3700Types = {-1, country.get3700NonCash(), country.get3700Debt(), country.get3700FizLic(),
                country.get3700UrLic()};
        List<int[]> index = new ArrayList<int[]>(4);

        index.add(kassaTypes);
        index.add(bassTypes);
        index.add(p24Types);
        index.add(l3700Types);

        return Utilities.collectAllTypes(metricsByHour, index);
    }

    private Date getYesterdayDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }


    private void reloadCountry() {
        switch (manager.getLang()) {
            case UA:
                country = new CountryUA();
                break;
            case RU:
                country = new CountryRU();
                break;
            case GEO:
                country = new CountryGEO();
                break;
            default:
                country = new CountryUA();
                break;
        }
    }

    private int currentTime(Date date) {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
        return Integer.parseInt(df.format(date));
    }

    private List<DayHourMetrics> generateData(Date date) {
        reloadCountry();
        return dm.getByDayMetrics(QueryInf.CURRENT_DAY_METRICS, currentTime(date));
    }
}