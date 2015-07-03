package com.pb.dashboard.monitoring.timings;

import com.github.wolfie.refresher.Refresher;
import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.monitoring.components.filter.FilterView;
import com.pb.dashboard.monitoring.components.navigator.NavigatorView;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsTabSheet;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;

public class TimingsView extends DAbstractView {

    private TimingsControllerI controller;
    private TimingsModelI model;

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }

    private void init() {
        if (controller == null) {
            controller = new TimingsController(this);
            initView();
        } else {
            controller.update();
        }
    }

    public void initView() {
        Page.getCurrent().setTitle("Тайминги");
        addStyleName("timings-view");
        initFilter();
        initNavigator();
        initTabSheet();
        navigationBar.setBack(Dashboard.Biplane.Monitoring.PATH);
    }

    private void initFilter() {
        FilterView filterView = controller.getFilterView();
        content.addComponent(filterView);
        content.setComponentAlignment(filterView, Alignment.TOP_CENTER);
    }

    private void initNavigator() {
        NavigatorView navigatorView = controller.getNavigatorView();
        navigatorView.setMargin(true);
        content.addComponent(navigatorView);
        content.setComponentAlignment(navigatorView, Alignment.TOP_CENTER);
    }

    public void addRefresher(Refresher refresher) {
        addExtension(refresher);
    }

    private void initTabSheet() {
        IndicatorsTabSheet indicators = controller.getIndicators();
        indicators.setSizeFull();
        content.addComponent(indicators);
        content.setExpandRatio(indicators, 1f);
    }

    public void setModel(TimingsModelI model) {
        this.model = model;
    }

    public TimingsModelI getModel() {
        return model;
    }
}