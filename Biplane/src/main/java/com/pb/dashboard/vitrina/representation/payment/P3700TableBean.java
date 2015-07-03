package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.List;

public class P3700TableBean extends TableBeanAbs implements Serializable {

    private static final long serialVersionUID = -2330237204682369883L;

    @Override
    public void getData(List<Metrics> metrics) {
        metrics = checkNull(metrics);
        resetInts();
        try {
            for (Metrics m : metrics) {
                if (m != null) {
                    if (m.getMetricKey() == country.get3700NonCash()) {
                        ints[1] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.get3700Debt()) {
                        ints[2] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.get3700FizLic()) {
                        ints[3] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.get3700UrLic()) {
                        ints[4] += m.getMetricValue();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        nonCash = ints[1] + "";
        debt = ints[2] + "";
        fizLic = ints[3] + "";
        urLic = ints[4] + "";
        if (!"".equals(nonCash)) {
            acceptPays = nonCash;
        }
    }
}
