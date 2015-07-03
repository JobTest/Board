package com.pb.dashboard.monitoring.timings;

/**
 * Created by vlad
 * Date: 16.12.14_17:04
 */
public enum TimingsType {

    QUERIES("Запросы"),
    ERRORS("Ошибки"),
    MIN("Мин."),
    MAX("Макс."),
    AVG("Средн."),
    P90("90%"),
    P95("95%"),
    P99("99%");

    private final String name;

    TimingsType(String name) {
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
