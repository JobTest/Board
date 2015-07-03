package com.pb.dashboard.itdashbord.chart;

import com.pb.dashboard.itdashbord.chart.data.AbstractVaadinChartExample;
import com.pb.dashboard.itdashbord.db.DataHolder;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ChartHolder extends AbstractVaadinChartExample {

    private static final SolidColor LIGHT_BLUE = new SolidColor(68, 170, 213, .2);
    private static final SolidColor GRAY = new SolidColor("#666666");

    @Override
    public String getDescription() {
        return "Stacked area";
    }

    @Override
    public Component getChart() {
        return getChart(getSeries(0, 10));
    }

    public Component getCharts2() {
        return getChart(getSeries(10, 20));
    }

    public Component getCharts3() {
        return getChart(getSeries(20, 30));
    }

    public Component getCharts4() {
        return getChart(getSeries(30, DataHolder.getPaymentByType().length));
    }

    public Chart getChart(List<Series> series) {

        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();
        conf.setSubTitle("Всего за Май-Август");

        conf.setTitle(new Title("Количество платежей по шаблонам"));
        XAxis xAxis = new XAxis();
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);
        xAxis.setCategories("Май", "Июнь", "Июль", "Август");
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle(new Title("Количество платежей"));
        Labels labels = new Labels();
        // labels.setFormatter("this.value / 1000");
        yAxis.setLabels(labels);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ Highcharts.numberFormat(this.y, 0, ',')");
        conf.setTooltip(tooltip);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setStacking(Stacking.NORMAL);
        plotOptions.setLineColor(GRAY);
        plotOptions.setLineWidth(1);
        Marker marker = new Marker();
        marker.setLineColor(GRAY);
        marker.setLineWidth(1);
        plotOptions.setMarker(marker);
        conf.setPlotOptions(plotOptions);
        conf.setSeries(series);

        chart.drawChart(conf);

        return chart;
    }


    private List<Series> getSeries(int from, int to) {
        List<Series> series = new ArrayList<Series>();

        Object[] holder = DataHolder.getPaymentByType();

        for (int i = from; i < to; i++) {
            Object[] item = (Object[]) holder[i];
            series.add(new ListSeries(item[0].toString(), Integer
                    .parseInt(item[4].toString().trim()), Integer
                    .parseInt(item[3].toString().trim()), Integer
                    .parseInt(item[2].toString().trim()), Integer
                    .parseInt(item[1].toString().trim())));
        }
        return series;
    }


    public Component getPieChart() {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Количество платежей по шаблонам");
        conf.setSubTitle("Всего за Май-Август");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        //tooltip.setFormatter("this.y +': '+ Highcharts.numberFormat(this.percentage, 0, ',') + '%'");
        tooltip.setPointFormat("{point.y}: {point.percentage}%");
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        Labels dataLabels = new Labels();
        dataLabels.setEnabled(true);
        conf.setPlotOptions(plotOptions);


        conf.setSeries(getDataSeries(0, 10));

        chart.drawChart(conf);
        return chart;
    }

    private DataSeries getDataSeries(int from, int to) {
        DataSeries series = new DataSeries();

        Object[] holder = DataHolder.getPaymentByType();

        for (int i = from; i < to; i++) {
            Object[] item = (Object[]) holder[i];
            series.add(new DataSeriesItem(item[0].toString(), Integer
                    .parseInt(item[4].toString().trim()) + Integer
                    .parseInt(item[3].toString().trim()) + Integer
                    .parseInt(item[2].toString().trim()) + Integer
                    .parseInt(item[1].toString().trim())));
        }
        return series;
    }

    public Component getLineChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);
        // chart.setHeight("450px");

        Configuration conf = chart.getConfiguration();

        conf.setTitle(new Title("Динамика количества платежей через \"Мои платежи\" ТСО"));
        conf.setSubTitle("Всего за Май-Август");

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setFloating(true);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(75);
        legend.setY(50);
        legend.setBorderWidth(1);
        legend.setBackgroundColor("white");
        conf.setLegend(legend);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Июль", "Август", "Сентябрь", "Октябрь");
        xAxis.setPlotBands(new PlotBand(4.5, 6.5, LIGHT_BLUE));
        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle(new Title("Кол-во платежей"));
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setHeaderFormat("");
        tooltip.setPointFormat("{series.name}: {point.y} платежей");
        conf.setTooltip(tooltip);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setFillOpacity(0.5);
        conf.setPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Кол-во платежей в ТСО", 1574766, 1420160, 1401736, 1492494));
        conf.addSeries(new ListSeries("ТСО через меню \"Мои платежи\"", 85840, 173972, 217306, 320306));

        chart.drawChart(conf);
        return chart;
    }
}