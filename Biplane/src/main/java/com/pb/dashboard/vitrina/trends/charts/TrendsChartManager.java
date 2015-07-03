package com.pb.dashboard.vitrina.trends.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.HighChartsDefaultTheme;
import com.vaadin.addon.charts.themes.VaadinTheme;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TrendsChartManager implements Serializable {

    private Color[] colors;

    private Map<ChartEnum, Chart> charts = new HashMap<ChartEnum, Chart>();

    private Chart paydeskHourCash = createChart(ChartEnum.PAYDESK_HOUR_CASH);
    private Chart paydeskHourPhys = createChart(ChartEnum.PAYDESK_HOUR_PHYS);
    private Chart paydeskDayCash = createChart(ChartEnum.PAYDESK_DAY_CASH);
    private Chart paydeskDayPhys = createChart(ChartEnum.PAYDESK_DAY_PHYS);

    private Chart terminalHourCash = createChart(ChartEnum.TERMINAL_HOUR_CASH);
    private Chart terminalHourPhys = createChart(ChartEnum.TERMINAL_HOUR_PHYS);
    private Chart terminalDayCash = createChart(ChartEnum.TERMINAL_DAY_CASH);
    private Chart terminalDayPhys = createChart(ChartEnum.TERMINAL_DAY_PHYS);

    private Chart p24HourCash = createChart(ChartEnum.P24_HOUR_CASH);
    private Chart p24HourPhys = createChart(ChartEnum.P24_HOUR_PHYS);
    private Chart p24DayCash = createChart(ChartEnum.P24_DAY_CASH);
    private Chart p24DayPhys = createChart(ChartEnum.P24_DAY_PHYS);

    private Chart l3700HourCash = createChart(ChartEnum.L3700_HOUR_CASH);
    private Chart l3700HourPhys = createChart(ChartEnum.L3700_HOUR_PHYS);
    private Chart l3700DayCash = createChart(ChartEnum.L3700_DAY_CASH);
    private Chart l3700DayPhys = createChart(ChartEnum.L3700_DAY_PHYS);

    public TrendsChartManager() {
        Color[] vaadinColors = new VaadinTheme().getColors();
        colors = Arrays.copyOf(vaadinColors, 3);
    }

    public Chart getChart(ChartEnum type) {
        return charts.get(type);
    }

    private Chart createChart(ChartEnum chartEnum) {

        /* Chart theme modified */

        HighChartsDefaultTheme highChartsDefaultTheme = new HighChartsDefaultTheme();
        highChartsDefaultTheme.setCredits(new Credits(false));
        ChartOptions.get().setTheme(highChartsDefaultTheme);

        /* Chart creation */

        Chart chart = new Chart(ChartType.PIE);
        chart.setHeight("200px");

        Configuration conf = chart.getConfiguration();

        conf.setTitle("");
        conf.getChart().setBackgroundColor(new SolidColor(255, 255, 255, 0));

        Labels labels = new Labels();
        labels.setFormatter(
                "this.y > 5 ? this.point.name : null");
        labels.setColor(new SolidColor(255, 255, 255));
        labels.setDistance(-23);

        PlotOptionsPie options = new PlotOptionsPie();
        options.setAllowPointSelect(true);
        options.setDataLabels(labels);

        conf.setPlotOptions(options);

        charts.put(chartEnum, chart);

        return chart;
    }

    public void setAllCharts(Map<ChartEnum, Integer[]> chartsData) {
        for (ChartEnum type : ChartEnum.values()) {
            setSeries(type, charts.get(type), chartsData);
        }
    }

    private void setSeries(ChartEnum type, Chart chart, Map<ChartEnum, Integer[]> chartsData) {
        DataSeries series = new DataSeries();
        series.setName("Платежей");
        Number[] values = chartsData.get(type);
        if (type.getSeriesCount() == 2) {
            series.setData(new String[]{"Юр.", "Физ."}, values);
        } else {
            series.setData(new String[]{"Нал.", "Безнал.", "Задолж."}, values, colors);
        }
        chart.getConfiguration().setSeries(series);
        chart.drawChart();
    }

}
