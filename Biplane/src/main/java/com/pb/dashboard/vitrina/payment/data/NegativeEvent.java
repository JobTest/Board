package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class NegativeEvent implements Serializable {
    private int metricKey;
    private String metricName;
    private int metricValue;

    public NegativeEvent() {
    }

    public NegativeEvent(int metricKey, String metricName, int metricValue) {
        this.metricKey = metricKey;
        this.metricName = metricName;
        this.metricValue = metricValue;
    }

    public int getMetricKey() {
        return metricKey;
    }

    public String getMetricName() {
        return metricName;
    }

    public int getMetricValue() {
        return metricValue;
    }

    public void setMetricKey(int metricKey) {
        this.metricKey = metricKey;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public void setMetricValue(int metricValue) {
        this.metricValue = metricValue;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.metricKey;
        hash = 37 * hash + (this.metricName != null ? this.metricName.hashCode() : 0);
        hash = 37 * hash + this.metricValue;
        return hash;
    }

    @Override
    public String toString() {
        return "Metrics{" + "metricKey=" + metricKey + ", metricName=" + metricName + ", metricValue=" + metricValue + '}';
    }


}
