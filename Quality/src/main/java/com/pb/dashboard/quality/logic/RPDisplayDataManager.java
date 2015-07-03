package com.pb.dashboard.quality.logic;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.quality.chart.ChartBuilder;
import com.pb.dashboard.quality.chart.ChartDataHolder;
import com.pb.dashboard.quality.holder.PaymentsQualityItem;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.PaymentType;
import com.pb.dashboard.quality.types.Range;
import com.pb.dashboard.quality.types.ValueType;
import com.pb.dashboard.quality.view.table.RPTableDataHolder;
import com.pb.dashboard.quality.view.tabsheet.RPTabsheetBuilder;
import com.pb.dashboard.quality.view.tabsheet.RPTabsheetDataHolder;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.ui.TabSheet;

import java.util.*;

public class RPDisplayDataManager extends DisplayDataManager {

    private ChartBuilder chartBuilder;
    private RPTabsheetBuilder rpTabsheetBuilder = new RPTabsheetBuilder();

    private static final int CAUSES_LIMIT = 7;
    private static final int TOTAL_COLUMNS = CAUSES_LIMIT + 4; // number of table columns (dynamic + 4 static)

    public RPDisplayDataManager(ChartBuilder chartBuilder) {
        this.chartBuilder = chartBuilder;
    }

    public void initReport() {
        super.initDates();
        super.loadMainChartDataSeries(ValueType.PAYMENTS, ValueType.REJECTED_PAYMENTS);
        chartBuilder.setRejectedPaymentsChartSeries(super.mainChartPercentage);
    }

    public TabSheet initTabsheet() {
        return rpTabsheetBuilder.getTabSheet(Channel.OFFICE, createOfficeReport(Range.DAY,
                new Date[]{super.dateFrom, super.dateTo}));
    }

    public TabSheet displayDetailedData(Channel channel, Range range, Date[] dates) {
        super.setDates(dates);
        return rpTabsheetBuilder.getTabSheet(channel, getTabsheetDataHolder(channel, range, dates));
    }

    private RPTabsheetDataHolder getTabsheetDataHolder(Channel channel, Range range, Date[] dates) {
        if (channel == Channel.OFFICE) return createOfficeReport(range, dates);
        if (channel == Channel.BIPLAN) return createBiplanReport(range, dates);
        if (channel == Channel.L3700) return create3700Report(range, dates);
        if (channel == Channel.TERMINAL) return createTCOReport(range, dates);
        return createP24Report(range, dates);
    }

    private RPTabsheetDataHolder createOfficeReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.EXPRESS};
        return computeStats(range, dates, Channel.OFFICE, types);
    }

    private RPTabsheetDataHolder createBiplanReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.PROCEEDS, PaymentType.PHYS};
        return computeStats(range, dates, Channel.BIPLAN, types);
    }

    private RPTabsheetDataHolder create3700Report(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.EXPRESS};
        return computeStats(range, dates, Channel.L3700, types);
    }

    private RPTabsheetDataHolder createP24Report(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.ALL, PaymentType.MOBILE, PaymentType.BIPLAN, PaymentType.P2P};
        return computeStats(range, dates, Channel.P24, types);
    }

    private RPTabsheetDataHolder createTCOReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.ALL, PaymentType.MOBILE, PaymentType.BIPLAN};
        return computeStats(range, dates, Channel.TERMINAL, types);
    }

    //todo: computeStats() needs refactoring
    private RPTabsheetDataHolder computeStats(Range range, Date[] dates, Channel channel, PaymentType[] paymentTypes) {
        List<ChartDataHolder> listOfChartPercentHolders = new ArrayList<ChartDataHolder>();
        List<ChartDataHolder> listOfChartQuantityHolders = new ArrayList<ChartDataHolder>();
        List<RPTableDataHolder> listOfTableHolders = new ArrayList<RPTableDataHolder>();
//        Statement stmt = super.dbManager.getStatement(); // open DB connection and get sql Statement Object
        for (PaymentType currentType : paymentTypes) {
            String[] causes = calculateRejectCauses(channel, currentType, dates); // get rejection causes
            List<String> periodCategories = new ArrayList<String>(); // chart categories - table categories
            Map<String, List<Number>> chartDataPercentage = createDataSeriesMap(causes); // Chart DataSeries temp holder (percent)
            Map<String, List<Number>> chartDataQuantity = createDataSeriesMap(causes); // Chart DataSeries temp holder (quantity)
            List<Object[]> rows = new ArrayList<Object[]>(); // Table Rows holder
            // init grand totals for table footer
            double totalPayments = 0,
                    totalRejected = 0,
                    totalClients = 0;
            Map<String, Double> rejections = getRejectionsMap(causes);
            double totalRejections = 0;
            Map<String, Double> byDayMapPayments = null;
            Map<String, Double> byDayMapRejected = null;
            Map<String, Double> byDayMapClients = null;
            if (range == Range.DAY) {
                byDayMapPayments = super.dbManager.getValueByDate(super.bank, ValueType.PAYMENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapRejected = super.dbManager.getValueByDate(super.bank, ValueType.REJECTED_PAYMENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapClients = super.dbManager.getValueByDate(super.bank, ValueType.UNIQUE_CLIENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
            }
            Map<String, List<PaymentsQualityItem>> rejectedItems = sortByDate(super.dbManager.getItems(super.bank,
                    ValueType.REJECTED_PAYMENTS, channel, currentType,
                    super.sdf.format(dateFrom), super.sdf.format(dateTo)));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates[0]);
            while (cal.getTime().before(dates[1]) || cal.getTime().equals(dates[1])) {
                Date to = (range == Range.DAY) ? cal.getTime() : super.adjustDateByRange(cal.getTime(), range);
                String dateFrom = super.sdf.format(cal.getTime());
                String dateTo = sdf.format(to);
                String period = super.getPeriod(range, new Date[]{cal.getTime(), to});
                periodCategories.add(period);
                double paymentVal = 0,
                        rejectedVal = 0,
                        clientsVal = 0;
                if (byDayMapPayments != null) {
                    if (byDayMapPayments.get(dateFrom) != null) paymentVal = byDayMapPayments.get(dateFrom);
                    if (byDayMapRejected.get(dateFrom) != null) rejectedVal = byDayMapRejected.get(dateFrom);
                    if (byDayMapClients.get(dateFrom) != null) clientsVal = byDayMapClients.get(dateFrom);
                } else {
                    paymentVal = super.dbManager.getSumValue(super.bank, ValueType.PAYMENTS, channel, currentType,
                            dateFrom, dateTo);
                    rejectedVal = super.dbManager.getSumValue(super.bank, ValueType.REJECTED_PAYMENTS, channel, currentType,
                            dateFrom, dateTo);
                    clientsVal = super.dbManager.getSumValue(super.bank, ValueType.UNIQUE_CLIENTS, channel, currentType,
                            dateFrom, dateTo);
                }
                totalPayments += paymentVal;
                totalRejected += rejectedVal;
                totalClients += clientsVal;
                // Table data for static columns
                double[] values = {paymentVal, rejectedVal, clientsVal};
                Object[] row = getRow(period, values);

                List<PaymentsQualityItem> rejectedPayments = getRejectedPaymentsByPeriod(rejectedItems, cal, to);
                totalRejections = calculateRejectionPercent(causes, chartDataPercentage, chartDataQuantity, rejections,
                        totalRejections, row, rejectedPayments);
                rows.add(row);
                addTime(cal, range);
            }
            // Prepare xAxis Category names (dates)
            String[] xAxisCategories = periodCategories.toArray(new String[periodCategories.size()]);
            // Preparing chart percentage data
            listOfChartPercentHolders.add(convertToChartDataHolder(xAxisCategories, causes, chartDataPercentage));
            // Preparing chart quantity data
            listOfChartQuantityHolders.add(convertToChartDataHolder(xAxisCategories, causes, chartDataQuantity));
            // Preparing table data
            double[] totals = {totalPayments, totalRejected, totalClients, totalRejections};
            listOfTableHolders.add(convertToTableDataHolder(causes, totals, rejections, rows));
        }
        return new RPTabsheetDataHolder(listOfChartPercentHolders, listOfChartQuantityHolders, listOfTableHolders);
    }

    private List<PaymentsQualityItem> getRejectedPaymentsByPeriod(Map<String, List<PaymentsQualityItem>> rejectedItems,
                                                                  Calendar cal, Date to) {
        List<PaymentsQualityItem> rejectedPayments = new ArrayList<PaymentsQualityItem>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cal.getTime());
        while (calendar.getTime().before(to) || calendar.getTime().equals(to)) {
            if (rejectedItems.get(super.sdf.format(calendar.getTime())) != null)
                rejectedPayments.addAll(rejectedItems.get(super.sdf.format(calendar.getTime())));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return rejectedPayments;
    }

    private Map<String, List<PaymentsQualityItem>> sortByDate(List<PaymentsQualityItem> items) {
        Map<String, List<PaymentsQualityItem>> result = new HashMap<String, List<PaymentsQualityItem>>();
        for (PaymentsQualityItem item : items) {
            if (result.get(item.getDate()) == null) result.put(item.getDate(), new ArrayList<PaymentsQualityItem>());
            result.get(item.getDate()).add(item);
        }
        return result;
    }

    private Object[] getRow(String period, double[] values) {
        Object[] row = new Object[11]; // number of table columns
        row[0] = period;
        row[1] = String.format("%,d", (int) values[0]);
        row[2] = String.format("%,d", (int) values[2]);
        double percentVal = (values[0] > 0 && values[1] > 0) ? ((values[1] / values[0]) * 100) : 0;
        row[3] = String.format("%.1f", percentVal);
        return row;
    }

    private void addTime(Calendar cal, Range range) {
        if (range == Range.WEEK) cal.add(Calendar.DAY_OF_WEEK, 7);
        if (range == Range.MONTH) cal.add(Calendar.MONTH, 1);
        if (range == Range.DAY) cal.add(Calendar.DAY_OF_MONTH, 1);
    }

    private ChartDataHolder convertToChartDataHolder(String[] xAxisCategories, String[] causes,
                                                     Map<String, List<Number>> chartDataPercentage) {
        return new ChartDataHolder(xAxisCategories, convertToChartSeries(causes, chartDataPercentage),
                xAxisCategories.length);
    }

    private List<Series> convertToChartSeries(String[] categories, Map<String, List<Number>> map) {
        List<Series> list = new ArrayList<Series>();
        for (int i = 0; i < categories.length; i++) {
            ListSeries series = new ListSeries(categories[i]);
            series.setData(map.get(categories[i]));
            list.add(series);
        }
        return list;
    }

    private RPTableDataHolder convertToTableDataHolder(String[] causes, double[] totals, Map<String, Double> rejections,
                                                       List<Object[]> rows) {
        double[] causesTotalData = new double[causes.length];
        for (int i = 0; i < causes.length; i++) {
            causesTotalData[i] = (totals[3] > 0 && rejections.get(causes[i]) > 0) ?
                    ((rejections.get(causes[i]) / totals[3]) * 100) : 0;
        }
        String[] tableFooter = getTableFooter(totals[0], totals[1], totals[2], causesTotalData);
        return new RPTableDataHolder(causes, tableFooter, rows);
    }

    private double calculateRejectionPercent(String[] causes, Map<String, List<Number>> chartDataPercentage, Map<String,
            List<Number>> chartDataQuantity, Map<String, Double> rejections, double totalRejections, Object[] row,
                                             List<PaymentsQualityItem> rejectedPayments) {
        for (int i = 0; i < causes.length; i++) {
            double allRejections = 0;
            double rejectedValue = 0;
            for (PaymentsQualityItem item : rejectedPayments) {
                allRejections += item.getValue();
                if (item.getRejectName().equals(causes[i])) {
                    rejectedValue += item.getValue();
                    rejections.put(causes[i], (rejections.get(causes[i]) + item.getValue()));
                }
            }
            double percent = (allRejections > 0 && rejectedValue > 0) ? ((rejectedValue / allRejections) * 100) : 0;
            chartDataPercentage.get(causes[i]).add(percent);
            chartDataQuantity.get(causes[i]).add(rejectedValue);
            // Table data for dynamic columns
            row[i + 4] = String.format("%.1f", percent);
            if (i == (causes.length - 1)) totalRejections += allRejections;
        }
        return totalRejections;
    }

    private String[] getTableFooter(double totalPayments, double totalRejected, double totalClients,
                                    double[] causesData) {
        double totalPercent = (totalRejected > 0 && totalPayments > 0) ? ((totalRejected / totalPayments) * 100) : 0;
        String[] footer = new String[TOTAL_COLUMNS]; // number of table columns
        footer[0] = "Итого";
        footer[1] = String.format("%,d", (int) totalPayments);
        footer[2] = String.format("%,d", (int) totalClients);
        footer[3] = String.format("%.1f", totalPercent);
        for (int i = 0; i < causesData.length; i++) {
            footer[i + 4] = String.format("%.1f", causesData[i]);
        }
        return footer;
    }

    private Map<String, Double> getRejectionsMap(String[] rejections) {
        Map<String, Double> map = new HashMap<String, Double>();
        for (String rejection : rejections) {
            map.put(rejection, 0.00);
        }
        return map;
    }

    private Map<String, List<Number>> createDataSeriesMap(String[] categories) {
        Map<String, List<Number>> map = new HashMap<String, List<Number>>(categories.length);
        for (String category : categories) {
            map.put(category, new ArrayList<Number>());
        }
        return map;
    }

    private String[] calculateRejectCauses(Channel channel, PaymentType paymentType, Date[] dates) {
        String[] causes = new String[CAUSES_LIMIT];
        Map<String, Double> allCauses = new HashMap<String, Double>();
        List<PaymentsQualityItem> rejectedPayments = super.dbManager.getItems(Bank.UKRAINE, ValueType.REJECTED_PAYMENTS,
                channel, paymentType, super.sdf.format(dates[0]), super.sdf.format(dates[1]));
        for (PaymentsQualityItem item : rejectedPayments) {
            if (!allCauses.containsKey(item.getRejectName())) allCauses.put(item.getRejectName(), 0.00);
            double value = allCauses.get(item.getRejectName()) + item.getValue();
            allCauses.put(item.getRejectName(), value);
        }
        allCauses = sortMapByValue(allCauses);
        int i = 0;
        for (String rejectName : allCauses.keySet()) {
            if (i >= causes.length) break;
            causes[i++] = rejectName;
        }
        return causes;
    }

    private Map<String, Double> sortMapByValue(Map<String, Double> unsortedMap) {
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortedMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> first, Map.Entry<String, Double> second) {
                return second.getValue().compareTo(first.getValue());
            }
        });
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
