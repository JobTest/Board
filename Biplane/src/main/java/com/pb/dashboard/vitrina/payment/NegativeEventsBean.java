package com.pb.dashboard.vitrina.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.payment.data.NegativeEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NegativeEventsBean implements Serializable {

    private List<Metrics> negEventMetrics;
    private List<NegativeEvent> danglingPayment;
    private List<NegativeEvent> rejectedPosting;
    private List<NegativeEvent> debtWithError;
    private List<NegativeEvent> tableError;
    private List<NegativeEvent> ODBSpider;
    private List<NegativeEvent> ODBSyland;

    public NegativeEventsBean() {
        init();
        fillLists();
        generateData();
        //randomData();
    }

    private void init() {
        danglingPayment = new ArrayList<NegativeEvent>();
        rejectedPosting = new ArrayList<NegativeEvent>();
        debtWithError = new ArrayList<NegativeEvent>();
        tableError = new ArrayList<NegativeEvent>();
        ODBSpider = new ArrayList<NegativeEvent>();
        ODBSyland = new ArrayList<NegativeEvent>();
    }

    private void fillLists() {
        ASEDBManager asedbm = ASEDBManager.getInstance();
        negEventMetrics = asedbm.noneHourMetrics;
    }

    private void generateData() {
        for (Metrics metrics : negEventMetrics) {
            switch (metrics.getMetricKey()) {
                case 48:
                    danglingPayment.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                case 49:
                    rejectedPosting.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                case 50:
                    debtWithError.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                case 51:
                    tableError.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                case 52:
                    ODBSpider.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                case 53:
                    ODBSyland.add(new NegativeEvent(metrics.getMetricKey(), metrics.getMetricName(), metrics.getMetricValue()));
                    break;
                default:
                    break;
            }
        }
    }

    public void updateData() {
        init();
        fillLists();
        generateData();
        //  randomData();
    }


    private void randomData() {
        Random rn = new Random();
        danglingPayment.add(new NegativeEvent(0, "Зависшие платежи ПК Биплан", rn.nextInt(100)));
        rejectedPosting.add(new NegativeEvent(0, "Забракованные проводки", rn.nextInt(100)));
        debtWithError.add(new NegativeEvent(0, "Поиск задолженности, с ошибкой или без результата", rn.nextInt(100)));
        tableError.add(new NegativeEvent(0, "Ошибки в таблице error_log", rn.nextInt(100)));
        ODBSpider.add(new NegativeEvent(0, "Отставание репликации в ОДБ Spider", rn.nextInt(100)));
        ODBSyland.add(new NegativeEvent(0, "Отставание репликации в ОДБ Syland", rn.nextInt(100)));

    }

    public List<NegativeEvent> getDanglingPayment() {
        return danglingPayment;
    }

    public List<NegativeEvent> getRejectedPosting() {
        return rejectedPosting;
    }

    public List<NegativeEvent> getDebtWithError() {
        return debtWithError;
    }

    public List<NegativeEvent> getTableError() {
        return tableError;
    }

    public List<NegativeEvent> getODBSpider() {
        return ODBSpider;
    }

    public List<NegativeEvent> getODBSyland() {
        return ODBSyland;
    }
}
