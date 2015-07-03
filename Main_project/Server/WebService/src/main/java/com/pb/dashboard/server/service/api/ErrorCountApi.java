package com.pb.dashboard.server.service.api;

import org.joda.time.LocalDateTime;

/**
 * Created by vlad
 * Date: 06.05.15_15:24
 */
public class ErrorCountApi implements ErrorCountApiI {

    private long date;
    private int business;
    private int system;
    private int code499;

    public void setDate(long date) {
        this.date = date;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public void setCode499(int code499) {
        this.code499 = code499;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public int getBusiness() {
        return business;
    }

    @Override
    public int getSystem() {
        return system;
    }

    @Override
    public int getCode499() {
        return code499;
    }
}
