package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.List;

public class Sub24TableBean extends TableBeanAbs implements Serializable {

    private static final long serialVersionUID = -4067597136588664937L;

    public void getData(List<Metrics> metrics) {
        metrics = checkNull(metrics);
        resetInts();
        try {
            for (Metrics m : metrics) {
                if (m != null) {
                    if (m.getMetricKey() == country.getP24NonCash()) {
                        ints[1] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getP24Debt()) {
                        ints[2] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getP24FizLic()) {
                        ints[3] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getP24UrLic()) {
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
