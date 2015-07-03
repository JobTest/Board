package com.pb.dashboard.tickets.view.chart;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Style;

public class BranchChart extends Chart {

    private Configuration conf;
    private XAxis xAxis;

    public BranchChart() {
        super(ChartType.COLUMN);
        setHeight("430px");
        conf = getConfiguration();
        conf.getTitle().setText(null);

        xAxis = new XAxis();
        Labels labels = new Labels();
        labels.setRotation(-63);
        labels.setAlign(HorizontalAlign.RIGHT);
        Style style = new Style();
        style.setFontSize("13px");
        style.setFontFamily("Verdana, sans-serif");
        labels.setStyle(style);
        xAxis.setLabels(labels);
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setMin(0);
        yAxis.setTitle("Количество");
        conf.addyAxis(yAxis);

        Legend legend = new Legend();
        legend.setEnabled(false);
        conf.setLegend(legend);

        // Set vertical zooming
        conf.getChart().setZoomType(ZoomType.Y);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("''+ this.x +' : '" + "+ Highcharts.numberFormat(this.y, 0)");
        conf.setTooltip(tooltip);
        conf.disableCredits();
    }

    public void setSeries(BranchChartDataHolder dataHolder) {
        xAxis.setCategories(dataHolder.getCategories());
        conf.setSeries(dataHolder.getSeries());
        drawChart(conf);
    }

}
