package com.pb.dashboard.external.monitor.logic;

public class SessionViewParams {

    private String complexPKey;
    private String isSystemErrorsData;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String min;
    private String interfaceName;

    public SessionViewParams(String[] params) {
        this.complexPKey = params[0];
        isSystemErrorsData = params[1];
        this.year = params[2];
        this.month = params[3];
        this.day = params[4];
        this.hour = params[5];
        this.min = params[6];
        this.interfaceName = params[7];
    }

    public String getComplexPKey() {
        return complexPKey;
    }

    public String getSystemErrorsData() {
        return isSystemErrorsData;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                complexPKey, isSystemErrorsData, year, month, day, hour, min, interfaceName);
    }

}
