package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class Metrics implements Serializable {
    private int metricKey;
    private String metricName;
    private int metricValue;

    public Metrics() {
    }

    public Metrics(int metricKey, String metricName, int metricValue) {
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Metrics other = (Metrics) obj;
        if (this.metricKey != other.metricKey) {
            return false;
        }
        if ((this.metricName == null) ? (other.metricName != null) : !this.metricName.equals(other.metricName)) {
            return false;
        }
        if (this.metricValue != other.metricValue) {
            return false;
        }
        return true;
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
