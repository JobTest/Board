package com.pb.dashboard.itdashbord.db;

public class QueryBuilder {

    public final int coulmnCnt = 10;
    public final String tableName = "dashboard.mass_pays_auth",
                        date = "date",
                        card = "card",
                        phone = "phone",
                        qrCode = "qr_code",
                        noAuth = "no_auth",
                        sms1 = "1sms",
                        sms3 = "3sms",
                        sms5 = "5sms",
                        sms10 = "10sms",
                        total = "total";

    public String selectAuthStatsAll() {
        return String.format("SELECT * FROM %s " +
                             "ORDER BY %s",
                             tableName,
                             date);
    }

    public int getCoulmnCnt() {
        return coulmnCnt;
    }

    public String getDate() {
        return date;
    }

    public String getCard() {
        return card;
    }

    public String getPhone() {
        return phone;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getNoAuth() {
        return noAuth;
    }

    public String getSms1() {
        return sms1;
    }

    public String getSms3() {
        return sms3;
    }

    public String getSms5() {
        return sms5;
    }

    public String getSms10() {
        return sms10;
    }

    public String getTotal() {
        return total;
    }
}
