package com.pb.dashboard.vitrina.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.information.MetricsInf;
import com.pb.dashboard.vitrina.payment.data.Debt;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DebtBean implements Serializable {

    private List<Metrics> noneHourMetrics;
    private List<Debt> debt;
    private int accepted = 0;
    private int notProcessed = 0;
    private DecimalFormat pbformat;

    public DebtBean() {
        fillLists();
        generateData();
    }

    public List<Debt> getDebt() {
        return debt;
    }

    private void generateData() {
        debt = new ArrayList<Debt>();
        getAmountByType(0);
        debt.add(new Debt("Выгрузка во внешние комплексы", pbformat.format(accepted), pbformat.format(notProcessed)));
        getAmountByType(1);
        debt.add(new Debt("off-line задолженность", pbformat.format(accepted), pbformat.format(notProcessed)));
    }

    public void updateData() {
        fillLists();
        debt.clear();
        generateData();
    }

    private void fillLists() {
        ASEDBManager dm = ASEDBManager.getInstance();
        noneHourMetrics = dm.noneHourMetrics;
        pbformat = dm.setDecimalFormat();

    }

    private void getAmountByType(int type) {
        if (type == 0) {
            getData(noneHourMetrics);
        } else if (type == 1) {
            getData(noneHourMetrics);
        }
    }

    private void getData(List<Metrics> metrics) {
        for (Metrics m : metrics) {
            if (m.getMetricKey() == MetricsInf.DEBT_ACCEPTED) {
                accepted = m.getMetricValue();
            } else if (m.getMetricKey() == MetricsInf.DEBT_NOT_PROCESSED) {
                notProcessed = m.getMetricValue();
            }
        }
    }
}
