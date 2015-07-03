package com.pb.dashboard.server.service.api;

import com.pb.dashboard.server.dao.entity.description.DescriptionCompany;

/**
 * Created by vlad
 * Date: 05.03.15_14:05
 */
public class CompanyDescription {

    private int id;
    private String name;
    private String filial;

    public CompanyDescription(DescriptionCompany entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.filial = entity.getFilial();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilial() {
        return filial;
    }
}
