package com.pb.dashboard.vitrina.trends;

import com.pb.dashboard.vitrina.trends.charts.ChartEnum;
import com.pb.dashboard.vitrina.trends.charts.TrendsChartManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrendsInfoPanelManager implements Serializable {

    private TrendsChartManager chartManager = new TrendsChartManager();
    private String regex = ".*день$";

    private List<TrendsSubPanel> subPanels = new ArrayList<TrendsSubPanel>();
    private boolean isDayPanel = true,
            isHourPanel = false;

    public void setChartSeries(Map<ChartEnum, Integer[]> chartsData) {
        chartManager.setAllCharts(chartsData);
    }

    public void setSubPanelsData(List<Integer[]> values) {
        for (int i = 0; i < subPanels.size(); i++) {
            subPanels.get(i).setValues(values.get(i));
        }
    }

    public HorizontalLayout getPaydeskPanel() {
        Component[] subPanels = {createSubPanel(isHourPanel), createSubPanel(isDayPanel)};
        Chart[] charts = {chartManager.getChart(ChartEnum.PAYDESK_HOUR_CASH),
                chartManager.getChart(ChartEnum.PAYDESK_HOUR_PHYS),
                chartManager.getChart(ChartEnum.PAYDESK_DAY_CASH),
                chartManager.getChart(ChartEnum.PAYDESK_DAY_PHYS)};
        return getInfoPanel("КАССА", subPanels, charts);
    }

    public HorizontalLayout getTerminalPanel() {
        Component[] subPanels = {createSubPanel(isHourPanel), createSubPanel(isDayPanel)};
        Chart[] charts = {chartManager.getChart(ChartEnum.TERMINAL_HOUR_CASH),
                chartManager.getChart(ChartEnum.TERMINAL_HOUR_PHYS),
                chartManager.getChart(ChartEnum.TERMINAL_DAY_CASH),
                chartManager.getChart(ChartEnum.TERMINAL_DAY_PHYS)};
        return getInfoPanel("ТСО", subPanels, charts);
    }

    public HorizontalLayout getP24Panel() {
        Component[] subPanels = {createSubPanel(isHourPanel), createSubPanel(isDayPanel)};
        Chart[] charts = {chartManager.getChart(ChartEnum.P24_HOUR_CASH),
                chartManager.getChart(ChartEnum.P24_HOUR_PHYS),
                chartManager.getChart(ChartEnum.P24_DAY_CASH),
                chartManager.getChart(ChartEnum.P24_DAY_PHYS)};
        return getInfoPanel("ПРИВАТ24", subPanels, charts);
    }

    public HorizontalLayout get3700Panel() {
        Component[] subPanels = {createSubPanel(isHourPanel), createSubPanel(isDayPanel)};
        Chart[] charts = {chartManager.getChart(ChartEnum.L3700_HOUR_CASH),
                chartManager.getChart(ChartEnum.L3700_HOUR_PHYS),
                chartManager.getChart(ChartEnum.L3700_DAY_CASH),
                chartManager.getChart(ChartEnum.L3700_DAY_PHYS)};
        return getInfoPanel("3700", subPanels, charts);
    }

    private HorizontalLayout getInfoPanel(String title, Component[] subPanels, Chart[] charts) {
        HorizontalLayout panel = initPanel();
        String lastHour = title + ": Предыдущий час";
        String currentDay = title + ": Текущий день";
        Component hourlyStats = createPanel(lastHour, subPanels[0], charts[0], charts[1]);
        Component dailyStats = createPanel(currentDay, subPanels[1], charts[2], charts[3]);
        panel.addComponent(hourlyStats);
        panel.addComponent(dailyStats);
        return panel;
    }

    private HorizontalLayout initPanel() {
        HorizontalLayout panel = new HorizontalLayout();
        panel.addStyleName("trends-info-panel");
        panel.setWidth("100%");
        panel.setSpacing(true);
        return panel;
    }

    private Component createPanel(String caption, Component subPanel, Chart first, Chart second) {
        HorizontalLayout panel = new HorizontalLayout();
        panel.setSizeFull();
        panel.setMargin(true);
        panel.setHeight("220px");
        panel.setCaption(caption);
        panel.addComponents(subPanel, first, second);
        panel.setExpandRatio(subPanel, 1.0f);
        panel.setExpandRatio(first, 3.0f);
        panel.setExpandRatio(second, 3.0f);
        return wrapPanel(panel);
    }

    private Component wrapPanel(Component component) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        if (component.getCaption().matches(regex)) {
            panel.addStyleName("day");
        }
        panel.addComponent(component);
        return panel;
    }

    private VerticalLayout createSubPanel(boolean isDayPanel) {
        TrendsSubPanel subPanel = new TrendsSubPanel(isDayPanel);
        subPanels.add(subPanel);
        return subPanel;
    }

}
