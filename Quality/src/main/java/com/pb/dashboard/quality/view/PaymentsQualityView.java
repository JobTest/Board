package com.pb.dashboard.quality.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class PaymentsQualityView extends VerticalLayout implements View {

    private static final long serialVersionUID = -2400615020115897779L;

    public PaymentsQualityView() {
        setSizeFull();
        addStyleName("quality-view");
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
        BrowserFrame browser = new BrowserFrame("",
                new ExternalResource("https://mypayments.privatbank.ua/statistic-dev.html"));
        browser.setSizeFull();
        panel.addComponent(browser);
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
