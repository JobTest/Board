package com.pb.dashboard.server.service.api.support;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by vlad
 * Date: 12.05.15_16:33
 */
@JsonSerialize(using = FilterTypeSerializer.class)
public enum FilterType implements FilterTypeI {

    T10MIN(0, "10 мин", false, false),
    HOUR(1, "час", true, false),
    DAY(2, "день", false, true),
    MONTH(3, "месяц", false, false);

    private int id;
    private String name;
    private boolean date;
    private boolean period;

    FilterType(int id, String name, boolean dateVisible, boolean periodVisible) {
        this.id = id;
        this.name = name;
        this.date = dateVisible;
        this.period = periodVisible;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return name;
    }

    public boolean isDate() {
        return date;
    }

    public boolean isPeriod() {
        return period;
    }


    public static FilterType getType(int id) {
        for (FilterType type : FilterType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type with id = " + id + " not found.");
    }
}
