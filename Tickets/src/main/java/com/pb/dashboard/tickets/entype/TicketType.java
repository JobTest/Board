package com.pb.dashboard.tickets.entype;

public enum TicketType {

    ALL(0, "Все"),
    BUS(1, "Автобус"),
    TRAIN(2, "Поезд"),
    AVIA(3, "Авиа"),
    FOOTB(4, "Футбол"),
    HOCKEY(5, "Хоккей"),
    BASKET(6, "Баскетбол"),
    CULTU(7, "Концерт"),
    KINO(8, "Кино"),
    ABON(9, "Абонемент");

    private int id;
    private String name;

    private TicketType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TicketType idToTicketType(int id) {
        for (TicketType type : TicketType.values()) {
            if (type.getId() == id) return type;
        }
        return null;
    }

}
