package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.payment.data.Metrics;

import java.io.Serializable;
import java.util.List;

public class BassTableBean extends TableBeanAbs implements Serializable {

    private static final long serialVersionUID = -2467156877639155625L;

    @Override
    public void getData(List<Metrics> metrics) {
        metrics = checkNull(metrics);
        resetInts();
        try {
            for (Metrics m : metrics) {
                if (m != null) {
                    if (m.getMetricKey() == country.getBassCash()) {
                        ints[0] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getBassNonCash()) {
                        ints[1] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getBassDebt()) {
                        ints[2] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getBassFizLic()) {
                        ints[3] += m.getMetricValue();
                    } else if (m.getMetricKey() == country.getBassUrLic()) {
                        ints[4] += m.getMetricValue();
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e);
        }
        cash = ints[0] + "";
        nonCash = ints[1] + "";
        debt = ints[2] + "";
        fizLic = ints[3] + "";
        urLic = ints[4] + "";
        acceptPays = add(cash, nonCash);
    }

}
