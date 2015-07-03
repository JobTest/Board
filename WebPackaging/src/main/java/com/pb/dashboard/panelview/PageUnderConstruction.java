package com.pb.dashboard.panelview;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class PageUnderConstruction extends VerticalLayout implements View {

    public PageUnderConstruction() {
        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        row.addComponent(createPanels());
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        Component content = createContent();
        panel.addComponent(content);
        return panel;
    }

    private Component createContent() {
        HorizontalLayout dataView = new HorizontalLayout();
        dataView.setSizeFull();
        dataView.setStyleName("data-view");
        ThemeResource resource = new ThemeResource("dash-img/under-construction.jpg");
        Image underConstr = new Image("", resource);
        dataView.addComponent(underConstr);
        dataView.setComponentAlignment(underConstr, Alignment.MIDDLE_CENTER);
        return dataView;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
