package com.pb.dashboard.vitrina.statistics.byday;

import java.io.Serializable;

public class DayHourMetrics implements Serializable {
    private int metricKey;
    private int metricHour;
    private int metricValue;

    public DayHourMetrics() {
    }

    public DayHourMetrics(int metricKey, int metricHour, int metricValue) {
        this.metricKey = metricKey;
        this.metricHour = metricHour;
        this.metricValue = metricValue;
    }

    public int getMetricKey() {
        return metricKey;
    }

    public int getMetricHour() {
        return metricHour;
    }

    public int getMetricValue() {
        return metricValue;
    }

    public void setMetricKey(int metricKey) {
        this.metricKey = metricKey;
    }

    public void setMetricHour(int metricHour) {
        this.metricHour = metricHour;
    }

    public void setMetricValue(int metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public String toString() {
        return "DayMetrics{" + "metricKey=" + metricKey + ", metricHour=" + metricHour + ", metricValue=" + metricValue + '}';
    }
}
