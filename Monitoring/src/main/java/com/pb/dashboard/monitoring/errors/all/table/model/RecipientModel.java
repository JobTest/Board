package com.pb.dashboard.monitoring.errors.all.table.model;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class RecipientModel {

    private String id;
    private String name;
    private String filial;
    private int count;

    public RecipientModel() {
    }

    public RecipientModel(String id, String name, String filial, int count) {
        this.id = id;
        this.name = name;
        this.filial = filial;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
