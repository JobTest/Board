package com.pb.dashboard.core.model;

public enum Bank {

    UKRAINE(1, "Украина", "PB"),
    RUSSIA(2, "Россия", "MP"),
    GEORGIA(3, "Грузия", "TG"),
    LATVIA(4, "Латвия", "PL"),
    A_BANK(5, "А-Банк", "AB");

    private int id;
    private String name;
    private String attrName;

    private Bank(int id, String name, String attrName) {
        this.id = id;
        this.name = name;
        this.attrName = attrName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAttrName() {
        return attrName;
    }
}