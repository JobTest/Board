package com.pb.dashboard.dao.entity.biplanesupport.db;

/**
 * Created by vlad
 * Date: 30.04.15_9:27
 */
public class ErrorsTypeCustom implements ErrorsTypeI {

    private Integer id;
    private String name;

    public ErrorsTypeCustom(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
