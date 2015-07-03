package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.vaadin.addon.charts.model.style.SolidColor;

/**
 * Created by vlad
 * Date: 03.04.15_12:16
 */
public enum TimingsType {
    AVG("avg", SolidColor.BLUE),
    PERCENT_90("90%", SolidColor.GREEN),
    PERCENT_95("95%", SolidColor.RED),
    PERCENT_99("99%", SolidColor.ORANGE);

    private final String name;
    private final SolidColor color;

    TimingsType(String name, SolidColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public SolidColor getColor() {
        return color;
    }

    public static TimingsType get(String name) {
        for (TimingsType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("TimingsType " + name);
    }
}
