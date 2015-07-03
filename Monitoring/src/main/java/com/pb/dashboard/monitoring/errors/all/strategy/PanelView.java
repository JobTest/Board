package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.monitoring.components.navigator.NavigatorView;
import com.pb.dashboard.monitoring.errors.all.filter.FilterView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by vlad
 * Date: 18.09.14
 */
public class PanelView extends VerticalLayout {

    private NavigatorView navigator;
    private FilterView filter;
    private VerticalLayout panel;

    private PanelControllerI controller;
    private PanelModelI model;

    public PanelView(PanelControllerI controller, PanelModelI model) {
        this.controller = controller;
        this.model = model;
    }

    public void init() {
        addComponent(navigator);
        addComponent(filter);
        setComponentAlignment(filter, Alignment.TOP_CENTER);
        panel = new VerticalLayout();
        addComponent(panel);
    }

    public void setNavigator(NavigatorView navigator) {
        this.navigator = navigator;
    }

    public void setFilter(FilterView filter) {
        this.filter = filter;
    }

    public void setPanelComponent(Component component) {
        panel.removeAllComponents();
        panel.addComponent(component);
    }
}