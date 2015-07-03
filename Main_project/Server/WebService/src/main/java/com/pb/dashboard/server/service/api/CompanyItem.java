package com.pb.dashboard.server.service.api;

import com.pb.dashboard.server.dao.entity.iqlogrep.CountForCompany;

/**
 * Created by vlad
 * Date: 05.03.15_14:06
 */
public class CompanyItem {

    private int hour;
    private int count;
    private int errorCount;
    private int durationMax;
    private int duration90;
    private int duration95;
    private int duration99;

    public CompanyItem(CountForCompany entity) {
        this.hour = entity.getHour();
        this.count = entity.getCount();
        this.errorCount = entity.getErrorCount();
        this.durationMax = entity.getDurationMax();
        this.duration90 = entity.getDuration90();
        this.duration95 = entity.getDuration95();
        this.duration99 = entity.getDuration99();
    }

    public int getHour() {
        return hour;
    }

    public int getCount() {
        return count;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getDurationMax() {
        return durationMax;
    }

    public int getDuration90() {
        return duration90;
    }

    public int getDuration95() {
        return duration95;
    }

    public int getDuration99() {
        return duration99;
    }
}
