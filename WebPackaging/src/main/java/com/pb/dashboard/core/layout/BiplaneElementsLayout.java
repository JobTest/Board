package com.pb.dashboard.core.layout;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.panelviewlogic.PaymentsErrorCheker;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;

public class BiplaneElementsLayout extends ElementsLayout {

    private final static int COLUMNS = 3;
    private final static int ROWS = 3;

    public BiplaneElementsLayout() {
        super();
        setMargin(false);
        setSpacing(false);

        setColumns(COLUMNS);
        setRows(ROWS);
        setWidth("95%");
        setHeight("700px");

        addLink("home-co.png", Dashboard.PATH, 0, 0);
        PaymentsErrorCheker pec = new PaymentsErrorCheker();
        if (pec.isError()) {
            addLink("payments.png", Dashboard.Biplane.Payments.Pay.PATH, 2, 1);
        } else {
            addLink("payments-whith-alarm.gif", Dashboard.Biplane.Payments.Pay.PATH, 2, 1);
        }
        addLink("predmine.png", Dashboard.Biplane.Predmine.PATH, 1, 0);
        addLink("monitoring.png", Dashboard.Biplane.Monitoring.PATH, 2, 0);
        addLink("support.png", Dashboard.Biplane.Support.Errors.PATH, 0, 2);
        addLink("biplane-test.png", Dashboard.Biplane.Tests.AllTests.PATH, 1, 2);
        addLink("debt.png", Dashboard.Biplane.DebtLoad.Queue.PATH, 2, 2);
        ThemeResource resource = new ThemeResource("dash-img/logo.png");
        Image logo = new Image("", resource);
        logo.addStyleName("logo");
        addComponent(logo, 1, 1);
        setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
    }
}