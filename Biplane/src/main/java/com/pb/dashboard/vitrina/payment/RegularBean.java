package com.pb.dashboard.vitrina.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.information.MetricsInf;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.payment.data.Regular;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RegularBean implements Serializable {

    private List<Regular> regulars;
    private List<Metrics> noneHourMetrics;
    private DecimalFormat pbformat;
    private int comeIn = -1;
    private int workIn = -1;

    public RegularBean() {
        fillLists();
        generateData();
    }

    public List<Regular> getRegulars() {
        return regulars;
    }

    private void generateData() {
        regulars = new ArrayList<Regular>();

        for (Metrics m : noneHourMetrics) {

            if (m.getMetricKey() == MetricsInf.REGULAR_RECEIVED) {
                comeIn = m.getMetricValue();
            } else if (m.getMetricKey() == MetricsInf.REGULAR_PROCESSED) {
                workIn = m.getMetricValue();
            }
        }
        regulars.add(new Regular(pbformat.format(comeIn), pbformat.format(workIn)));
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
