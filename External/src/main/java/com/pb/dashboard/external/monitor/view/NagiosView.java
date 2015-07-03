package com.pb.dashboard.external.monitor.view;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class NagiosView extends VerticalLayout {

    private static final String NAGIOS_TITLE = "Внутренний мониторинг(Nagios)";
    private static final String NAGIOS_URL = "http://biplanevit:vitrum43s@10.13.99.72/nagios/cgi-bin/status.cgi?hostgroup=all&style=summary";

    public NagiosView() {
        addStyleName("support-view");
        addComponent(buildContent());
    }

    private Component buildContent() {
        HorizontalLayout dataView = new HorizontalLayout();
        dataView.setSizeFull();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);

        BrowserFrame browser = new BrowserFrame("", new ExternalResource(NAGIOS_URL));
        browser.addStyleName("nagios-panel");
        browser.setCaption(NAGIOS_TITLE);
        browser.setSizeFull();
        dataView.addComponents(browser);
        return dataView;
    }
}
