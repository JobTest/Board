package com.pb.dashboard.vitrina.view;

import com.pb.dashboard.vitrina.chart.BassChart;
import com.pb.dashboard.vitrina.chart.KassaChart;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class BiplaneChartView extends VerticalLayout implements View {

    public BiplaneChartView() {
        setSizeFull();
        addStyleName("dashboard-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);

        setExpandRatio(row, 3);
        row.addComponent(createPanels(new KassaChart().getChart(), new BassChart().getChart()));

    }

    private CssLayout createPanels(Component... content) {
        Button configureUA = new Button();
        configureUA.addStyleName("borderless");
        configureUA.addStyleName("small");
        configureUA.addStyleName("icon-only");
        configureUA.setIcon(new ThemeResource("icons/ua.png"));
        configureUA.addStyleName("configure");

        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(configureUA);
        for (Component component : content) {
            panel.addComponent(component);
        }
        return panel;
    }

    private static final long serialVersionUID = -1744455941100836080L;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}