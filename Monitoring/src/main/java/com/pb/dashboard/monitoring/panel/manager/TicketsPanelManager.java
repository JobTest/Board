package com.pb.dashboard.monitoring.panel.manager;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class TicketsPanelManager extends DPanelManager {

    private ButtonsPanelManager buttonsPanelManager;

    public TicketsPanelManager(ButtonsPanelManager buttonsPanelManager) {
        this.buttonsPanelManager = buttonsPanelManager;
    }

    public Component getTicketsPanel() {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setSpacing(true);
        panel.addComponents(getUkrPanel(), getRusPanel(), getGeoPanel());
        return panel;
    }

    private Component getUkrPanel() {
        Component ukr = getPanel(buttonsPanelManager.getPanel(Country.UKRAINE, Complex.IRBIS),
                buttonsPanelManager.getPanel(Country.UKRAINE, Complex.OCTOPUS));
        ukr.addStyleName("ukr");
        ukr.addStyleName("ukr-tickets");
        return ukr;
    }

    private Component getRusPanel() {
        Component rus = getPanel();
        rus.addStyleName("rus");

        return rus;
    }

    private Component getGeoPanel() {
        Component geo = getPanel();
        geo.addStyleName("geo");
        return geo;
    }
}