package com.pb.dashboard.vitrina.redmine.version;

import com.pb.dashboard.vitrina.redmine.ClickLabel;
import com.vaadin.addon.charts.Chart;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class VersionView extends VerticalLayout {

    private Chart chart;
    private ClickLabel clickLabel;

    public VersionView() {
    }

    public void init() {
        initChart();
    }

    private void initChart() {
        HorizontalLayout l = new HorizontalLayout();
        l.addComponent(chart);

        l.setComponentAlignment(chart, Alignment.TOP_CENTER);

        clickLabel = new ClickLabel("100");
        clickLabel.setStyleName("redmine-count");
        clickLabel.setHeight("230px");
        l.addComponent(clickLabel);
        l.setComponentAlignment(clickLabel, Alignment.MIDDLE_LEFT);

        addComponent(l);
        setComponentAlignment(l, Alignment.TOP_CENTER);
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }


    public Chart getChart() {
        return chart;
    }

    public ClickLabel getClickLabel() {
        return clickLabel;
    }
}