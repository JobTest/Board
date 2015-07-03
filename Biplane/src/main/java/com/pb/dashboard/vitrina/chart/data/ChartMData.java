package com.pb.dashboard.vitrina.chart.data;

import java.io.Serializable;

public class ChartMData implements Serializable {
    private int pkey;
    private int value;
    private String date;

    public ChartMData(int pkey, int value, String date) {
        this.pkey = pkey;
        this.value = value;
        this.date = date;
    }

    public ChartMData() {
    }

    public String getDate() {
        return date;
    }

    public int getPkey() {
        return pkey;
    }

    public int getValue() {
        return value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPkey(int pkey) {
        this.pkey = pkey;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "chartMData{" + "value=" + value + ", date=" + date + '}';
    }


}
