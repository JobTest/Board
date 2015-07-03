package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.external.monitor.chart.ChartBuilder;
import com.pb.dashboard.external.monitor.chart.ChartData;
import com.pb.dashboard.external.monitor.chart.MonitorChart;
import com.pb.dashboard.external.monitor.logic.QueriesDataManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueriesViewPanelManager implements Serializable {

    private QueriesDataManager dataManager;
    private ChartBuilder chartManager;

    public QueriesViewPanelManager(QueriesDataManager dataManager, ChartBuilder chartManager) {
        this.dataManager = dataManager;
        this.chartManager = chartManager;
    }

    // Left Vertical Layout for Queries View
    public VerticalLayout getPaymentsQueriesPanel() {
        return getPanel(getQueriesSubPanel(Complex.BIPLANE_API2X.getName(),
                QueriesDataManager.PAY_INTERFACES, dataManager.getPaymentsQueries()));
    }

    // Right Vertical Layout for Queries View
    public VerticalLayout getDebtQueriesPanel() {
        return getPanel(getQueriesSubPanel(Complex.TEMPLATES.getName(),
                QueriesDataManager.TEML_INTERFACES, dataManager.getTemplQueries()));
    }

    /* Queries View Sub Panels */

    private Component getQueriesSubPanel(String title, String[] interfaces, List<ChartData> chartDataList) {
        String yCategoriesTitle = "К-во запросов";
        int length = interfaces.length;
        ArrayList<Chart> charts = new ArrayList<Chart>(length);
        for (int i = 0; i < chartDataList.size(); i++) {
            String chartTitle = interfaces[i];
            MonitorChart chart = chartManager.getChart(chartTitle, yCategoriesTitle, true);
            chartManager.setChartSeries(chart, chartDataList.get(i));
            charts.add(chart);
        }
        Component panel = getSubPanel(charts.toArray(new Chart[charts.size()]));
        panel.setCaption(title);
        return panel;
    }

    /* Style Wrappers */

    private VerticalLayout getPanel(Component... components) {
        VerticalLayout layout = new VerticalLayout();
        layout.addStyleName("layout-panel");
        layout.setSpacing(true);
        for (Component component : components)
            layout.addComponent(component);

        return layout;
    }

    private Component getSubPanel(Component... components) {
        VerticalLayout panel = new VerticalLayout();
        panel.addStyleName("inner-panel");
        for (Component c : components)
            panel.addComponent(c);
        return panel;
    }

}
