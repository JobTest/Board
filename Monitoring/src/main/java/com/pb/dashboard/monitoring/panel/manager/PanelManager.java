package com.pb.dashboard.monitoring.panel.manager;

import com.vaadin.ui.Component;

public class PanelManager {

    private CountryHeaderManager headerManager = new CountryHeaderManager();
    private ButtonsPanelManager buttonsPanelManager = new ButtonsPanelManager();
    private PaymentsPanelManager paymentsPanelManager = new PaymentsPanelManager(buttonsPanelManager);
    private TicketsPanelManager ticketsPanelManager = new TicketsPanelManager(buttonsPanelManager);

    public Component getCountryHeaders() {
        return headerManager.getCountryHeaders();
    }

    public Component getTicketsPanel() {
        return ticketsPanelManager.getTicketsPanel();
    }

    public Component getPaymentsPanel() {
        return paymentsPanelManager.getPaymentsPanel();
    }

}
