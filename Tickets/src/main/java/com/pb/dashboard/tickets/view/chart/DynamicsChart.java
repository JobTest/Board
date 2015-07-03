package com.pb.dashboard.tickets.view.chart;

import com.pb.dashboard.core.model.Month;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;

public class DynamicsChart extends Chart {

    private Configuration conf;

    public DynamicsChart() {
        super(ChartType.COLUMN);
        setHeight("330px");
        setCaption("Динамика продаж");
        conf = getConfiguration();
        conf.getTitle().setText(null);

        XAxis x = new XAxis();
        String[] categories = new String[12]; // holder for months of the year
        int i = 0;
        for (Month month : Month.values()) {
            if (month != Month.YEAR) categories[i++] = month.getName();
        }
        x.setCategories(categories);
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        y.setTitle("Количество");
        conf.addyAxis(y);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x + ' : ' + Highcharts.numberFormat(this.y, 0)");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);

        conf.disableCredits();
    }

    public void setSeries(DynamicsChartDataHolder dataHolder) {
        conf.setSeries(dataHolder.getSeries());
        drawChart(conf);
    }

}
