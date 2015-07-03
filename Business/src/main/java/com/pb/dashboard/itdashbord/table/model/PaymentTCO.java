package com.pb.dashboard.itdashbord.table.model;

import java.io.Serializable;

public class PaymentTCO implements Serializable {

    private static final long serialVersionUID = 1449142595099070189L;
    String date;
    String dayOfWeek;
    String tco;
    String myPayment;
    String perCent;


    public PaymentTCO(String date, String dayOfWeek, String tco, String myPayment) {
        super();
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.tco = tco;
        this.myPayment = myPayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTco() {
        return tco;
    }

    public void setTco(String tco) {
        this.tco = tco;
    }

    public String getMyPayment() {
        return myPayment;
    }

    public void setMyPayment(String myPayment) {
        this.myPayment = myPayment;
    }

    public String getPerCent() {
        return perCent;
    }

    public void setPerCent(String perCent) {
        this.perCent = perCent;
    }
}
