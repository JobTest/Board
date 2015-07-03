package com.pb.dashboard.monitoring.errors.all.panel;

import com.pb.dashboard.monitoring.components.navigator.NavigatorController;
import com.pb.dashboard.monitoring.errors.all.filter.FilterController;
import com.pb.dashboard.monitoring.errors.all.filter.FilterControllerI;
import com.pb.dashboard.monitoring.errors.all.filter.FilterModelBuilder;
import com.pb.dashboard.monitoring.errors.all.filter.FilterModelI;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelBuilder;
import com.pb.dashboard.monitoring.errors.all.strategy.*;
import org.apache.log4j.Logger;

import java.util.Map;

public class ErrorsManager {

    private static final Logger log = Logger.getLogger(ErrorsManager.class);

    private Map<String, String> params;
    private PanelControllerI panelController;

    public ErrorsManager(Map<String, String> params) {
        this.params = params;
        init();
    }

    private void init() {
        PanelModelI model = new PanelModel();
        panelController = new PanelController(model);
        panelController.setFilterController(createFilter());
        panelController.setNavigatorController(createNavigator());
        panelController.init();
    }

    private NavigatorController createNavigator() {
        NavigatorModelAdapter navigatorModel = NavigatorModelBuilder.build(params);
        return new NavigatorController(navigatorModel);
    }

    private FilterControllerI createFilter() {
        FilterModelI filterModel = FilterModelBuilder.build();
        return new FilterController(filterModel);
    }

    public PanelView getView() {
        return panelController.getView();
    }
}