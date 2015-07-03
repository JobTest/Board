package com.pb.dashboard.panelviewlogic;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.util.List;

public class PaymentsErrorCheker {
    private ASEDBManager dm = ASEDBManager.getInstance();
    private int limit = 10;// лимит не выгруженных платежей

    public PaymentsErrorCheker() {
    }

    public boolean isError() {
        List<Metrics> noneHourMetrics = dm.noneHourMetrics;
        for (Metrics m : noneHourMetrics) {
            if (m.getMetricKey() == 29) {
                if (limit<m.getMetricValue())return false;
            }
        }
        return true;
    }
}
