package com.pb.dashboard.vitrina.core.config;

public enum Language {

    UA("Украина"),
    RU("Россия"),
    GEO("Грузия");

    private String name;

    private Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
