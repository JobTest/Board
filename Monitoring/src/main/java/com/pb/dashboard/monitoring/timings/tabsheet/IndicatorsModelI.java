package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.observers.Observer;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 09.12.14_14:59
 */
public interface IndicatorsModelI {

    public FilterRange getFilterRange();

    public void setFilterRange(FilterRange filterRange);

    public boolean isReglament();

    public void setReglament(boolean reglament);

    public Map<InterfaceMetricI, List<MetricItem>> getTimingMetricsMap();

    public void setTimingMetricsMap(Map<InterfaceMetricI, List<MetricItem>> timingMetrics);

    public void setMetricSelected(int metricPkey);

    public InterfaceMetricI getMetricSelected();

    public void setLimit(InterfaceLimitI limit);

    public InterfaceLimitI getLimit();

    public void update();

    public void addObserver(Observer<IndicatorsModelI> observer);

    public void removeObserver(Observer<IndicatorsModelI> observer);
}
