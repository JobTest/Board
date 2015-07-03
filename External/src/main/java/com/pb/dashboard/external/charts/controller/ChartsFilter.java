package com.pb.dashboard.external.charts.controller;

import java.util.Date;

public class ChartsFilter {
    private boolean daily;
    private boolean hourly;
    private Date from;
    private Date to;
    private int bank;

    public ChartsFilter(int bank,String filterValue) {
        this.bank = bank;
        if (filterValue.equals("hourly")){
            hourly = true;
        }
        else{
            daily = true;
        }
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
        this.hourly = !daily;
    }

    public boolean isHourly() {
        return hourly;
    }

    public void setHourly(boolean hourly) {
        this.hourly = hourly;
        this.daily = !daily;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getBank() {
        return bank;
    }
}
