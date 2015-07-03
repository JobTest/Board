package com.pb.dashboard.vitrina.chart;

import com.pb.dashboard.vitrina.chart.data.AbstractVaadinChartExample;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

import java.io.Serializable;
import java.util.Map;

public class KassaChart extends AbstractVaadinChartExample implements Serializable {
    private static final long serialVersionUID = -5684291683207390274L;
    private ChartManager cbm = new ChartManager();

    @Override
    public String getDescription() {
        return "График приема платежей";
    }

    @Override
    public Component getChart() {
        Chart chart = new Chart();
        chart.setHeight("400px");
        chart.setWidth("100%");

        Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.SPLINE);

        configuration.getTitle().setText("График приема платежей");
        configuration.getSubTitle().setText("Данные за последние три месяца");

        configuration.getxAxis().setCategories("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");

        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title("Количество платежей"));
        Labels labels = new Labels();
//        labels.setFormatter("this.value +'°'");
        yAxis.setLabels(labels);

        Axis xAxis = configuration.getxAxis();
        xAxis.setTitle(new Title("Дата"));
//        configuration.getTooltip().setFormatter("''+ this.series.name +'' +this.x +': принято '+ this.y +' платежей'");
        configuration.getTooltip().setShared(true);
        configuration.getTooltip().setCrosshairs(true);
        Legend legend = new Legend();
        legend.setEnabled(false);
        configuration.setLegend(legend);

        PlotOptionsSpline plotOptions = new PlotOptionsSpline();
        configuration.setPlotOptions(plotOptions);
        plotOptions.setMarker(new Marker(true));
        plotOptions.getMarker().setRadius(4);
        plotOptions.getMarker()
                .setLineColor(new SolidColor("#666666"));
        plotOptions.getMarker().setLineWidth(1);

        cbm.getKassaPayment();
        try {
            Map<String, Integer[]> currentMonthValue = cbm.getCurrentMonthValue();
            Map<String, Integer[]> agotMonthValue = cbm.getAgotMonthValue();
            Map<String, Integer[]> twoAgoMonthValue = cbm.get2AgoMonthValue();
            Map.Entry<String, Integer[]> nextCur = currentMonthValue.entrySet().iterator().next();
            Map.Entry<String, Integer[]> nextAgo = agotMonthValue.entrySet().iterator().next();
            Map.Entry<String, Integer[]> next2Ago = twoAgoMonthValue.entrySet().iterator().next();
            configuration.addSeries(createSerial(nextCur.getKey(), MarkerSymbolEnum.SQUARE, nextCur.getValue()));
            configuration.addSeries(createSerial(nextAgo.getKey(), MarkerSymbolEnum.TRIANGLE, nextAgo.getValue()));
            configuration.addSeries(createSerial(next2Ago.getKey(), MarkerSymbolEnum.DIAMOND, next2Ago.getValue()));
        } catch (IndexOutOfBoundsException ex) {
        }
        chart.drawChart(configuration);
        return chart;
    }

    private DataSeries createSerial(String name, MarkerSymbolEnum markerType, Integer[] data) {
        PlotOptionsSpline plotOptions;
        DataSeries ls = new DataSeries();
        plotOptions = new PlotOptionsSpline();
        Marker marker = new Marker();
        marker.setSymbol(markerType);
        plotOptions.setMarker(marker);
        ls.setPlotOptions(plotOptions);
        ls.setName(name);
        ls.setData(data);
        return ls;
    }
}