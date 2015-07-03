package com.pb.dashboard.monitoring.components.parameter;

/**
 * Created by vlad
 * Date: 05.01.15_9:12
 */
public enum MonitoringParam {

    COUNTRY("country"),
    COMPLEX("complex"),
    INTERFACE("interface"),
    METRIC("metric"),
    RANGE("range"),
    DATE("date"),
    DATE_FROM("from"),
    DATE_TO("to"),
    REGLAMENT("reglament"),
    CRITERION("criterion"),
    TYPE("type");

    private final String name;

    MonitoringParam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
