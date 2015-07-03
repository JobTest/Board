package com.pb.dashboard.external.monitor.chart;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ErrorsDataHolder {

    private Calendar calendar;
    private int businessErrors;
    private int systemErrors;

    public ErrorsDataHolder(int businessErrors, int systemErrors) {
        calendar = Calendar.getInstance();
        this.businessErrors = businessErrors;
        this.systemErrors = systemErrors;
    }

    public String getDate(SimpleDateFormat sdf) {
        return sdf.format(calendar.getTime());
    }

    public void setDay(int day) {
        this.calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setHour(int hour) {
        this.calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinutes(int minutes) {
        this.calendar.set(Calendar.MINUTE, minutes);
    }

    public void setMonth(int month) {
        this.calendar.set(Calendar.MONTH, month);
    }


    public void setYear(int year) {
        this.calendar.set(Calendar.YEAR, year);
    }


    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setBusinessErrors(int businessErrors) {
        this.businessErrors = businessErrors;
    }

    public void setSystemErrors(int systemErrors) {
        this.systemErrors = systemErrors;
    }

    public Calendar getCalendar() {
        return calendar;
    }


    public int getBusinessErrors() {
        return businessErrors;
    }

    public int getSystemErrors() {
        return systemErrors;
    }
}
