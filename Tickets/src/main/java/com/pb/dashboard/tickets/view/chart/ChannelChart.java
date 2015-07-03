package com.pb.dashboard.tickets.view.chart;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;

public class ChannelChart extends Chart {

    private Configuration conf;

    public ChannelChart() {
        super(ChartType.BAR);
        setHeight("330px");
        conf = getConfiguration();
        conf.setTitle("");

        YAxis y = new YAxis();
        y.setMin(0);
        Title title = new Title("Количество");
        title.setVerticalAlign(VerticalAlign.HIGH);
        y.setTitle(title);
        conf.addyAxis(y);

        Legend legend = new Legend();
        legend.setReversed(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name +': '+ Highcharts.numberFormat(this.y, 0)");
        conf.setTooltip(tooltip);

        PlotOptionsBar plot = new PlotOptionsBar();
        plot.setDataLabels(new Labels(true));
        conf.setPlotOptions(plot);

        conf.disableCredits();
    }

    public void setSeries(ChannelChartDataHolder dataHolder) {
        conf.setSeries(dataHolder.getSeries());
        drawChart(conf);
    }

}
