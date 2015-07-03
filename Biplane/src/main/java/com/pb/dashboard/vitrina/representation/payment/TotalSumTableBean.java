package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.core.country.CountryUA;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TotalSumTableBean implements Serializable {
    private static final long serialVersionUID = -8402028808474271269L;

    public List<Metrics> sumMetrics;
    public ASEDBManager dm = ASEDBManager.getInstance();
    public String nonCash = "0";
    public String cash = "0";
    public String debt = "0";
    public String total = "0";
    public String fizLic = "0";
    public String urLic = "0";
    protected CountryI country;
    protected ConfigManager manager;

    public void setConfigManager(ConfigManager manager) {
        this.manager = manager;
    }

    public void fillLists() {
        sumMetrics = dm.sumMetrics;
    }

    public List<Object[]> populatePayments() {
        fillLists();
        reloadCountry();
        List<Object[]> payment = new ArrayList<Object[]>();
        getData(sumMetrics);
        payment.add(new Object[]{getLastTime(0), total, cash, nonCash, debt,
                fizLic, urLic});
        return payment;
    }

    public void getData(List<Metrics> metrics) {
        cash = "0";
        nonCash = "0";
        debt="0";
        fizLic = "0";
        urLic="0";
        for (Metrics m : metrics) {
            int key = m.getMetricKey();
            if (key == country.getKassaCash()
                    || key == country.getBassCash()) {
                cash = (valueOf(cash) + m.getMetricValue()) + "";
            }
            if (key == country.getKassaNonCash()
                    || key == country.getBassNonCash()
                    || key == country.getP24NonCash()
                    || key == country.get3700NonCash()) {
                nonCash = (valueOf(nonCash) + m.getMetricValue()) + "";
            }
            if (key == country.getKassaDebt()
                    || key == country.getBassDebt()
                    || key == country.getP24Debt()
                    || key == country.get3700Debt()) {
                debt = (valueOf(debt) + m.getMetricValue()) + "";
            }
            if (key == country.getKassaFizLic()
                    || key == country.getBassFizLic()
                    || key == country.getP24FizLic()
                    || key == country.get3700FizLic()) {
                fizLic = (valueOf(fizLic) + m.getMetricValue()) + "";
            }
            if (key == country.getKassaUrLic()
                    || key == country.getBassUrLic()
                    || key == country.getP24UrLic()
                    || key == country.get3700UrLic()) {
                urLic = (valueOf(urLic) + m.getMetricValue()) + "";
            }
        }
        total = (valueOf(cash) + valueOf(nonCash)) + "";
    }

    public String getLastTime(int hour) {
        Date date = new Date();
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("HH", locale);
        int d = Integer.parseInt(df.format(date.getTime() - hour).toString());
        return "00:00" + "-" + (d + 1) + ":00";
    }

    private Integer valueOf(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private void reloadCountry() {
        switch (manager.getLang()) {
            case UA:
                country = new CountryUA();
                break;
            case RU:
                country = new CountryRU();
                break;
            case GEO:
                country = new CountryGEO();
                break;
            default:
                country = new CountryUA();
        }
    }
}