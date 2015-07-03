package com.pb.dashboard.quality.types;

public enum PaymentType {

    UNDEF("Payment Type Undefined", "Payment Type Undefined"),
    PROCEEDS("Выручка", "PROCEEDS"),
    TERMINAL("ТСО", "TCO"),
    ALL("Все платежи", "ALL"),
    P24("Приват24", "P24"),
    BIPLAN("Биплан", "BPL"),
    P2P("P2P платежи", "P2P"),
    MOBILE("Мобильная связь", "MOB"),
    EXPRESS("Экспресс платежи", "EXPRESS"),
    PHYS("Платежи физ.лиц", "PHYS_PAYS");

    private String name;
    private String attrID; // DIMENSION_1

    private PaymentType(String name, String attrID) {
        this.name = name;
        this.attrID = attrID;
    }

    public String getName() {
        return name;
    }

    public String getAttrID() {
        return attrID;
    }
}
