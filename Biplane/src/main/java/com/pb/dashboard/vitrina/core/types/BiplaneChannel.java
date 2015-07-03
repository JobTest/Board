package com.pb.dashboard.vitrina.core.types;

public enum BiplaneChannel {

    UNDEF(-1, "Undefined"),
    ALL(0, "Все"),
    PAYDESK(1, "Касса"),
    TERMINAL(2, "ТСО"),
    P24(3, "Приват24"),
    LINE3700(4, "3700");

    private int id;
    private String name;

    private BiplaneChannel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
