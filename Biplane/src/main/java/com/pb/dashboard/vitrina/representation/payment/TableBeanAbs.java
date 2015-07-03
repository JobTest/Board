package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.core.country.CountryGEO;
import com.pb.dashboard.vitrina.core.country.CountryI;
import com.pb.dashboard.vitrina.core.country.CountryRU;
import com.pb.dashboard.vitrina.core.country.CountryUA;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class TableBeanAbs {

    protected static final Logger LOG = Logger.getLogger(TableBeanAbs.class);

    public List<Metrics> metrics;
    public List<Metrics> lastMetrics;
    public List<Metrics> last2HourMetrics;
    public List<Metrics> last3HourMetrics;
    public List<Metrics> sumMetrics;
    public int MILLIS_IN_HOUR = 1000 * 60 * 60;
    public String nonCash;
    public String debt;
    public String acceptPays;
    public String cash;
    public String fizLic;
    public String urLic;
    public ASEDBManager dm = ASEDBManager.getInstance();
    private Date period;
    protected CountryI country;
    protected ConfigManager manager;
    protected int[] ints;

    /* Old metrics for comparison */
    public List<Metrics> old1HourBackMetrics;
    public List<Metrics> old2HourBackMetrics;
    public List<Metrics> old3HourBackMetrics;
    public String oldAcceptPays;
    public String oldCash;
    public String oldNonCash;
    public String oldDebt;
    public List<Object[]> monthOldPayments;

    public void setConfigManager(ConfigManager manager) {
        this.manager = manager;
    }

    public List<Object[]> getPayments() {
        fillLists();
        reloadCountry();
        monthOldPayments = populateOldPayments();
        return populatePayments();
    }

    public String getLastTime(int hour) {
        Date date = new Date();
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("HH", locale);
        int d = Integer.parseInt(df.format(date.getTime() - hour));
        return d + ":00" + "-" + (d + 1) + ":00";
    }

    protected void resetInts() {
        ints = new int[5];
    }

    public void fillLists() {
        if (period == null) {
            metrics = dm.metrics;
            lastMetrics = dm.lastMetrics;
            last2HourMetrics = dm.last2HourMetrics;
            last3HourMetrics = dm.last3HourMetrics;
            sumMetrics = dm.sumMetrics;

            old1HourBackMetrics = dm.old1HourBackMetrics;
            old2HourBackMetrics = dm.old2HourBackMetrics;
            old3HourBackMetrics = dm.old3HourBackMetrics;
        } else {
            LOG.info("Collecting data for: " + period);
            sumMetrics = dm.getMetricsByPeriod(
                    QueryInf.ALL_METRICS_SUMM_BY_DAY, convertTime(period));
            metrics = dm.getMetricsByPeriod(QueryInf.ALL_METRICS_BYDAY,
                    convertTime(period));
            lastMetrics = dm.getMetricsByPeriod(QueryInf.ALL_METRICS_Pre_BYDAY,
                    convertTime(period));
            last2HourMetrics = dm.getMetricsByPeriod(
                    QueryInf.ALL_METRICS_2Pre_BYDAY, convertTime(period));
            last3HourMetrics = dm.getMetricsByPeriod(
                    QueryInf.ALL_METRICS_3Pre_BYDAY, convertTime(period));
        }
    }

    public List<Object[]> populatePayments() {
        List<Object[]> payment = new ArrayList<Object[]>();
        getAmountByType(1);
        payment.add(new Object[]{getLastTime(0), acceptPays, cash, nonCash,
                debt, fizLic, urLic});
        getAmountByType(2);
        payment.add(new Object[]{getLastTime(MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        getAmountByType(3);
        payment.add(new Object[]{getLastTime(2 * MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        getAmountByType(4);
        payment.add(new Object[]{getLastTime(3 * MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        return payment;
    }

    public List<Object[]> populateOldPayments() {
        List<Object[]> payment = new ArrayList<Object[]>();
        getAmountByType(5);
        payment.add(new Object[]{getLastTime(MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        getAmountByType(6);
        payment.add(new Object[]{getLastTime(2 * MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        getAmountByType(7);
        payment.add(new Object[]{getLastTime(3 * MILLIS_IN_HOUR), acceptPays,
                cash, nonCash, debt, fizLic, urLic});
        return payment;
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
                break;
        }
    }

    public void getAmountByType(int type) {
        clearData();
        if (type == 1) getData(metrics);
        else if (type == 2) getData(lastMetrics);
        else if (type == 3) getData(last2HourMetrics);
        else if (type == 4) getData(last3HourMetrics);
        else if (type == 5) getData(old1HourBackMetrics);
        else if (type == 6) getData(old2HourBackMetrics);
        else if (type == 7) getData(old3HourBackMetrics);
    }

    public Object[] getSumMetrics() {
        reloadCountry();
        clearData();
        if (sumMetrics == null) {
            sumMetrics = dm.sumMetrics;
        }
        getData(sumMetrics);
        return new Object[]{"Всего", acceptPays, cash, nonCash, debt, fizLic,
                urLic};
    }

    protected abstract void getData(List<Metrics> metrics);

    public void clearData() {
        cash = "0";
        nonCash = "0";
        debt = "0";
        acceptPays = "0";
        fizLic = "0";
        urLic = "0";

        oldAcceptPays = "0";
        oldCash = "0";
        oldNonCash = "0";
        oldDebt = "0";
    }

    protected String add(String first, String second) {
        return String.valueOf(valueOf(first) + valueOf(second));
    }

    private Integer valueOf(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public int convertTime(Date date) {
        Locale locale = new Locale("ru");
        DateFormat df = new SimpleDateFormat("yyyyMMdd", locale);
        return Integer.parseInt(df.format(date));
    }

    protected List<Metrics> nullStateCheker(List<Metrics> metrics) {
        if (metrics == null) {
            return new ArrayList<Metrics>();
        }
        return metrics;
    }

    protected List<Metrics> checkNull(List<Metrics> metrics) {
        if (metrics == null)
            return new ArrayList<Metrics>();
        return metrics;
    }
}
