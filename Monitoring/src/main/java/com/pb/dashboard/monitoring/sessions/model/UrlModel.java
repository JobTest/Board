package com.pb.dashboard.monitoring.sessions.model;

import com.pb.dashboard.monitoring.components.filter.FilterRange;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 19.01.15_12:16
 */
public class UrlModel {

    private Integer country;
    private Integer complex;
    private Integer bpInterface;
    private FilterRange range;
    private Calendar date;
    private Calendar dateFrom;
    private Calendar dateTo;


    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getComplex() {
        return complex;
    }

    public void setComplex(Integer complex) {
        this.complex = complex;
    }

    public Integer getBpInterface() {
        return bpInterface;
    }

    public void setBpInterface(Integer bpInterface) {
        this.bpInterface = bpInterface;
    }

    public Calendar getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Calendar dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Calendar getDateTo() {
        return dateTo;
    }

    public void setDateTo(Calendar dateTo) {
        this.dateTo = dateTo;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public FilterRange getRange() {
        return range;
    }

    public void setRange(FilterRange range) {
        this.range = range;
    }
}
