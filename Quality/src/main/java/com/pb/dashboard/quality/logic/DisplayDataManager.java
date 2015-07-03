package com.pb.dashboard.quality.logic;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.quality.chart.ChartDataHolder;
import com.pb.dashboard.quality.db.QualityDBManager;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.Range;
import com.pb.dashboard.quality.types.ValueType;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DisplayDataManager {

    protected Bank bank = Bank.UKRAINE;
    protected Date dateFrom;
    protected Date dateTo;

    protected ChartDataHolder mainChartPercentage;
    //protected ChartDataHolder mainChartQuantity;

    protected QualityDBManager dbManager = new QualityDBManager();
    private Locale ru = new Locale("RU");

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("d-MMM-yyyy", ru);
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMMM, yyyy", ru);

    protected void setDates(Date[] dates) {
        dateFrom = dates[0];
        dateTo = dates[1];
    }

    protected void loadMainChartDataSeries(ValueType firstType, ValueType secondType) {
        Map<Channel, List<Number>> chartDataPercentage = initChannelMap();
        //Map<Channel, List<Number>> chartDataQuantity = initChannelMap();
        List<String> dates = new ArrayList<String>(); // chart categories (dates)
        for (Channel channel : Channel.values()) {
            if (channel == Channel.UNDEF) continue;
            Map<String, Double> firstTypeMap = dbManager.getTotalValueByChannel(bank, firstType, channel,
                    sdf.format(dateFrom), sdf.format(dateTo));
            Map<String, Double> secondTypeMap = dbManager.getTotalValueByChannel(bank, secondType, channel,
                    sdf.format(dateFrom), sdf.format(dateTo));
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFrom);
            while (cal.getTime().before(dateTo) || cal.getTime().equals(dateTo)) {
                String date = sdf.format(cal.getTime());
                double first = (firstTypeMap.get(date) != null) ? firstTypeMap.get(date) : 0;
                double second = (secondTypeMap.get(date) != null) ? secondTypeMap.get(date) : 0;
                double percent = (first > 0 && second > 0) ? ((second / first) * 100) : 0;
                chartDataPercentage.get(channel).add(percent);
                //chartDataQuantity.get(channel).add(second);
                if (channel == Channel.BIPLAN) dates.add(dayFormat.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        // Convert List to array of xAxis Category names (dates)
        String[] xAxisCategories = dates.toArray(new String[dates.size()]);
        // Convert holder to series
        List<Series> listOfSeriesPercentage = new ArrayList<Series>();
        //List<Series> listOfSeriesQuantity = new ArrayList<Series>();
        for (Channel channel : Channel.values()) {
            if (channel == Channel.UNDEF) continue;
            ListSeries seriesPercentage = new ListSeries(channel.getName());
            //ListSeries seriesQuantity = new ListSeries(channel.getName());
            seriesPercentage.setData(chartDataPercentage.get(channel));
            //seriesQuantity.setData(chartDataQuantity.get(channel));
            listOfSeriesPercentage.add(seriesPercentage);
            //listOfSeriesQuantity.add(seriesQuantity);
        }
        mainChartPercentage = new ChartDataHolder(xAxisCategories, listOfSeriesPercentage, xAxisCategories.length);
        //super.mainChartQuantity = new ChartDataHolder(xAxisCategories, listOfSeriesQuantity);
    }

    protected String getPeriod(Range range, Date[] dates) {
        return (range == Range.DAY) ? dayFormat.format(dates[0]) :
                (range == Range.WEEK) ? dayFormat.format(dates[0]) + " - " + dayFormat.format(dates[1]) :
                        monthFormat.format(dates[0]);
    }

    private Map<Channel, List<Number>> initChannelMap() {
        Map<Channel, List<Number>> map = new HashMap<Channel, List<Number>>();
        for (Channel channel : Channel.values()) {
            if (channel == Channel.UNDEF) continue;
            map.put(channel, new ArrayList<Number>());
        }
        return map;
    }

    // Dates initialization and adjustment
    protected void initDates() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dateTo = cal.getTime();
        cal.add(Calendar.MONTH, -1);
        dateFrom = cal.getTime();
    }

    protected Date adjustDateByRange(Date date, Range range) {
        if (range == Range.WEEK) return addWeek(date);
        if (range == Range.MONTH) return addMonth(date);
        return new Date();
    }

    private Date addMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    private Date addWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }
}
