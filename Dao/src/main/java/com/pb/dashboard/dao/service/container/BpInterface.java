package com.pb.dashboard.dao.service.container;

/**
 * Created by vlad
 * Date: 29.04.15_18:30
 */
public class BpInterface {

    private String main;
    private String part;

    public BpInterface() {
    }

    public BpInterface(String main, String part) {
        this.main = main;
        this.part = part;
    }

    public String getMain() {
        return main;
    }

    public String getPart() {
        return part;
    }
}
