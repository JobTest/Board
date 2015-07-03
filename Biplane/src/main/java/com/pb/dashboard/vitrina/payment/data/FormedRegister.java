package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class FormedRegister implements Serializable {
    private String type;
    private String send;
    private String notSend;

    public FormedRegister() {
    }

    public FormedRegister(String type, String send, String notSend) {
        this.type = type;
        this.send = send;
        this.notSend = notSend;
    }

    public String getNotSend() {
        return notSend;
    }

    public String getSend() {
        return send;
    }

    public String getType() {
        return type;
    }

    public void setNotSend(String notSend) {
        this.notSend = notSend;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FormedRegister{" + "type=" + type + ", send=" + send + ", notSend=" + notSend + '}';
    }


}
