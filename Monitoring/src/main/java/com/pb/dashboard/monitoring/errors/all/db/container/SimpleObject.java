package com.pb.dashboard.monitoring.errors.all.db.container;

import java.util.Objects;

public class SimpleObject {

    private Integer id;
    private String name;

    public SimpleObject() {
    }

    public SimpleObject(Integer id) {
        this.id = id;
    }

    public SimpleObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleObject other = (SimpleObject) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }
}
