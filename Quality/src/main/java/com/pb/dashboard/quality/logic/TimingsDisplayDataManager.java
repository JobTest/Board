package com.pb.dashboard.quality.logic;

import com.pb.dashboard.quality.chart.ChartBuilder;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.PaymentType;
import com.pb.dashboard.quality.types.Range;
import com.pb.dashboard.quality.types.ValueType;
import com.pb.dashboard.quality.view.table.TimingsTableDataHolder;
import com.pb.dashboard.quality.view.tabsheet.TimingsTabsheetBuilder;
import com.vaadin.ui.TabSheet;

import java.util.*;

public class TimingsDisplayDataManager extends DisplayDataManager {

    private ChartBuilder chartBuilder;
    private TimingsTabsheetBuilder tabsheetBuilder = new TimingsTabsheetBuilder();

    public TimingsDisplayDataManager(ChartBuilder chartBuilder) {
        this.chartBuilder = chartBuilder;
    }

    public void initReport() {
        super.initDates();
        super.loadMainChartDataSeries(ValueType.PAYMENTS, ValueType.TIMED_OUT_PAYMENTS);
        chartBuilder.setTimingsChartSeries(super.mainChartPercentage);
    }

    public TabSheet initTabsheet() {
        return tabsheetBuilder.getTabSheet(Channel.OFFICE, createOfficeReport(Range.DAY,
                new Date[]{super.dateFrom, super.dateTo}));
    }

    public TabSheet displayDetailedData(Channel channel, Range range, Date[] dates) {
        super.setDates(dates);
        return tabsheetBuilder.getTabSheet(channel, getTabsheetDataHolder(channel, range, dates));
    }

    private List<TimingsTableDataHolder> getTabsheetDataHolder(Channel channel, Range range, Date[] dates) {
        if (channel == Channel.OFFICE) return createOfficeReport(range, dates);
        if (channel == Channel.BIPLAN) return createBiplanReport(range, dates);
        if (channel == Channel.L3700) return create3700Report(range, dates);
        if (channel == Channel.TERMINAL) return createTCOReport(range, dates);
        return createP24Report(range, dates);
    }

    private List<TimingsTableDataHolder> createOfficeReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.EXPRESS};
        return computeStats(range, dates, Channel.OFFICE, types);
    }

    private List<TimingsTableDataHolder> createBiplanReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.PROCEEDS, PaymentType.PHYS};
        return computeStats(range, dates, Channel.BIPLAN, types);
    }

    private List<TimingsTableDataHolder> create3700Report(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.EXPRESS};
        return computeStats(range, dates, Channel.L3700, types);
    }

    private List<TimingsTableDataHolder> createP24Report(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.ALL, PaymentType.MOBILE, PaymentType.BIPLAN, PaymentType.P2P};
        return computeStats(range, dates, Channel.P24, types);
    }

    private List<TimingsTableDataHolder> createTCOReport(Range range, Date[] dates) {
        PaymentType[] types = {PaymentType.ALL, PaymentType.MOBILE, PaymentType.BIPLAN};
        return computeStats(range, dates, Channel.TERMINAL, types);
    }

    private List<TimingsTableDataHolder> computeStats(Range range, Date[] dates, Channel channel, PaymentType[] paymentTypes) {
        List<TimingsTableDataHolder> listOfTableHolders = new ArrayList<TimingsTableDataHolder>();
        for (PaymentType currentType : paymentTypes) {
            List<Object[]> rows = new ArrayList<Object[]>(); // Table Rows holder
            // init grand totals for table footer
            double totalPayments = 0,
                    totalTimedOut = 0,
                    totalClients = 0;
            Map<String, Double> byDayMapPayments = null;
            Map<String, Double> byDayMapTimedOut = null;
            Map<String, Double> byDayMapClients = null;
            Map<String, Double> byDayMapAvgPaymentTime = null;
            Map<String, Double> byDayMapAvgODBTime = null;
            if (range == Range.DAY) {
                byDayMapPayments = super.dbManager.getValueByDate(super.bank, ValueType.PAYMENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapTimedOut = super.dbManager.getValueByDate(super.bank, ValueType.TIMED_OUT_PAYMENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapClients = super.dbManager.getValueByDate(super.bank, ValueType.UNIQUE_CLIENTS, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapAvgPaymentTime = super.dbManager.getValueByDate(super.bank, ValueType.AVG_PAYMENT_TIME, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
                byDayMapAvgODBTime = super.dbManager.getValueByDate(super.bank, ValueType.AVG_TIME_ODB, channel,
                        currentType, super.sdf.format(dateFrom), super.sdf.format(dateTo));
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(dates[0]);
            while (cal.getTime().before(dates[1]) || cal.getTime().equals(dates[1])) {
                Date to = (range == Range.DAY) ? cal.getTime() : super.adjustDateByRange(cal.getTime(), range);
                String dateFrom = super.sdf.format(cal.getTime());
                String dateTo = sdf.format(to);
                String period = super.getPeriod(range, new Date[]{cal.getTime(), to});
                double paymentVal = 0,
                        timedOutVal = 0,
                        clientsVal = 0,
                        avgPaymentTime = 0,
                        avgODBTime = 0;
                if (byDayMapPayments != null) {
                    if (byDayMapPayments.get(dateFrom) != null) paymentVal = byDayMapPayments.get(dateFrom);
                    if (byDayMapTimedOut.get(dateFrom) != null) timedOutVal = byDayMapTimedOut.get(dateFrom);
                    if (byDayMapClients.get(dateFrom) != null) clientsVal = byDayMapClients.get(dateFrom);
                    if (byDayMapAvgPaymentTime.get(dateFrom) != null)
                        avgPaymentTime = byDayMapAvgPaymentTime.get(dateFrom);
                    if (byDayMapAvgODBTime.get(dateFrom) != null) avgODBTime = byDayMapAvgODBTime.get(dateFrom);
                } else {
                    paymentVal = super.dbManager.getSumValue(super.bank, ValueType.PAYMENTS, channel, currentType,
                            dateFrom, dateTo);
                    timedOutVal = super.dbManager.getSumValue(super.bank, ValueType.TIMED_OUT_PAYMENTS, channel, currentType,
                            dateFrom, dateTo);
                    clientsVal = super.dbManager.getSumValue(super.bank, ValueType.UNIQUE_CLIENTS, channel, currentType,
                            dateFrom, dateTo);
                    avgPaymentTime = super.dbManager.getAvgProcessingTime(super.bank, ValueType.AVG_PAYMENT_TIME, channel,
                            currentType, dateFrom, dateTo);
                    avgODBTime = super.dbManager.getAvgProcessingTime(super.bank, ValueType.AVG_TIME_ODB, channel,
                            currentType, dateFrom, dateTo);
                }
                totalPayments += paymentVal;
                totalTimedOut += timedOutVal;
                totalClients += clientsVal;
                // Table data for static columns
                rows.add(buildRow(period, paymentVal, timedOutVal, avgPaymentTime, avgODBTime, (int) clientsVal));
                if (range == Range.WEEK) cal.add(Calendar.DAY_OF_WEEK, 7);
                if (range == Range.MONTH) cal.add(Calendar.MONTH, 1);
                if (range == Range.DAY) cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            // Preparing Table data
            double processingTime = super.dbManager.getAvgProcessingTime(super.bank, ValueType.AVG_PAYMENT_TIME, channel,
                    currentType, super.sdf.format(dates[0]), super.sdf.format(dates[1]));
            double odbTime = super.dbManager.getAvgProcessingTime(super.bank, ValueType.AVG_TIME_ODB, channel,
                    currentType, super.sdf.format(dates[0]), super.sdf.format(dates[1]));
            double[] footerData = {totalPayments, totalTimedOut, totalClients, processingTime, odbTime};
            String[] tableFooter = getTableFooter(footerData);
            TimingsTableDataHolder tableDataHolder = new TimingsTableDataHolder(tableFooter, rows);
            listOfTableHolders.add(tableDataHolder);
        }
        return listOfTableHolders;
    }

    private Object[] buildRow(String period, double paymentVal, double timedOutVal,
                              double avgPaymentTime, double avgODBTime,
                              int clientsVal) {
        Object[] row = new Object[6]; // number of table columns
        row[0] = period;
        row[1] = String.format("%,d", (int) paymentVal);
        row[2] = String.format("%,d", (int) clientsVal);
        double percentVal = (paymentVal > 0 && timedOutVal > 0) ? ((timedOutVal / paymentVal) * 100) : 0;
        row[3] = String.format("%.2f", percentVal);
        row[4] = String.format("%s", convertTime(avgPaymentTime));
        row[5] = String.format("%s", convertTime(avgODBTime));
        return row;
    }

    private String[] getTableFooter(double[] data) {
        double totalPercent = (data[1] > 0 && data[0] > 0) ? ((data[1] / data[0]) * 100) : 0;
        String[] footer = new String[6];  // number of table columns
        footer[0] = "Итого";
        footer[1] = String.format("%,d", (int) data[0]);
        footer[2] = String.format("%,d", (int) data[2]);
        footer[3] = String.format("%.2f", totalPercent);
        footer[4] = String.format("%s", convertTime(data[3]));
        footer[5] = String.format("%s", convertTime(data[4]));
        return footer;
    }

    private String convertTime(double raw) {
        double seconds = raw / 1000;
        if (seconds > 60) {
            double minutes = seconds / 60;
            if (minutes > 60) {
                double hours = minutes / 60;
                return String.format("%d ч. %d мин.", (int) hours, (int) (minutes % 60));
            }
            return String.format("%d мин. %d сек.", (int) minutes, (int) (seconds % 60));
        }
        return String.format("%.3f сек.", seconds);
    }
}
