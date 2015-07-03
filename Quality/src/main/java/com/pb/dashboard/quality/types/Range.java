package com.pb.dashboard.quality.types;

public enum Range {

    MONTH("месяц"),
    WEEK("неделя"),
    DAY("день");

    private String name;

    private Range(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
