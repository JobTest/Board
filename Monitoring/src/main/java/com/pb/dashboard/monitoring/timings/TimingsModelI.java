package com.pb.dashboard.monitoring.timings;

import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;

/**
 * Created by vlad
 * Date: 20.11.14_10:04
 */
public interface TimingsModelI {

    public NavigatorModelAdapter getNavigator();

    public void setNavigator(NavigatorModelAdapter model);

    public FilterModelI getFilter();

    public void setFilter(FilterModelI model);

    public IndicatorsModelI getIndicators();

    public void setIndicators(IndicatorsModelI indicators);
}