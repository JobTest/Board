package com.pb.dashboard.vitrina.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.information.MetricsInf;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.payment.data.PVN;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PVNBean implements Serializable {

    private List<PVN> pvns;
    private List<Metrics> noneHourMetrics;
    private DecimalFormat pbformat;
    private int accepted = 0;
    private int processed = 0;

    public PVNBean() {
        fillLists();
        generateData();
    }

    public List<PVN> getPvns() {
        return pvns;
    }

    private void generateData() {
        pvns = new ArrayList<PVN>();

        for (Metrics m : noneHourMetrics) {
            if (m.getMetricKey() == MetricsInf.PVN_ACCEPTED) {
                accepted = m.getMetricValue();
            } else if (m.getMetricKey() == MetricsInf.PVN_PROCESSED) {
                processed = m.getMetricValue();
            }
        }
        pvns.add(new PVN(pbformat.format(accepted), pbformat.format(processed)));
    }

    private void fillLists() {
        ASEDBManager dm = ASEDBManager.getInstance();
        noneHourMetrics = dm.noneHourMetrics;
        pbformat = dm.setDecimalFormat();
    }

    public void updateData() {
        fillLists();
        generateData();
    }
}
