package com.pb.dashboard.vitrina.payment.data;

import java.io.Serializable;

public class PaymentDetailsSumm implements Serializable {

    private int currentTime;
    private int acceptPays;
    private int cash;
    private int nonCash;
    private int debt;

    public PaymentDetailsSumm() {
    }

    public PaymentDetailsSumm(int currentTime, int acceptPays, int cash, int nonCash, int debt) {
        this.currentTime = currentTime;
        this.acceptPays = acceptPays;
        this.cash = cash;
        this.nonCash = nonCash;
        this.debt = debt;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getAcceptPays() {
        return acceptPays;
    }

    public void setAcceptPays(int acceptPays) {
        this.acceptPays = acceptPays;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getNonCash() {
        return nonCash;
    }

    public void setNonCash(int nonCash) {
        this.nonCash = nonCash;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }


}
