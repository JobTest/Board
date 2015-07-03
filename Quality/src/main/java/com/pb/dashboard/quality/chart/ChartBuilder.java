package com.pb.dashboard.quality.chart;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.addon.charts.themes.HighChartsDefaultTheme;

public class ChartBuilder {

    private Chart excludedPaymentsChart = createChart("Забракованные платежи по всем каналам приема",
            "Доля забракованных платежей в разрезе каналов создания",
            "Забраковано, (%)");

    private Chart timingsChart = createChart("Тайминги платежей по всем каналам приема",
            "Доля платежей не уложившихся в регламент в разрезе каналов создания",
            "Не уложились в регламент, (%)");

    private Labels tabSheetChartLabels;
    private final int TS_CHART_CATEGORIES_LIMIT = 11;

    public Chart getExcludedPaymentsChart() {
        return excludedPaymentsChart;
    }

    public Chart getTimingsChart() {
        return timingsChart;
    }

    public Chart getTabSheetChart(String chartTitle, ChartDataHolder dataHolder) {
        Chart tabSheetChart = createTabSheetChart();
        tabSheetChart.getConfiguration().setTitle(chartTitle);
        setTabChartSeries(tabSheetChart, dataHolder);
        return tabSheetChart;
    }

    private Chart createChart(String title, String subTitle, String yAxisTitle) {
        HighChartsDefaultTheme highChartsDefaultTheme = new HighChartsDefaultTheme();
        highChartsDefaultTheme.setCredits(new Credits(false));
        ChartOptions.get().setTheme(highChartsDefaultTheme);

        Chart chart = new Chart(ChartType.LINE);
        chart.setHeight("550px");
        chart.setWidth("100%");

        Configuration conf = chart.getConfiguration();
        conf.getTitle().setText(title);
        conf.getSubTitle().setText(subTitle);

        Axis xAxis = conf.getxAxis();
        Labels labels = new Labels();
        labels.setRotation(-60);
        labels.setAlign(HorizontalAlign.RIGHT);
        Style style = new Style();
        style.setFontSize("13px");
        style.setFontFamily("Verdana, sans-serif");
        labels.setStyle(style);
        xAxis.setLabels(labels);
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);

        Axis yAxis = conf.getyAxis();
        yAxis.setMin(0);
        yAxis.setTitle(new Title(yAxisTitle));

        String format = "'<b>' + this.series.name + '</b><br/>' + this.x + ': <b>' + this.y.toFixed(2) + '%</b>'";
        conf.getTooltip().setFormatter(format);

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setDataLabels(new Labels(false));
        conf.setPlotOptions(plotOptions);

        return chart;
    }

    private Chart createTabSheetChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();

        XAxis xAxis = conf.getxAxis();
        tabSheetChartLabels = new Labels();
        Style style = new Style();
        style.setFontSize("13px");
        style.setFontFamily("Verdana, sans-serif");
        tabSheetChartLabels.setStyle(style);
        xAxis.setLabels(tabSheetChartLabels);

        YAxis yAxis = conf.getyAxis();
        yAxis.setTitle("Доля забракованных, (%)");
        yAxis.setMin(0);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setBackgroundColor("#FFFFFF");
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(100);
        legend.setY(0);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        String format = "'<b>' + this.series.name + '</b><br/>' + this.x + ': <b>' + this.y.toFixed(2) + '%</b>'";
        tooltip.setFormatter(format);
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);

        return chart;
    }

    public void setTabChartSeries(Chart chart, ChartDataHolder dataHolder) {
        chart.getConfiguration().getxAxis().setCategories(dataHolder.getCategories());
        chart.getConfiguration().setSeries(dataHolder.getSeries());
        if (dataHolder.getSize() > TS_CHART_CATEGORIES_LIMIT) {
            tabSheetChartLabels.setRotation(-70);
            tabSheetChartLabels.setAlign(HorizontalAlign.RIGHT);
        }
        chart.drawChart();
    }

    public void setRejectedPaymentsChartSeries(ChartDataHolder holder) {
        excludedPaymentsChart.getConfiguration().getxAxis().setCategories(holder.getCategories());
        excludedPaymentsChart.getConfiguration().setSeries(holder.getSeries());
        excludedPaymentsChart.drawChart();
    }

    public void setTimingsChartSeries(ChartDataHolder holder) {
        timingsChart.getConfiguration().getxAxis().setCategories(holder.getCategories());
        timingsChart.getConfiguration().setSeries(holder.getSeries());
        timingsChart.drawChart();
    }

}
