package com.pb.dashboard.tickets.entype;

public enum FinancialType {

    // Corresponds to ATTR_ID in xml data

    UNDEF(-1, "Undefined"),
    SALES_ALL(1, "Общее кол-во продаж"),
    PRICE_ALL(2, "Общая стоимость"),
    COMMISSIOIN_ALL(3, "Общая комиссия"),
    SALES_BY_BRANCH(4, "Кол-во продаж в разрезе РП"),
    PRICE_BY_BRANCH(5, "Стоимость в разрезе РП"),
    COMMISSION_BY_BRANCH(6, "Комиссия в разрезе РП");

    private int attrId;
    private String name;

    private FinancialType(int attrId, String name) {
        this.attrId = attrId;
        this.name = name;
    }

    public int getAttrId() {
        return attrId;
    }

    public String getName() {
        return name;
    }
}
