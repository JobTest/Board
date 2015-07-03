package com.pb.dashboard.monitoring.timings.tabsheet;

/**
 * Created by vlad
 * Date: 20.11.14_10:14
 */
public enum IndicatorsTab {

    CHARTS("Графики"),
    TABLE("Таблица"),
    SLA("SLA");

    private String name;

    private IndicatorsTab(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
