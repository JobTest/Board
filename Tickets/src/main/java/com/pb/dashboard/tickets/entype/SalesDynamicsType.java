package com.pb.dashboard.tickets.entype;

public enum SalesDynamicsType {

    BY_TICKET_TYPE(0, "По типу билета"),
    BY_CHANNEL(1, "В разрезе каналов"),
    DYNAMICS(2, "Сравнительный график");

    private int typeID;
    private String name;

    private SalesDynamicsType(int typeID, String name) {
        this.typeID = typeID;
        this.name = name;
    }

    public int getTypeID() {
        return typeID;
    }

    public String getName() {
        return name;
    }

}
