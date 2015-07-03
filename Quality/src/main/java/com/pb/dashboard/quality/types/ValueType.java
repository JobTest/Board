package com.pb.dashboard.quality.types;

public enum ValueType {

    UNDEF("Value Undefined"),
    PAYMENTS("КОЛИЧЕСТВО ПЛАТЕЖЕЙ"),
    REJECTED_PAYMENTS("КОЛИЧЕСТВО ЗАБРАКОВАННЫХ ПЛАТЕЖЕЙ"),
    UNIQUE_CLIENTS("КОЛИЧЕСТВО УНИКАЛЬНЫХ КЛИЕНТОВ"),
    AVG_PAYMENT_TIME("СРЕДНЕЕ ВРЕМЯ ПРОВЕДЕНИЯ"),
    AVG_TIME_ODB("СРЕДНЕЕ ВРЕМЯ ОДБ"),
    TIMED_OUT_PAYMENTS("КОЛИЧЕСТВО ПЛАТЕЖЕЙ, НЕ УЛОЖИВШИХСЯ В РЕГЛАМЕНТ");

    private String name;

    private ValueType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
