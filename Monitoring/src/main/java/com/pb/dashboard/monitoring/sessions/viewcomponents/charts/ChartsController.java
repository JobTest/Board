package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetric;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.sessions.SessionsModelI;
import com.pb.dashboard.monitoring.timings.TimingsType;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartsController {

    private SessionsModelI model;
    private DateControllerI dateController;
    private Map<InterfaceMetricI, MetricItem> data = new HashMap<>();
    private static final InterfaceMetricI UNKNOWN_METRIC_NAME = new InterfaceMetric("Неучтенныe метрики");

    public ChartsController(SessionsModelI model) {
        this.model = model;
        dateController = new DateController(model.getDateFrom(), model.getDateTo());
    }

    public Component getCharts() {
        loadChartsData();
        return buildCharts();
    }

    private HorizontalLayout buildCharts() {
        Chart chartT90 = getFillChart(TimingsType.P90);
        Chart chartT95 = getFillChart(TimingsType.P95);
        Chart chartT99 = getFillChart(TimingsType.P99);
        HorizontalLayout chartsLayout = new HorizontalLayout(chartT90, chartT95, chartT99);
        chartsLayout.setSizeFull();
        chartsLayout.setSpacing(false);
        return chartsLayout;
    }

    private Chart getFillChart(TimingsType type) {
        Chart chart = getChart(type.getName());
        setChartSeries(chart, type.getName());
        return chart;
    }

    private Chart getChart(String name) {
        Chart chart = new Chart(ChartType.PIE);
        chart.setWidth("100%");
        chart.setHeight("25%");
        Configuration conf = chart.getConfiguration();
        conf.disableCredits();
        conf.setTitle(name);
        addPlotOptions(conf);
        chart.drawChart(conf);
        return chart;
    }

    private void setChartSeries(Chart chart, String name) {
        ColorsPiker colorsPiker = new ColorsPiker();
        Map<Integer, SolidColor> colors = colorsPiker.getColors();
        final DataSeries series = new DataSeries();
        final DataSeries innerSeries = new DataSeries();
        int i = 1;
        for (InterfaceMetricI metric : data.keySet()) {
            DataSeriesItem pieItem = new DataSeriesItem(metric.getDescription(), getItemValue(name, data.get(metric)));
            if (i < colors.size()) {
                pieItem.setColor(colors.get(i));
            }
            if (!isGray(name, data.get(metric))) {
                pieItem.setColor(colorsPiker.getGray());
            } else {
                i++;
            }
            if (metric.isMain()) {
                innerSeries.add(pieItem);
            } else {
                if (metric.equals(UNKNOWN_METRIC_NAME)) {
                    innerSeries.add(pieItem);
                } else {
                    series.add(pieItem);
                }
            }
        }

        PlotOptionsPie innerPieOptions = new PlotOptionsPie();
        innerSeries.setPlotOptions(innerPieOptions);
        innerPieOptions.setBorderColor(SolidColor.MISTYROSE);
        innerPieOptions.setSize(100);
        chart.getConfiguration().setSeries(series, innerSeries);
    }

    private boolean isGray(String name, MetricItem item) {
        return getItemValue(name, item) != 0;
    }

    private int getItemValue(String name, MetricItem item) {
        if (name.equals(TimingsType.P90.getName())) {
            return item.getT90();
        }
        if (name.equals(TimingsType.P95.getName())) {
            return item.getT95();
        }
        if (name.equals(TimingsType.P99.getName())) {
            return item.getT99();
        }
        return 0;
    }

    private void addPlotOptions(Configuration conf) {
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);

        Labels dataLabels = new Labels();
        dataLabels.setEnabled(false);
        plotOptions.setDataLabels(dataLabels);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

    }

    private void loadChartsData() {
        setData();
    }

    private void setData() {
        if (dateController.isDaily()) {
            setDaylyData();
        } else if (dateController.isHourly()) {
            setHourlyData();
        } else {
            setMinutesData();
        }
    }

    private void setMinutesData() {
        int hour = dateController.getHour();
        int minute = dateController.getMinute();
        MetricItem unknownMetric = new MetricItem();
        Map<InterfaceMetricI, List<MetricItem>> tMetricsBy10Min = ServiceFactory.getMonitoring().getTimingMetricsBy10Min(model.getBpInterface(), dateController.getDateFrom().toLocalDate());
        for (InterfaceMetricI metric : tMetricsBy10Min.keySet()) {
            int i = 0;
            int bdHour = 0;
            for (MetricItem item : tMetricsBy10Min.get(metric)) {
                int hourItem = item.getDateTime().getHourOfDay();
                if (bdHour != hourItem) {
                    bdHour = hourItem;
                    i = 0;
                }
                if (hour == hourItem && i * 10 == minute) {
                    addMetrics(unknownMetric, item, metric.isMain());
                    data.put(metric, item);
                }
                i++;
            }
        }
        data.put(UNKNOWN_METRIC_NAME, unknownMetric);
        changeAllValue(unknownMetric);
    }

    private void changeAllValue(MetricItem unknownMetric) {
        addMetrics(data.get(UNKNOWN_METRIC_NAME), unknownMetric, false);
    }

    private void addMetrics(MetricItem unknownMetric, MetricItem item, boolean isAdd) {
        if (isAdd) {
            unknownMetric.setT90(unknownMetric.getT90() + item.getT90());
            unknownMetric.setT95(unknownMetric.getT95() + item.getT95());
            unknownMetric.setT99(unknownMetric.getT99() + item.getT99());
        } else {
            unknownMetric.setT90(unknownMetric.getT90() - item.getT90());
            unknownMetric.setT95(unknownMetric.getT95() - item.getT95());
            unknownMetric.setT99(unknownMetric.getT99() - item.getT99());
        }
    }

    private void setHourlyData() {
        int hour = dateController.getHour();
        MetricItem unknownMetric = new MetricItem();
        Map<InterfaceMetricI, List<MetricItem>> tMetricsByHour = ServiceFactory.getMonitoring().getTimingMetricsByHour(loadMetrics(), dateController.getDateFrom().toLocalDate());
        for (InterfaceMetricI metric : tMetricsByHour.keySet()) {
            for (MetricItem item : tMetricsByHour.get(metric)) {
                if (hour == item.getDateTime().getHourOfDay()) {
                    addMetrics(unknownMetric, item, metric.isMain());
                    data.put(metric, item);
                }
            }
        }
        data.put(UNKNOWN_METRIC_NAME, unknownMetric);
        changeAllValue(unknownMetric);
    }

    private void setDaylyData() {
        MetricItem unknownMetric = new MetricItem();
        Map<InterfaceMetricI, List<MetricItem>> tMetricsByDay = ServiceFactory.getMonitoring().getTimingMetricsByDay(loadMetrics(), dateController.getDateFrom().toLocalDate(), dateController.getDateTo().toLocalDate());
        for (InterfaceMetricI metric : tMetricsByDay.keySet()) {
            for (MetricItem item : tMetricsByDay.get(metric)) {
                addMetrics(unknownMetric, item, metric.isMain());
                data.put(metric, item);
            }
        }
        data.put(UNKNOWN_METRIC_NAME, unknownMetric);
        changeAllValue(unknownMetric);
    }

    private List<InterfaceMetricI> loadMetrics() {
        return new ArrayList<>(ServiceFactory.getMonitoring().getInterfaceMetrics(model.getBpInterface().getPkey()).values());
    }
}
