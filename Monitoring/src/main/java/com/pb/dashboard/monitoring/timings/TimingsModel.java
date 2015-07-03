package com.pb.dashboard.monitoring.timings;

import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;

public class TimingsModel implements TimingsModelI {

    private FilterModelI filter;
    private NavigatorModelAdapter navigator;
    private IndicatorsModelI indicators;

    public TimingsModel() {
    }

    public FilterModelI getFilter() {
        return filter;
    }

    @Override
    public void setFilter(FilterModelI model) {
        this.filter = model;
    }

    public NavigatorModelAdapter getNavigator() {
        return navigator;
    }

    public void setNavigator(NavigatorModelAdapter navigator) {
        this.navigator = navigator;
    }

    public IndicatorsModelI getIndicators() {
        return indicators;
    }

    public void setIndicators(IndicatorsModelI indicators) {
        this.indicators = indicators;
    }
}
