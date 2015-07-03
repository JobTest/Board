package com.pb.dashboard.dao.entity.biplanesupport.db;

public class TimingT2Data {

    private String message;
    private String appTimestamp;
    private String duration;
    private String status;
    private String module;
    private String dopInfo;
    private String method;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppTimestamp() {
        return appTimestamp;
    }

    public void setStartTime(String appTimestamp) {
        this.appTimestamp = appTimestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDopInfo() {
        return dopInfo;
    }

    public void setDopInfo(String dopInfo) {
        this.dopInfo = dopInfo;
    }
}