package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.navigator.NavigatorObserver;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.charts.ChartsControllerI;
import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 09.12.14_15:05
 */
public interface IndicatorsControllerI extends Observer<FilterModelI>, NavigatorObserver {

    public IndicatorsTabSheet getView();

    public ChartsControllerI getChartsController();

    public void setLinkManager(LinkManagerI linkManager);

    public void setTimingMetricsMap(Map<InterfaceMetricI, List<MetricItem>> filterItems);

    public void setFilterRange(FilterRange filterRange);

    public void setData(Map<InterfaceMetricI, List<MetricItem>> filterItems, FilterRange range);

    public void update();

    public void setLimit(InterfaceLimitI limit);
}
