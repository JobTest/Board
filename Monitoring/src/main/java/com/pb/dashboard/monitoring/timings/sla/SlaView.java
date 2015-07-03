package com.pb.dashboard.monitoring.timings.sla;

import com.pb.dashboard.monitoring.timings.sla.builderchart.CountsChart;
import com.pb.dashboard.monitoring.timings.sla.builderchart.PercentChart;
import com.pb.dashboard.monitoring.timings.sla.builderchart.SlaDecorator;
import com.pb.dashboard.monitoring.timings.sla.builderchart.TimingsChart;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by vlad
 * Date: 06.11.14_15:00
 */
public class SlaView extends VerticalLayout {

    private static final long serialVersionUID = -4196254092317281443L;
    private SlaControllerI controller;
    private SlaModelI model;

    private CheckBox checkBox;
    private PercentChart percentChart;
    private CountsChart countChart;
    private TimingsChart timingsChart;

    public SlaView(SlaControllerI controller, SlaModelI model) {
        this.model = model;
        this.controller = controller;
    }

    public void setDecorator(SlaDecorator decorator) {
        removeAllComponents();
        addComponent(decorator.getShowError());
        addComponent(decorator.getPercentChart());
        addComponent(decorator.getCountsChart());
        addComponent(decorator.getTimingsChart());

        this.checkBox = decorator.getShowError();
        this.percentChart = decorator.getPercentChart();
        this.countChart = decorator.getCountsChart();
        this.timingsChart = decorator.getTimingsChart();
    }
}