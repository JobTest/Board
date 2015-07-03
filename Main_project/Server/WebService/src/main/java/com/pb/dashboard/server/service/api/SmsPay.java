package com.pb.dashboard.server.service.api;

import com.pb.dashboard.server.dao.entity.dashboard.MassPaysAuthEntity;

/**
 * Created by vlad
 * Date: 19.03.15_9:22
 */
public class SmsPay {

    private String date;
    private int sms1;
    private int sms3;
    private int sms5;
    private int sms10;

    public SmsPay(MassPaysAuthEntity item) {
        this.date = item.getDate();
        this.sms1 = item.getSms1();
        this.sms3 = item.getSms3();
        this.sms5 = item.getSms5();
        this.sms10 = item.getSms10();
    }

    public String getDate() {
        return date;
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
}
