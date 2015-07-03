package com.pb.dashboard.panelview;

import com.pb.dashboard.core.layout.DashboardElementsLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;

public class DashboardPanelView extends Panel implements View {

    private static final Logger log = Logger.getLogger(DashboardPanelView.class);
    private static final long serialVersionUID = -8050072612767915813L;
    private VerticalLayout mainLayout;

    @Override
    public void enter(ViewChangeEvent event) {
        Page.getCurrent().setTitle("IT Dashboard");
        setSizeFull();

        initMainLayout();
    }

    private void initMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setStyleName("dashboard");
        mainLayout.setSizeFull();
        addElements();

        setContent(mainLayout);
    }

    private void addElements() {
        DashboardElementsLayout elements = new DashboardElementsLayout();
        mainLayout.addComponent(elements);
        mainLayout.setComponentAlignment(elements, Alignment.MIDDLE_CENTER);
        mainLayout.setExpandRatio(elements, 20);
    }
}