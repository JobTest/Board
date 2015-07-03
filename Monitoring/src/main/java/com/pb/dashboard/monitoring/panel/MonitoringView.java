package com.pb.dashboard.monitoring.panel;

import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.monitoring.panel.manager.PanelManager;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MonitoringView extends DAbstractView {

    public static final String BIPLANE = "Биплан";
    public static final String PAYMENTS = "Прием платежей";
    public static final String TICKETS = "Продажа билетов";
    private PanelManager panelManager = new PanelManager();

    private MonitoringControllerI controller;
    private MonitoringModelI model;

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }

    private void init() {
        if (controller == null) {
            controller = new MonitoringController();
            model = controller.getModel();
            initView();
        }
    }

    private void initView() {
        Page.getCurrent().setTitle(BIPLANE);
        addStyleName("timings-view");

        content.addComponent(getCountryPanels());
    }

    private Component getCountryPanels() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.addStyleName("countries");

        Label paymentsTitle = new Label(PAYMENTS);
        paymentsTitle.setSizeUndefined();
        paymentsTitle.addStyleName("title");
        Label ticketsTitle = new Label(TICKETS);
        ticketsTitle.setSizeUndefined();
        ticketsTitle.addStyleName("title");

        addComponent(content, panelManager.getCountryHeaders());
        addComponent(content, paymentsTitle, panelManager.getPaymentsPanel());
        addComponent(content, ticketsTitle, panelManager.getTicketsPanel());

        return content;
    }

    private void addComponent(VerticalLayout content, Component... components) {
        content.addComponents(components);
        for (Component component : components) {
            content.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
        }
    }
}