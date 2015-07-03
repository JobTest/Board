package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class PaymentDetails implements Serializable {

    private String currentTime;
    private String acceptPays;
    private String cash;
    private String nonCash;
    private String debt;

    public PaymentDetails() {
    }

    public PaymentDetails(String currentTime, String acceptPays, String cash, String nonCash, String debt) {
        this.currentTime = currentTime;
        this.acceptPays = acceptPays;
        this.cash = cash;
        this.nonCash = nonCash;
        this.debt = debt;
    }

    public String getAcceptPays() {
        return acceptPays;
    }

    public String getCash() {
        return cash;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getDebt() {
        return debt;
    }

    public String getNonCash() {
        return nonCash;
    }

    public void setAcceptPays(String acceptPays) {
        this.acceptPays = acceptPays;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public void setNonCash(String nonCash) {
        this.nonCash = nonCash;
    }


}
