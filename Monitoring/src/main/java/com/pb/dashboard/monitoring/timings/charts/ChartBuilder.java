package com.pb.dashboard.monitoring.timings.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Style;

/**
 * Created by vlad
 * Date: 12.12.14_15:18
 */
public class ChartBuilder {

    private static ChartBuilder builder = new ChartBuilder();
    private Chart chart;
    private String title;
    private String yAxisTitle;

    public static Chart createLineChart(String title, String yAxisTitle) {
        builder.title = title;
        builder.yAxisTitle = yAxisTitle;
        builder.initChart();
        return builder.chart;
    }

    private void initChart() {
        chart = new Chart(ChartType.LINE);
        chart.setHeight("400px");
        chart.setWidth("100%");
        chart.addStyleName("chart");
        initConfig();
    }

    private void initConfig() {
        Configuration conf = chart.getConfiguration();
        conf.disableCredits();
        conf.getTitle().setText(title);
        conf.getChart().setZoomType(ZoomType.X);
        initXAxis();
        initYAxis();
        initFormatter();
        initPlotOptionsLine();
    }

    private void initXAxis() {
        Configuration conf = chart.getConfiguration();
        Axis xAxis = conf.getxAxis();
        xAxis.setType(AxisType.DATETIME);
        xAxis.setLabels(createLabels());
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);
    }

    private Labels createLabels() {
        Labels labels = new Labels();
        labels.setRotation(-60);
        labels.setAlign(HorizontalAlign.RIGHT);
        labels.setStyle(createStyle());
        return labels;
    }

    private Style createStyle() {
        Style style = new Style();
        style.setFontSize("13px");
        style.setFontFamily("Verdana, sans-serif");
        return style;
    }

    private void initYAxis() {
        Configuration conf = chart.getConfiguration();
        Axis yAxis = conf.getyAxis();
        yAxis.setTitle(yAxisTitle);
        yAxis.getLabels().setFormatter("this.value");

        yAxis.setMin(0);
    }

    private void initFormatter() {
        String format = "function() {" +
                " var date = new Date(this.x);" +
                " var options = { timeZone: 'UTC'};" +
                " var format = date.toLocaleString('ua-UA',options);" +
                " return '<b>' + this.series.name + '</b><br/>' + format + ': <b>' + this.y + '</b>';" +
                "}";
        Configuration conf = chart.getConfiguration();
        conf.getTooltip().setFormatter(format);
    }

    private void initPlotOptionsLine() {
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setDataLabels(new Labels(false));
        Configuration conf = chart.getConfiguration();
        conf.setPlotOptions(plotOptions);
    }
}
