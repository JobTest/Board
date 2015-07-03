package com.pb.dashboard.monitoring.errors.all.db.container;

public class SessionStackTraceData {

    private String message;

    public SessionStackTraceData() {
    }

    public SessionStackTraceData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}