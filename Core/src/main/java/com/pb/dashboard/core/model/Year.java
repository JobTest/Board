package com.pb.dashboard.core.model;

@Deprecated
public enum Year {

    Y2013(2013),
    Y2014(2014),
    Y2015(2015);

    private int year;

    private Year(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}