package com.pb.dashboard.server.service.api;

/**
 * Created by vlad
 * Date: 05.03.15_14:04
 */
public class BpInterface {

    private Integer pkey;
    private String name;
    private String description;

    public BpInterface(com.pb.dashboard.server.dao.entity.vitrina.BpInterface entity) {
        this.pkey = entity.getPkey();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    public Integer getPkey() {
        return pkey;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
