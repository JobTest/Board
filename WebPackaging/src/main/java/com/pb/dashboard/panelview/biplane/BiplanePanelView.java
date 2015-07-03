package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.core.layout.BiplaneElementsLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class BiplanePanelView extends Panel implements View {

    private VerticalLayout mainLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("IT Dashboard");
        setSizeFull();

        initMainLayout();
    }

    private void initMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setStyleName("dashboard-biplane");
        mainLayout.setSizeFull();
        addElements();

        setContent(mainLayout);
    }

    private void addElements() {
        BiplaneElementsLayout elements = new BiplaneElementsLayout();
        mainLayout.addComponent(elements);
        mainLayout.setComponentAlignment(elements, Alignment.MIDDLE_CENTER);
        mainLayout.setExpandRatio(elements, 20);
    }
}