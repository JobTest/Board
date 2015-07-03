package com.pb.dashboard.external.monitor.entype;

public enum FilterRange {

    R10MIN(0, "10 мин"),
    HOUR(1, "час"),
    DAY(2, "день"),
    MONTH(3, "месяц");

    private int id;
    private String name;

    private FilterRange(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static FilterRange idToFilter(int id) {
        for (FilterRange range : FilterRange.values()) {
            if (range.getId() == id) {
                return range;
            }
        }
        throw new IllegalArgumentException("id " + id + " is not existing.");
    }

}
