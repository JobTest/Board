package com.pb.dashboard.core.model;

import com.pb.dashboard.core.util.IntegerUtil;

public enum Complex {

    BIPLANE_API2X(1, "Прием платежей (API 2.x)", "https://itwiki.privatbank.ua/wiki/API_%D0%9F%D0%9A_%D0%91%D0%B8%D0%BF%D0%BB%D0%B0%D0%BD_2.x"),
    DEBT(2, "Задолженность", "http://conf.privatbank.ua/pages/viewpage.action?pageId=8454394"),
    IRBIS(3, "ПК Ирбис", "https://itwiki.privatbank.ua/wiki/%D0%9F%D0%9A_%D0%98%D1%80%D0%B1%D0%B8%D1%81"),
    OCTOPUS(4, "ПК Octopus", "https://itwiki.privatbank.ua/wiki/API_%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D0%B6%D0%B8_%D0%B1%D0%B8%D0%BB%D0%B5%D1%82%D0%BE%D0%B2._%D0%9F%D0%9A_Octopus"),
    TEMPLATES(5, "Шаблоны", "https://itwiki.privatbank.ua/wiki/API_%D0%9F%D0%9A_%D0%A8%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD%D0%BE%D0%B2"),
    REPORTS(6, "Отчеты", "#"),
    SERVER_AUTO_UPLOAD(7, "Cервер автоматической выгрузки", ""),
    WIDGETS(8, "Widgets", "");

    private int id;
    private String name;
    private String specLink;

    private Complex(int id, String name, String specLink) {
        this.id = id;
        this.name = name;
        this.specLink = specLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecLink() {
        return specLink;
    }

    public static Complex pkeyToComplex(String idStr) {
        if (IntegerUtil.checkInt(idStr)) {
            int pkey = Integer.parseInt(idStr);
            return pkeyToComplex(pkey);
        }
        throw new IllegalArgumentException("Complex with id [" + idStr + "] not exists");
    }

    public static Complex pkeyToComplex(int id) {
        for (Complex complex : Complex.values()) {
            if (complex.getId() == id) {
                return complex;
            }
        }
        throw new IllegalArgumentException("Complex with id [" + id + "] not exists");
    }

    public static Complex nameToComplex(String name) {
        for (Complex complex : Complex.values()) {
            if (complex.getName().equalsIgnoreCase(name)) {
                return complex;
            }
        }
        throw new IllegalArgumentException("Complex with name [" + name + "] not exists");
    }
}