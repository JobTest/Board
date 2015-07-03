package com.pb.dashboard.monitoring.panel;

/**
 * Created by vlad
 * Date: 29.12.14_14:31
 */
public enum MonitoringType {

    TIMINGS("Тайминги"),
    STATISTIC("Статистика по получателям"),
    ERRORS("Ошибки");


    private final String name;

    MonitoringType(String name) {
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
