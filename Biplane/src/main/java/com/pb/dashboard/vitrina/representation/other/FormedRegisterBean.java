package com.pb.dashboard.vitrina.representation.other;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormedRegisterBean implements Serializable {

    private List<Metrics> noneHourMetrics;
    private ASEDBManager dm = ASEDBManager.getInstance();
    private int send = 0;
    private int notSend = 0;

    public List<Object[]> getRegs() {
        noneHourMetrics = dm.noneHourMetrics;
        return generateData();
    }

    private List<Object[]> generateData() {
        List<Object[]> regs = new ArrayList<Object[]>();
        getAmountByType(0);
        regs.add(new Object[]{"Реестры", String.valueOf(send), String.valueOf(notSend)});
        getAmountByType(1);
        regs.add(new Object[]{"Проводок  Киевский ГИВЦ", String.valueOf(send), String.valueOf(notSend)});
        getAmountByType(2);
        regs.add(new Object[]{"Проводок Винницкий ИРЦ", String.valueOf(send), String.valueOf(notSend)});
        return regs;
    }

    private void getAmountByType(int type) {
        if (type == 0) {
            getData(noneHourMetrics, 32, 31);
        } else if (type == 1) {
            getData(noneHourMetrics, 34, 33);
        } else if (type == 2) {
            getData(noneHourMetrics, 36, 35);
        }
    }

    private void getData(List<Metrics> metrics, int first, int second) {
        send = 0;
        notSend = 0;
        for (Metrics m : metrics) {
            if (m.getMetricKey() == first) {
                send = m.getMetricValue();
            } else if (m.getMetricKey() == second) {
                notSend = m.getMetricValue();
            }
        }
    }
}
