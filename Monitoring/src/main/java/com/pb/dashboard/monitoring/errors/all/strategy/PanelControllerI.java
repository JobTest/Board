package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.monitoring.components.navigator.NavigatorControllerI;
import com.pb.dashboard.monitoring.errors.all.filter.FilterControllerI;

/**
 * Created by vlad
 * Date: 05.01.15_14:36
 */
public interface PanelControllerI {

    public void init();

    public PanelView getView();

    public void setNavigatorController(NavigatorControllerI navigatorController);

    public void setFilterController(FilterControllerI filterController);

}
