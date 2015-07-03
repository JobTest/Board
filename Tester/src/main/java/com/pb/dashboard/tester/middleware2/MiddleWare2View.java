package com.pb.dashboard.tester.middleware2;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class MiddleWare2View extends VerticalLayout implements View {

    public MiddleWare2View() {
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
        BrowserFrame browser = new BrowserFrame(null, new ExternalResource("http://10.13.71.82/map/"));
        browser.setSizeFull();
        panel.addComponent(browser);
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
