package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class Payment implements Serializable {

    public String type;
    private String allAcceptPays;
    private String allCash;
    private String allNonCash;
    private String allDebt;

    public Payment(String type, String allAcceptPays, String allCash, String allNonCash, String allDebt) {
        this.type = type;
        this.allAcceptPays = allAcceptPays;
        this.allCash = allCash;
        this.allNonCash = allNonCash;
        this.allDebt = allDebt;
    }

    public String getAllAcceptPays() {
        return allAcceptPays;
    }

    public String getAllCash() {
        return allCash;
    }

    public String getAllDebt() {
        return allDebt;
    }

    public String getAllNonCash() {
        return allNonCash;
    }

    public String getType() {
        return type;
    }

    public void setAllAcceptPays(String allAcceptPays) {
        this.allAcceptPays = allAcceptPays;
    }

    public void setAllCash(String allCash) {
        this.allCash = allCash;
    }

    public void setAllDebt(String allDebt) {
        this.allDebt = allDebt;
    }

    public void setAllNonCash(String allNonCash) {
        this.allNonCash = allNonCash;
    }

    public void setType(String type) {
        this.type = type;
    }
}