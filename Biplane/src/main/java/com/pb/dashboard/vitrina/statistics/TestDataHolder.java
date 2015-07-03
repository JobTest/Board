package com.pb.dashboard.vitrina.statistics;

import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.statistics.byday.DayHourMetrics;

import java.util.List;

public class TestDataHolder {
    private static TestDataHolder ourInstance = new TestDataHolder();

    public static TestDataHolder getInstance() {
        return ourInstance;
    }

    private TestDataHolder() {
    }

    //DayHour Metrics

    //Ukraine
    private List<DayHourMetrics> mainDate;
    private List<DayHourMetrics> compDate;
    //Georgia
    private List<DayHourMetrics> mainDateGe;
    private List<DayHourMetrics> compDateGe;
    //Russia
    private List<DayHourMetrics> mainDateRu;
    private List<DayHourMetrics> compDateRu;


    public List<DayHourMetrics> getMainDate(CountryI country) {
        if (country instanceof CountryRU) return getRUMainDate();
        if (country instanceof CountryGEO) return getGEMainDate();
        return getUAMainDate();
    }

    public List<DayHourMetrics> getCompDate(CountryI country) {
        if (country instanceof CountryRU) return getRUCompDate();
        if (country instanceof CountryGEO) return getGECompDate();
        return getUACompDate();
    }

    private List<DayHourMetrics> getUAMainDate() {
        if (mainDate == null) mainDate = Utilities.getRandDayHourMetrics(1);
        return mainDate;
    }

    private List<DayHourMetrics> getRUMainDate() {
        if (mainDateRu == null) mainDateRu = Utilities.getRandDayHourMetrics(1);
        return mainDateRu;
    }

    private List<DayHourMetrics> getGEMainDate() {
        if (mainDateGe == null) mainDateGe = Utilities.getRandDayHourMetrics(1);
        return mainDateGe;
    }

    private List<DayHourMetrics> getUACompDate() {
        if (compDate == null) compDate = Utilities.getRandDayHourMetrics(1);
        return compDate;
    }

    private List<DayHourMetrics> getRUCompDate() {
        if (compDateRu == null) compDateRu = Utilities.getRandDayHourMetrics(1);
        return compDateRu;
    }

    private List<DayHourMetrics> getGECompDate() {
        if (compDateGe == null) compDateGe = Utilities.getRandDayHourMetrics(1);
        return compDateGe;
    }

}
