package com.pb.dashboard.external.monitor.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.external.monitor.entype.FilterRange;

/**
 * Created by vlad
 * Date: 10.11.14_16:26
 */
public class SessionsModel {

    private Complex complex;
    private boolean isSystem;
    private FilterRange filterRange;
    private int year;
    private int month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private String interfaceName;

    public SessionsModel() {
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complexPKey) {
        this.complex = complexPKey;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public void setFilterRange(FilterRange filterRange) {
        this.filterRange = filterRange;
    }
}
