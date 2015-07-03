package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 23.12.14_17:02
 */
public class StrategyBy10Min implements StrategyI {

    public static final int MILLISEC_IN_10MIN = 10 * 60 * 1000;
    private LoadData loadData;

    public StrategyBy10Min(LoadData loadData) {
        this.loadData = loadData;
        if (loadData == null) {
            this.loadData = new LoadData();
        }
    }

    @Override
    public int getReloadTimeMSec() {
        return MILLISEC_IN_10MIN;
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingsMetrics(List<InterfaceMetricI> metrics, LocalDate date, LocalDate first, LocalDate second) {
        return loadData.getTimingMetricsBy10Min(metrics, date);
    }

}