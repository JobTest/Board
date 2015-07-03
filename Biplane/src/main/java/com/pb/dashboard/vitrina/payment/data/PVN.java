package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class PVN implements Serializable {

    private String accepted;
    private String processed;

    public PVN() {
    }

    public PVN(String accepted, String processed) {
        this.accepted = accepted;
        this.processed = processed;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getProcessed() {
        return processed;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "PVN{" + "accepted=" + accepted + ", processed=" + processed + '}';
    }


}
