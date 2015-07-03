package com.pb.dashboard;

public enum Country {

    UKRAINE(1, "Украина"),
    RUSSIA(2, "Россия"),
    GEORGIA(3, "Грузия");

    private int id;
    private String name;

    private Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Country pkeyToCountry(String idStr) {
        int pkey = Integer.parseInt(idStr);
        return pkeyToCountry(pkey);
    }

    public static Country pkeyToCountry(int id) {
        for (Country country : Country.values()) {
            if (country.getId() == id) {
                return country;
            }
        }
        throw new IllegalArgumentException("Country with id [" + id + "] not exists.");
    }
}