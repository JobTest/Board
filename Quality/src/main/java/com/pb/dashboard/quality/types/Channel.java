package com.pb.dashboard.quality.types;

public enum Channel {

    UNDEF("Channel Undefined", "Channel Undefined"),
    OFFICE("Отделение", "OTD"),
    BIPLAN("Биплан", "BIPLAN"),
    P24("Приват24", "P24"),
    L3700("3700", "3700"),
    TERMINAL("ТСО", "TCO");

    private String name;
    private String attrId;

    private Channel(String name, String attrId) {
        this.name = name;
        this.attrId = attrId;
    }

    public String getName() {
        return name;
    }

    public String getAttrId() {
        return attrId;
    }
}
