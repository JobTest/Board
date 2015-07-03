package com.pb.dashboard.vitrina.representation.other;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormedProvodBean implements Serializable {

    private ASEDBManager dm = ASEDBManager.getInstance();
    private List<Object[]> entries;
    private List<Metrics> noneHourMetrics;
    private int unloaded = 0;
    private int held = 0;
    private int pending = 0;
    private int notUnloaded = 0;
    private int entriesLic = 0;
    private int all = 0;

    public List<Object[]> getData() {
        noneHourMetrics = dm.noneHourMetrics;
        return generateData();
    }

    public Object[] getSum() {
        return new Object[]{"Всего", String.valueOf(all)};
    }

    private List<Object[]> generateData() {
        List<Object[]> list = new ArrayList<Object[]>();
        clearData();
        for (Metrics m : noneHourMetrics) {
            if (m.getMetricKey() == 26) {
                unloaded = m.getMetricValue();
            } else if (m.getMetricKey() == 27) {
                held = m.getMetricValue();
            } else if (m.getMetricKey() == 28) {
                pending = m.getMetricValue();
            } else if (m.getMetricKey() == 29) {
                notUnloaded = m.getMetricValue();
            } else if (m.getMetricKey() == 30) {
                entriesLic = m.getMetricValue();
            }
        }
        all = held + entriesLic;
        list.add(new Object[]{
                String.valueOf(unloaded),
                String.valueOf(held),
                String.valueOf(pending),
                String.valueOf(notUnloaded),
                String.valueOf(entriesLic)});
        return list;
    }

    private void clearData() {
        unloaded = 0;
        held = 0;
        pending = 0;
        notUnloaded = 0;
        entriesLic = 0;
        all = 0;
    }
}
