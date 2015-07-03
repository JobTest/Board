package com.pb.dashboard.core.model;

public enum Month {

    JANUARY(1, "Январь"),
    FEBRUARY(2, "Февраль"),
    MARCH(3, "Март"),
    APRIL(4, "Апрель"),
    MAY(5, "Май"),
    JUNE(6, "Июнь"),
    JULY(7, "Июль"),
    AUGUST(8, "Август"),
    SEPTEMBER(9, "Сентябрь"),
    OCTOBER(10, "Октябрь"),
    NOVEMBER(11, "Ноябрь"),
    DECEMBER(12, "Декабрь"),
    YEAR(0, "Весь год");

    private int number;
    private String name;

    private Month(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public static String indexToName(int index) throws IllegalArgumentException {
        for (Month month : Month.values()) {
            if (month == Month.YEAR) {
                continue;
            }
            if (month.getNumber() == index) {
                return month.getName();
            }
        }
        throw new IllegalArgumentException("Illegal month index: " + index);
    }

    public static int nameToIndex(String name) throws IllegalArgumentException {
        for (Month month : Month.values()) {
            if (month == Month.YEAR) {
                continue;
            }
            if (month.getName().equals(name)) {
                return month.getNumber();
            }
        }
        throw new IllegalArgumentException("Illegal month name: " + name);
    }
}