package com.pb.dashboard.itdashbord.table.payment;

import com.pb.dashboard.itdashbord.db.ASEDBManager;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class TableBeanAbs {
    public ASEDBManager dm = ASEDBManager.getInstance();
    protected DecimalFormat pbformat = new DecimalFormat();

    public void fillLists() {
        dm.updateData();
    }

    protected String multyply(String first, String second) {
        return String.valueOf(valueOf(first) + valueOf(second));
    }

    protected String devide(String first, String second) {
        return String.valueOf(Math.round(Double.valueOf((valueOf(first) / valueOf(second)) * 100) * 100.0) / 100.0);
    }

    private Double valueOf(String value) {
        try {
            return Math.rint(Double.valueOf(value) * 100.0) / 100.0;
        } catch (NumberFormatException nfe) {
            return 0d;
        }
    }

    public int convertTime(Date date) {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
        int d = Integer.parseInt(df.format(date).toString());
        return d;
    }
}
