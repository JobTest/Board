package com.pb.dashboard.monitoring.timings;

import com.pb.dashboard.monitoring.components.filter.FilterView;
import com.pb.dashboard.monitoring.components.navigator.NavigatorView;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsTabSheet;

/**
 * Created by vlad
 * Date: 20.11.14_10:03
 */
public interface TimingsControllerI {

    public NavigatorView getNavigatorView();

    public FilterView getFilterView();

    public IndicatorsTabSheet getIndicators();

    void update();
}
