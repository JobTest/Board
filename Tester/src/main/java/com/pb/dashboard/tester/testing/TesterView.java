package com.pb.dashboard.tester.testing;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class TesterView extends VerticalLayout implements View {


    public TesterView() {
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
        panel.addComponent(createContent());
        return panel;
    }

    private Component createContent() {
        HorizontalLayout dataView = new HorizontalLayout();
        dataView.setSizeFull();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);
        TesterController controller = new TesterController();
        dataView.addComponent(controller.getPanel());

        return dataView;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
