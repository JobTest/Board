package com.pb.dashboard.vitrina.representation.payment;

import com.pb.dashboard.vitrina.core.config.ASEDBManager;
import com.pb.dashboard.vitrina.core.config.information.QueryInf;
import com.pb.dashboard.vitrina.payment.data.Metrics;
import com.pb.dashboard.vitrina.statistics.Utilities;
import com.pb.dashboard.vitrina.statistics.byday.DayHourMetrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OldMetricsHolder {

    private int date;
    private ASEDBManager dm;

    private Map<Integer, List<Metrics>> oldMetricsByHour;

    public OldMetricsHolder(ASEDBManager dm) {
        this.dm = dm;
    }

    public List<Metrics> getMetrics(int date, int hour) {
        if (oldMetricsByHour == null || this.date != date) {
            this.date = date;
            List<DayHourMetrics> oldMetrics = dm.getByDayMetrics(QueryInf.CURRENT_DAY_METRICS, date);
            if (oldMetrics.size() == 0) oldMetrics = Utilities.getZeroDayHourMetrics();
            //if (metrics.size() == 0) oldMetrics = LogUtil.getRandDayHourMetrics(1);
            oldMetricsByHour = loadMetrics(oldMetrics);
        }
        return oldMetricsByHour.get(hour);
    }

    private Map<Integer, List<Metrics>> loadMetrics(List<DayHourMetrics> metrics) {
        Map<Integer, List<Metrics>> map = new HashMap<Integer, List<Metrics>>();
        for (DayHourMetrics metric : metrics) {
            int hour = metric.getMetricHour();
            if (!map.containsKey(hour)) map.put(hour, new ArrayList<Metrics>());
            Metrics legacyMetric = convertToLegacyMetric(metric);
            map.get(hour).add(legacyMetric);
        }
        return map;
    }


    private Metrics convertToLegacyMetric(DayHourMetrics metric) {
        Metrics legacyMetric = new Metrics();
        legacyMetric.setMetricKey(metric.getMetricKey());
        legacyMetric.setMetricValue(metric.getMetricValue());
        return legacyMetric;
    }

}
