package com.pb.dashboard.vitrina.statistics.byday;

import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayMetrics extends Metrics implements Comparable {

    private int date;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public DayMetrics(int metricKey, String metricName, int metricValue, int date) {
        super(metricKey, metricName, metricValue);
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public Date getCalendarDate() {
        Date d = null;
        try {
            d = sdf.parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String toString() {
        return "date: " + getDate() + " key: " + getMetricKey() + " value: " + getMetricValue();
    }

    @Override
    public int compareTo(Object o) {
        DayMetrics that = (DayMetrics) o;
        if (this.getCalendarDate().after(that.getCalendarDate())) return 1;
        else if (this.getCalendarDate().before(that.getCalendarDate())) return -1;
        return 0;
    }
}
