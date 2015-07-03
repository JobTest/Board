package com.pb.dashboard;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.monitoring.errors.panel.ErrorsView;
import com.pb.dashboard.monitoring.panel.MonitoringView;
import com.pb.dashboard.monitoring.sessions.SessionsView;
import com.pb.dashboard.monitoring.timings.TimingsView;
import com.pb.dashboard.panelview.*;
import com.pb.dashboard.panelview.biplane.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("dashboard")
@SuppressWarnings("serial")
public class DashboardUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);

        navigator.addView(Dashboard.PATH, DashboardPanelView.class);
        navigator.addView(Dashboard.Biplane.PATH, BiplanePanelView.class);
        navigator.addView(Dashboard.Templates.PATH, TemplatesPanelView.class);
        navigator.addView(Dashboard.Templates.Sessions.PATH, ErrorSessionView.class);
        navigator.addView(Dashboard.Tickets.PATH, TicketsPanelView.class);
        navigator.addView(Dashboard.Quality.PATH, QualityPanelView.class);

        navigator.addView(Dashboard.Biplane.Payments.PATH, PaymentsPanelView.class);
        navigator.addView(Dashboard.Biplane.Predmine.PATH, PredminePanelView.class);
        navigator.addView(Dashboard.Biplane.Tests.PATH, TesterPanelView.class);
        navigator.addView(Dashboard.Biplane.DebtLoad.PATH, DebtLoadPanelView.class);

        // Monitoring
        navigator.addView(Dashboard.Biplane.Monitoring.PATH, MonitoringView.class);
        navigator.addView(Dashboard.Biplane.Monitoring.Timings.PATH, new TimingsView());
        navigator.addView(Dashboard.Biplane.Monitoring.Errors.PATH, new ErrorsView());
        navigator.addView(Dashboard.Biplane.Monitoring.Sessions.PATH, new SessionsView());

        // Biplane support
        navigator.addView(Dashboard.Biplane.Support.PATH, new SupportPanelView());

        navigator.setErrorView(DashboardPanelView.class);
    }
}