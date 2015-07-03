package com.pb.dashboard.vitrina.timings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class TimingsView extends VerticalLayout implements View {

    public TimingsView() {
        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        BrowserFrame browser = new BrowserFrame(null, new ExternalResource("http://10.13.70.21:8093/"));
        browser.setSizeFull();
//        row.addComponent(createPanels());
        row.addComponent(browser);
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        BrowserFrame browser = new BrowserFrame(null, new ExternalResource("http://10.13.70.21:8093/"));
        browser.setSizeFull();
        panel.addComponent(browser);
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
