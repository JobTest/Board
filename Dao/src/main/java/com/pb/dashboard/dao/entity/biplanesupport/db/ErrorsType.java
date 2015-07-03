package com.pb.dashboard.dao.entity.biplanesupport.db;

/**
 * Created by vlad
 * Date: 30.04.15_9:16
 */
public enum ErrorsType implements ErrorsTypeI {

    INPUT_USER(null, "Ошибки пользовательского ввода"),
    RECIPIENT(null, "Ошибки по получателям"),
    ALL(null, "Все ошибки");

    private final Integer id;
    private final String name;

    ErrorsType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
