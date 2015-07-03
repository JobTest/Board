package com.pb.dashboard.monitoring.errors.all.db.container;

public class SessionInfo implements Cloneable {

    private String startTimestamp = "";
    private String duration = "";
    private String bpInterface = "";

    public SessionInfo() {
    }

    public SessionInfo(String startTimestamp, String duration, String bpInterface) {
        this.startTimestamp = startTimestamp;
        this.duration = duration;
        this.bpInterface = bpInterface;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBpInterface() {
        return bpInterface;
    }

    public void setBpInterface(String bpInterface) {
        this.bpInterface = bpInterface;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append("Время выполнения: \"").append(getStartTimestamp()).append("\"  ");
        sb.append("Длительность: \"").append(getDuration()).append("\" мс.  ");
        sb.append("Интерфейс: \"").append(getBpInterface()).append("\")");
        return sb.toString();
    }

    @Override
    public SessionInfo clone() {
        try {
            return (SessionInfo) super.clone();
        } catch (CloneNotSupportedException ignore) {
        }
        return new SessionInfo();
    }
}

