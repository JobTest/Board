package com.pb.dashboard.monitoring.errors.outer.table;

/**
 * Created by evasive on 03.11.14.
 */

public class OuterSessionData implements Cloneable {

    private String sessionId;
    private String inName;
    private String startTime;
    private String duration;
    private String status;
    private String companyId;
    private String errorCode;

    public OuterSessionData() {
    }

    public OuterSessionData(String sessionId, String inName,
                            String startTime, String duration, String status, String companyId,
                            String errorCode) {
        this.sessionId = sessionId;
        this.inName = inName;
        this.startTime = startTime;
        this.duration = duration;
        this.companyId = companyId;
        this.status = status;
        this.errorCode = errorCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public OuterSessionData clone() {
        try {
            return (OuterSessionData) super.clone();
        } catch (CloneNotSupportedException ignore) {
        }
        return new OuterSessionData();
    }
}
