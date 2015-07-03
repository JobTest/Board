package com.pb.dashboard.vitrina.timeline;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.core.country.CountryUA;
import com.pb.dashboard.vitrina.statistics.byday.DayMetrics;
import com.vaadin.addon.timeline.Timeline;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimelineManager implements Serializable{

    private ASEDBManager dbManager = ASEDBManager.getInstance();
    protected CountryI country;
    protected ConfigManager manager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private int limit;
    private Map<String, List<DayMetrics>> metricsByDate;

    public TimelineManager(ConfigManager manager) {
        this.manager = manager;
    }

    public List<Container.Indexed> collectMetrics() {
        List<DayMetrics> metrics = updateMetrics();
        metricsByDate = filterByDate(metrics);
        return collectAllChannels(metricsByDate);
    }

    private List<DayMetrics> updateMetrics() {
        reloadCountry();
        return dbManager.getSumMetricByPeriod(QueryInf.METRIC_SUM_BY_PERIOD, getIntDate(PeriodType.END), getIntDate(PeriodType.BEGIN));
    }

    private List<Container.Indexed> collectAllChannels(Map<String, List<DayMetrics>> byDate) {
        Map<Date, Integer> paydesk = filterByChannel(byDate, country.getKassaCash(), country.getKassaNonCash());
        Map<Date, Integer> terminal = filterByChannel(byDate, country.getBassCash(), country.getBassNonCash());
        Map<Date, Integer> p24 = filterByChannel(byDate, -1, country.getP24NonCash());
        Map<Date, Integer> l3700 = filterByChannel(byDate, -1, country.get3700NonCash());

        limit = Collections.max(paydesk.values()) + Collections.max(terminal.values()) + Collections.max(p24.values())
                + Collections.max(l3700.values());

        return getContainers(paydesk, terminal, p24, l3700);
    }

    private Map<String, List<DayMetrics>> filterByDate(List<DayMetrics> metrics) {
        Map<String, List<DayMetrics>> byDate = initMap();
        for (DayMetrics m : metrics) {
            String date = String.valueOf(m.getDate());
            List<DayMetrics> dayMetricses = byDate.get(date);
            if (dayMetricses != null) {
                dayMetricses.add(m);
            }
        }
        return byDate;
    }

    private Map<String, List<DayMetrics>> initMap() {
        Map<String, List<DayMetrics>> map = new HashMap<String, List<DayMetrics>>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date today = new Date();
        while (cal.getTime().before(today)) {
            map.put(sdf.format(cal.getTime()), new ArrayList<DayMetrics>());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }

    private Map<Date, Integer> filterByChannel(Map<String, List<DayMetrics>> byDate, int cash, int noCash) {
        Map<Date, Integer> map = new TreeMap<Date, Integer>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date today = new Date();
        while (cal.getTime().before(today)) {
            String date = sdf.format(cal.getTime());
            List<DayMetrics> metrics = byDate.get(date);
            Integer total = 0;
            if (metrics != null) {
                for (DayMetrics m : metrics) {
                    if (m.getMetricKey() == cash || m.getMetricKey() == noCash) total += m.getMetricValue();
                }
            }
            map.put(toMidnight(cal.getTime()), total);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }

    private List<Container.Indexed> getContainers(Map<Date, Integer> paydesk, Map<Date, Integer> terminal,
                                                  Map<Date, Integer> p24, Map<Date, Integer> l3700) {

        List<Container.Indexed> list = new ArrayList<Container.Indexed>(4);
        Container.Indexed paydeskCont = createGraphDataSource(paydesk);
        Container.Indexed terminalCont = createGraphDataSource(terminal);
        Container.Indexed p24Cont = createGraphDataSource(p24);
        Container.Indexed l3700Cont = createGraphDataSource(l3700);
        list.add(paydeskCont);
        list.add(terminalCont);
        list.add(p24Cont);
        list.add(l3700Cont);
        return list;
    }

    public Container.Indexed createGraphDataSource(Map<Date, Integer> metrics) {
        Container.Indexed container = new IndexedContainer();
        container.addContainerProperty(Timeline.PropertyId.TIMESTAMP, Date.class, null);
        container.addContainerProperty(Timeline.PropertyId.VALUE, Integer.class, 0d);
        for (Map.Entry<Date, Integer> entry : metrics.entrySet()) {
            Item item = container.addItem(entry.getKey());
            item.getItemProperty(Timeline.PropertyId.TIMESTAMP).setValue(entry.getKey());
            item.getItemProperty(Timeline.PropertyId.VALUE).setValue(entry.getValue());
        }
        return container;
    }

    public List<int[]> getInfoPanelData(Date dateFrom, Date dateTo) {
        Calendar from = Calendar.getInstance();
        from.setTime(dateFrom);
        Calendar to = Calendar.getInstance();
        to.setTime(dateTo);

        List<Integer> paydesk = new ArrayList<Integer>();
        List<Integer> terminal = new ArrayList<Integer>();
        List<Integer> p24 = new ArrayList<Integer>();
        List<Integer> l3700 = new ArrayList<Integer>();
        List<Integer> phys = new ArrayList<Integer>();
        List<Integer> jur = new ArrayList<Integer>();
        List<Integer> cash = new ArrayList<Integer>();
        List<Integer> noCash = new ArrayList<Integer>();
        List<Integer> debt = new ArrayList<Integer>();

        while (from.getTime().before(to.getTime())) {
            int paydeskCount = 0,
                    terminalCount = 0,
                    p24Count = 0,
                    l3700Count = 0,
                    cashCount = 0,
                    noCashCount = 0,
                    physCount = 0,
                    jurCount = 0,
                    debtCount = 0;
            String date = sdf.format(from.getTime());
            List<DayMetrics> metrics = metricsByDate.get(date);
            if (metrics != null) {
                for (DayMetrics m : metrics) {
                    int key = m.getMetricKey();
                    int value = m.getMetricValue();
                    if (key == country.getKassaCash()) {
                        cashCount += value;
                        paydeskCount += value;
                    } else if (key == country.getKassaNonCash()) {
                        noCashCount += value;
                        paydeskCount += value;
                    } else if (key == country.getBassCash()) {
                        cashCount += value;
                        terminalCount += value;
                    } else if (key == country.getBassNonCash()) {
                        noCashCount += value;
                        terminalCount += value;
                    } else if (key == country.getP24NonCash()) {
                        p24Count += value;
                        noCashCount += value;
                    } else if (key == country.get3700NonCash()) {
                        l3700Count += value;
                        noCashCount += value;
                    } else if (key == country.getKassaFizLic() || key == country.getBassFizLic() ||
                            key == country.getP24FizLic() || key == country.get3700FizLic()) {
                        physCount += value;
                    } else if (key == country.getKassaUrLic() || key == country.getBassUrLic() ||
                            key == country.getP24UrLic() || key == country.get3700UrLic()) {
                        jurCount += value;
                    } else if (key == country.getKassaDebt() || key == country.getBassDebt() ||
                            key == country.getP24Debt() || key == country.get3700Debt()) {
                        debtCount += value;
                    }
                }
            }
            paydesk.add(paydeskCount);
            terminal.add(terminalCount);
            p24.add(p24Count);
            l3700.add(l3700Count);
            cash.add(cashCount);
            noCash.add(noCashCount);
            phys.add(physCount);
            jur.add(jurCount);
            debt.add(debtCount);
            from.add(Calendar.DAY_OF_MONTH, 1);
        }
        return processByChannel(paydesk, terminal, p24, l3700, phys, jur, cash, noCash, debt);
    }

    private List<int[]> processByChannel(List<Integer>... lists) {
        List<int[]> list = new ArrayList<int[]>(9);
        for (List<Integer> l : lists) {
            int sum = 0;
            for (Integer value : l) {
                sum += value;
            }
            int[] arr = {0, 0, 0};
            if (!l.isEmpty()) {
                arr[0] = Collections.min(l);
                arr[1] = Collections.max(l);
                arr[2] = sum / l.size();
            }
            list.add(arr);
        }
        return list;
    }

    private int getIntDate(PeriodType periodType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (PeriodType.END.equals(periodType)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            return Integer.parseInt(sdf.format(cal.getTime()));
        }

        return Integer.parseInt(sdf.format(new Date()));
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

    private Date toMidnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public int getLimit() {
        return limit;
    }


    private enum PeriodType {
        BEGIN, END
    }
}