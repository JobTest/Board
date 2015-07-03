package com.pb.dashboard.core.model;

public enum ProjectType {

    OTHER(0, "В разработке"),
    BIPLANE(1, "Биплан"),
    TICKETS(2, "Билетная система"),
    TEMPLATES(3, "Бизнес массовых платежей"),
    QUALITY(4, "Статистика качества платежей"),
    MONITOR(5, "Внутренний мониторинг"),
    DEBT_LOAD(6, "Протокол загрузки задолженности"),
    TESTER(7, "Тестирование ПК Биплан"),
    TIMINGS(8, "Тайминги");

    private int typeID;
    private String name;

    private ProjectType(int typeID, String name) {
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
