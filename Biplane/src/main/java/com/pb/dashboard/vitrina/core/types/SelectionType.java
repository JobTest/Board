package com.pb.dashboard.vitrina.core.types;

public enum SelectionType {

    DAY("День"),
    COMPARISON("Сравнение");

    private String name;

    private SelectionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
