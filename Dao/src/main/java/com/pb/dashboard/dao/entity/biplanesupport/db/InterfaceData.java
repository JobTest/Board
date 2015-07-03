package com.pb.dashboard.dao.entity.biplanesupport.db;

public class InterfaceData {

    private String bpInterface;
    private int count;
    private int err400count;
    private int err500count;

    public InterfaceData(String pbInterface, int count, int err400count, int err500count) {
        this.bpInterface = pbInterface;
        this.count = count;
        this.err400count = err400count;
        this.err500count = err500count;
    }

    public String getBpInterface() {
        return bpInterface;
    }

    public int getCount() {
        return count;
    }

    public int getErr400count() {
        return err400count;
    }

    public int getErr500count() {
        return err500count;
    }
}
