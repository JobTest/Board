package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimit;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetric;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vlad
 * Date: 09.12.14_14:59
 */
public class IndicatorsModel implements IndicatorsModelI {

    private static final Logger log = Logger.getLogger(IndicatorsModel.class);
    private Observers<Observer, IndicatorsModelI> observers = new Observers<Observer, IndicatorsModelI>();

    private Map<InterfaceMetricI, List<MetricItem>> timingMetricsMap = new LinkedHashMap<>();
    private int metricPkey;
    private FilterRange filterRange = FilterRange.R10MIN;
    private InterfaceLimitI limit = new InterfaceLimit();
    private boolean reglament;

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public void setFilterRange(FilterRange filterRange) {
        this.filterRange = filterRange;
    }

    @Override
    public boolean isReglament() {
        return reglament;
    }

    @Override
    public void setReglament(boolean reglament) {
        this.reglament = reglament;
    }

    @Override
    public InterfaceLimitI getLimit() {
        return limit;
    }

    @Override
    public void update() {
        observers.notifyModified(this);
    }

    @Override
    public void setLimit(InterfaceLimitI limit) {
        this.limit = limit;
    }

    @Override
    public void addObserver(Observer<IndicatorsModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<IndicatorsModelI> observer) {
        observers.remove(observer);
    }

    @Override
    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsMap() {
        return timingMetricsMap;
    }

    @Override
    public void setTimingMetricsMap(Map<InterfaceMetricI, List<MetricItem>> timingMetricsMap) {
        this.timingMetricsMap = timingMetricsMap;
    }

    @Override
    public void setMetricSelected(int metricPkey) {
        this.metricPkey = metricPkey;
    }

    @Override
    public InterfaceMetricI getMetricSelected() {
        Set<InterfaceMetricI> metrics = timingMetricsMap.keySet();
        for (InterfaceMetricI metric : metrics) {
            if (metricPkey == metric.getPkey()) {
                return metric;
            }
        }

        log.warn("InterfaceMetric not found by metricPkey[" + metricPkey + "]");
        if (!metrics.isEmpty()) {
            return metrics.iterator().next();
        }
        return new InterfaceMetric();
    }
}