package com.pb.dashboard.server.service.api;

import com.pb.dashboard.server.dao.entity.dashboard.MassPaysAuthEntity;

/**
 * Created by vlad
 * Date: 18.03.15_14:56
 */
public class AuthPay {

    private String date;
    private int tco;
    private int qrCode;
    private int noAuth;
    private double persNoAuth;
    private int card;
    private int phone;
    private double persPhone;

    public AuthPay(MassPaysAuthEntity entity) {
        this.date = entity.getDate();
        this.tco = entity.getTotal();
        this.qrCode = entity.getQrCode();
        this.noAuth = entity.getNoAuth();
        this.persNoAuth = rintTohundredths(100.0 * noAuth / tco);
        this.card = entity.getCard();
        this.phone = entity.getPhone();
        this.persPhone = rintTohundredths(100.0 * phone / tco);
    }

    private double rintTohundredths(double number) {
        return Math.rint(number * 100) / 100;
    }

    public String getDate() {
        return date;
    }

    public int getTco() {
        return tco;
    }

    public int getQrCode() {
        return qrCode;
    }

    public int getNoAuth() {
        return noAuth;
    }

    public double getPersNoAuth() {
        return persNoAuth;
    }

    public int getCard() {
        return card;
    }

    public int getPhone() {
        return phone;
    }

    public double getPersPhone() {
        return persPhone;
    }
}
