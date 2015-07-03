package com.pb.dashboard.server.dao.entity.description;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by vlad
 * Date: 02.02.15_12:04
 */

@Entity
public class DescriptionCompany {

    @Transient
    private int id;
    @Id
    @Column(name = "company_name")
    private String name;
    @Column(name = "filial_name")
    private String filial;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilial() {
        return filial;
    }

    public void setId(int id) {
        this.id = id;
    }
}
