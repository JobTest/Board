package com.pb.dashboard.vitrina.redmine;

import com.pb.dashboard.vitrina.chart.data.AbstractVaadinChartExample;
import com.pb.dashboard.vitrina.redmine.version.Version;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;

import java.util.Arrays;

import static com.pb.dashboard.vitrina.redmine.version.Version.Status.CLOSED;
import static com.pb.dashboard.vitrina.redmine.version.Version.Status.OPENED;
import static com.pb.dashboard.vitrina.redmine.version.Version.Tracker.ERROR;
import static com.pb.dashboard.vitrina.redmine.version.Version.Tracker.OTHER;

public class RedmineChart extends AbstractVaadinChartExample {
    private static Color[] colorsIn = {SolidColor.YELLOW, SolidColor.GRAY};
    private static Color[] colorsOut = {SolidColor.RED, new SolidColor("#87CEFA"), SolidColor.RED, new SolidColor("#87CEFA")};
    private Chart chart;
    private Version version;
    private DataSeries inSeries;
    private DataSeries outSeries;

    public RedmineChart(Version version) {
        this.version = version;
        init();
    }

    public Chart getChart() {
        return chart;
    }

    public void init() {
        chart = new Chart(ChartType.PIE);
//        chart.setSizeFull();
        chart.setHeight("350px");
        chart.setWidth("280px");
        initConfiguration();
    }

    private void initConfiguration() {
        Configuration conf = chart.getConfiguration();
        conf.setTitle(version.getName());
        conf.getChart().setBackgroundColor(new SolidColor(255, 255, 255, 0));
        initInSeries();
        initOutSeries();
        conf.setSeries(inSeries, outSeries);
        chart.drawChart(conf);
    }

    private void initInSeries() {
        inSeries = new DataSeries();
        inSeries.setName("Статус");

        PlotOptionsPie plotOptionsPie = getPlotOptionsPie(0);
        inSeries.setPlotOptions(plotOptionsPie);

        Color[] colors = Arrays.copyOf(RedmineChart.colorsIn, 2);
        String[] value = new String[]{OPENED.getName(), CLOSED.getName()};
        Number[] count = new Number[]{version.getTasks(OPENED).size(), version.getTasks(CLOSED).size()};
        inSeries.setData(value, count, colors);
    }

    private void initOutSeries() {
        outSeries = new DataSeries();
        outSeries.setName("Тип");

        PlotOptionsPie plotOptionsPie = getPlotOptionsPie(1);
        outSeries.setPlotOptions(plotOptionsPie);

        Color[] colors = Arrays.copyOf(RedmineChart.colorsOut, 4);
        String[] value = new String[]{ERROR.getName(), OTHER.getName(), ERROR.getName(), OTHER.getName()};
        Number[] count = new Number[]{version.getTasks(OPENED, ERROR).size(),
                version.getTasks(OPENED, OTHER).size(),
                version.getTasks(CLOSED, ERROR).size(),
                version.getTasks(CLOSED, OTHER).size()};
        outSeries.setData(value, count, colors);
    }

    private PlotOptionsPie getPlotOptionsPie(int number) {
        PlotOptionsPie pieOptions = new PlotOptionsPie();
        if (number == 0) {
//            pieOptions.setInnerSize("0%");
            pieOptions.setInnerSize("0");
//            pieOptions.setSize("61%");
            pieOptions.setSize("16");
            pieOptions.setDataLabels(getLabels(-50));
        } else {
//            pieOptions.setInnerSize("70%");
            pieOptions.setInnerSize("17");
//            pieOptions.setSize("100%");
            pieOptions.setSize("26");
            pieOptions.setDataLabels(getLabels(-30));
        }
        return pieOptions;
    }

    private Labels getLabels(int dist) {
        Labels labels = new Labels();
        int count = version.getTasks().size() / 10;
        labels.setFormatter("this.y > " + count + " ? this.point.name +': '+ this.y : null");
        labels.setColor(new SolidColor(0, 0, 0));
        labels.setDistance(dist);
        return labels;
    }

    public DataSeries getOutSeries() {
        return outSeries;
    }

    public DataSeries getInSeries() {
        return inSeries;
    }
}
