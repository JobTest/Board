package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class OperDayBiplane implements Serializable {

    private String type;
    private String opened;
    private String closed;

    public OperDayBiplane(String type, String opened, String closed) {
        this.type = type;
        this.opened = opened;
        this.closed = closed;
    }

    public OperDayBiplane() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getType() {
        return type;
    }

    public String getOpened() {
        return opened;
    }

    public String getClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return "operDayBiplane{" + "type=" + type + ", opened=" + opened + ", closed=" + closed + '}';
    }
}
