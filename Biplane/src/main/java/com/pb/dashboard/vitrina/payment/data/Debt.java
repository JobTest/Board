package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class Debt implements Serializable {

    private String type;
    private String accepted;
    private String notProcessed;

    public Debt() {
    }

    public Debt(String type, String accepted, String notProcessed) {
        this.type = type;
        this.accepted = accepted;
        this.notProcessed = notProcessed;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getNotProcessed() {
        return notProcessed;
    }

    public String getType() {
        return type;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setNotProcessed(String notProcessed) {
        this.notProcessed = notProcessed;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Debt{" + "type=" + type + ", accepted=" + accepted + ", notProcessed=" + notProcessed + '}';
    }


}
