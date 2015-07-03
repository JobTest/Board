package com.pb.dashboard.external.charts.layouts;

import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.pb.dashboard.external.charts.controller.ChartsHourlyDataManager;
import com.pb.dashboard.external.charts.data.AbstractVaadinChartExample;
import com.pb.dashboard.external.charts.data.ChartsDailyDataManager;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class ChartsLayout extends AbstractVaadinChartExample {
    private ChartsFilter filter;
    private ChartsHourlyDataManager hourlyDataManager;
    private ChartsDailyDataManager dailyDataManager;
    private boolean byLines;

    public ChartsLayout(ChartsFilter filter) {
        this.filter = filter;
    }

    public Component getChart(boolean byLines) {
        this.byLines = byLines;
        return getChart();
    }

    @Override
    public Component getChart() {
        if (filter.isHourly()) {
            Chart chart = getHourlyChart();
            return chart;
        }
        if (filter.isDaily()){
            Chart chart = getDailyChart();
            return chart;
        }

        dailyDataManager = new ChartsDailyDataManager(filter.getBank(), filter.getFrom(), filter.getTo());
        return new Label("it's not cool");
    }

    private Chart getDailyChart() {
        Chart chart = creatChart();
        Configuration configuration;
        if (byLines){
            configuration = creatConfiguration("ГРАФИК ЗАГРУЗКИ ЗАДОЛЖЕННОСТИ ПО СТРОКАМ");
        }else {
            configuration = creatConfiguration("ГРАФИК ЗАГРУЗКИ ЗАДОЛЖЕННОСТИ ПО ФАЙЛАМ");
        }
        dailyDataManager = new ChartsDailyDataManager(filter.getBank(),filter.getFrom(), filter.getTo());
        configuration.getxAxis().setCategories(dailyDataManager.getCategories());
        Axis yAxis = configuration.getyAxis();
        yAxis.setMin(0);
        yAxis.setTitle(dailyDataManager.getTitleY());

        addPlotOptionsLine(configuration);
        configuration.getLegend().setEnabled(false);
        configuration.disableCredits();

        if (byLines) {
            configuration.addSeries(dailyDataManager.getListSeriesByLines());
        }
        else {
            configuration.addSeries(dailyDataManager.getListSeriesByFiles());
        }

        chart.drawChart(configuration);

        return chart;
    }

    private Chart getHourlyChart() {
        Chart chart = creatChart();
        Configuration configuration;
        if (byLines){
            configuration = creatConfiguration("ГРАФИК ЗАГРУЗКИ ЗАДОЛЖЕННОСТИ ПО ЛИНИЯМ");
        }else {
            configuration = creatConfiguration("ГРАФИК ЗАГРУЗКИ ЗАДОЛЖЕННОСТИ ПО ФАЙЛАМ");
        }
        hourlyDataManager = new ChartsHourlyDataManager(filter.getBank(), filter.getFrom());
        creatXAxis(configuration);
        creatYAxis(configuration);


        addPlotOptionsLine(configuration);
        configuration.getLegend().setEnabled(false);
        configuration.disableCredits();

        if (byLines) {
            configuration.addSeries(hourlyDataManager.getListSeriesByLines());
        }
        else {
            configuration.addSeries(hourlyDataManager.getListSeriesByFiles());
        }
        chart.drawChart(configuration);

        return chart;
    }

    private void addPlotOptionsLine(Configuration configuration) {
        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setDataLabels(new Labels(true));
        plotOptions.setMarker(new Marker(false));
        configuration.setPlotOptions(plotOptions);
    }

    private void creatXAxis(Configuration configuration) {
        configuration.getxAxis().setCategories(hourlyDataManager.getCategories());
    }

    private void creatYAxis(Configuration configuration) {
        Axis yAxis = configuration.getyAxis();
        yAxis.setMin(0);
        yAxis.setTitle(hourlyDataManager.getTitleY());
        yAxis.getTitle().setVerticalAlign(VerticalAlign.HIGH);
    }

    private Chart creatChart(){
        Chart chart = new Chart();
        chart.setHeight("50%");
        chart.setWidth("100%");
        return chart;
    }

    private Configuration creatConfiguration(String title){
        Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.LINE);
        configuration.getChart().setMarginBottom(25);
        configuration.getTitle().setText(title);
        return configuration;
    }

}
