package com.pb.dashboard.server.dao.entity.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by vlad
 * Date: 18.03.15_14:35
 */
@Entity
@Table(name = "mass_pays_auth", schema = "", catalog = "dashboard")
public class MassPaysAuthEntity {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "card", nullable = false)
    private int card;

    @Column(name = "phone", nullable = false)
    private int phone;

    @Column(name = "qr_code", nullable = false)
    private int qrCode;

    @Column(name = "no_auth", nullable = false)
    private int noAuth;

    @Column(name = "1sms", nullable = false)
    private int sms1;

    @Column(name = "3sms", nullable = false)
    private int sms3;

    @Column(name = "5sms", nullable = false)
    private int sms5;

    @Column(name = "10sms", nullable = false)
    private int sms10;

    @Column(name = "total", nullable = false)
    private int total;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getCard() {
        return card;
    }

    public int getPhone() {
        return phone;
    }

    public int getQrCode() {
        return qrCode;
    }

    public int getNoAuth() {
        return noAuth;
    }

    public int getSms1() {
        return sms1;
    }

    public int getSms3() {
        return sms3;
    }

    public int getSms5() {
        return sms5;
    }

    public int getSms10() {
        return sms10;
    }

    public int getTotal() {
        return total;
    }
}
