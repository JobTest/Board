package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.charts.ChartsView;
import com.pb.dashboard.monitoring.timings.sla.SlaView;
import com.pb.dashboard.monitoring.timings.table.TimingsTable;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 20.11.14_10:10
 */
public class IndicatorsTabSheet extends TabSheet implements Observer<IndicatorsModelI> {

    private static final long serialVersionUID = 1330888496475851521L;
    private Map<IndicatorsTab, VerticalLayout> tabs;

    private ChartsView chartsView;
    private TimingsTable table;

    private IndicatorsModelI model;
    private IndicatorsControllerI controller;

    private SlaView slaView;

    public IndicatorsTabSheet(IndicatorsControllerI controller, IndicatorsModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        addStyleName("charts-tabs");
        initTabs();
    }

    private void initTabs() {
        initTabsEmptyContent();
        initTabCharts();
        initTabTable();
        initTabSla();
    }

    private void initTabsEmptyContent() {
        tabs = new HashMap<>();
        for (IndicatorsTab tab : IndicatorsTab.values()) {
            VerticalLayout layout = new VerticalLayout();
            layout.setMargin(true);
            addTab(layout, tab.getName());
            tabs.put(tab, layout);
        }
    }

    private void initTabCharts() {
        VerticalLayout layout = tabs.get(IndicatorsTab.CHARTS);
        chartsView = controller.getChartsController().getView();
        layout.addComponent(chartsView);
    }

    private void initTabTable() {
        VerticalLayout layout = tabs.get(IndicatorsTab.TABLE);
        table = new TimingsTable();
        layout.addComponent(table);
    }

    private void initTabSla() {
    }


    @Deprecated
    public void setContent(IndicatorsTab tab, Component component) {
        VerticalLayout layout = tabs.get(tab);
        if (layout != null) {
            layout.removeAllComponents();
            layout.addComponent(component);
        }
    }

    public void setSlaView(SlaView slaView) {
        VerticalLayout layout = tabs.get(IndicatorsTab.SLA);
        layout.addComponent(slaView);
        this.slaView = slaView;
    }

    @Override
    public void modified(IndicatorsModelI obj) {
        Map<InterfaceMetricI, List<MetricItem>> timingMetrics = model.getTimingMetricsMap();
        List<MetricItem> metrics = timingMetrics.get(model.getMetricSelected());
        if (metrics != null) {
            table.setFormatterCalendar(model.getFilterRange());
            table.setItems(metrics);
        }
    }
}