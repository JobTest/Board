package com.pb.dashboard.monitoring.panel.manager;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class PaymentsPanelManager extends DPanelManager {

    private ButtonsPanelManager buttonsPanelManager;

    public PaymentsPanelManager(ButtonsPanelManager buttonsPanelManager) {
        this.buttonsPanelManager = buttonsPanelManager;
    }

    public Component getPaymentsPanel() {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setSpacing(true);
        panel.addComponents(getUkrPanel(), getRusPanel(), getGeoPanel());

        return panel;
    }

    private Component getUkrPanel() {
        Component leftBlock = getSubPanel(buttonsPanelManager.getPanel(Country.UKRAINE, Complex.BIPLANE_API2X),
                buttonsPanelManager.getPanel(Country.UKRAINE, Complex.TEMPLATES),
                buttonsPanelManager.getPanel(Country.UKRAINE, Complex.SERVER_AUTO_UPLOAD));
        Component rightBlock = getSubPanel(buttonsPanelManager.getPanel(Country.UKRAINE, Complex.DEBT),
                buttonsPanelManager.getPanel(Country.UKRAINE, Complex.REPORTS));
        Component ukr = getPanel(leftBlock, rightBlock);
        ukr.addStyleName("ukr");
        ukr.addStyleName("ukr-payments");

        return ukr;
    }

    private Component getRusPanel() {
        Component centerBlock = getSubPanel(buttonsPanelManager.getPanel(Country.RUSSIA, Complex.BIPLANE_API2X),
                buttonsPanelManager.getPanel(Country.RUSSIA, Complex.DEBT),
                buttonsPanelManager.getPanel(Country.RUSSIA, Complex.TEMPLATES));
        Component rus = getPanel(centerBlock);
        rus.addStyleName("rus");
        rus.addStyleName("rus-payments");

        return rus;
    }

    private Component getGeoPanel() {
        Component centerBlock = getSubPanel(buttonsPanelManager.getPanel(Country.GEORGIA, Complex.BIPLANE_API2X),
                buttonsPanelManager.getPanel(Country.GEORGIA, Complex.DEBT),
                buttonsPanelManager.getPanel(Country.GEORGIA, Complex.TEMPLATES));
        Component geo = getPanel(centerBlock);
        geo.addStyleName("geo");
        geo.addStyleName("geo-payments");

        return geo;
    }
}