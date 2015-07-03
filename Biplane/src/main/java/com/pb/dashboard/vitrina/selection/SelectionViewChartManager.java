package com.pb.dashboard.vitrina.selection;

import com.pb.dashboard.vitrina.core.types.Period;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.themes.VaadinTheme;

import java.text.SimpleDateFormat;
import java.util.*;

public class SelectionViewChartManager {

    private Chart regChart;
    private Configuration regChartConf;

    private Chart compChart;
    private Configuration compChartConf;

    private SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy, EEE", new Locale("RU"));

    private Color[] colors;

    public SelectionViewChartManager() {
        Color[] vaadinColors = new VaadinTheme().getColors();
        colors = Arrays.copyOf(vaadinColors, 3);
    }

    public Chart getRegularChart() {
        if (regChart == null) initRegChart();
        return regChart;
    }

    public Chart getCompChart() {
        if (compChart == null) initBarChart();
        return compChart;
    }

    private void initRegChart() {
        regChart = new Chart(ChartType.AREASPLINE);
        regChart.setHeight("380px");
        regChartConf = regChart.getConfiguration();
        regChartConf.setTitle(new Title("Платежи по времени"));

        XAxis xAxis = new XAxis();

        Period[] periodsEnum = Period.values();
        String[] periods = new String[Period.values().length];
        int index = 0;
        for (int i = periodsEnum.length - 1; i >= 0; i--) {
            periods[index] = periodsEnum[i].getHour();
            index++;
        }

        xAxis.setCategories(periods);
        regChartConf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle(new Title("Количество"));
        regChartConf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setHeaderFormat("");
        tooltip.setPointFormat("{point.y}");
        regChartConf.setTooltip(tooltip);

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setFillOpacity(0.5);
        regChartConf.setPlotOptions(plotOptions);
    }

    public void initBarChart() {
        compChart = new Chart(ChartType.BAR);
        compChart.addStyleName("comp-chart");
        compChart.setHeight("380px");
        compChartConf = compChart.getConfiguration();
        compChartConf.setTitle("Всего за день");

        final String[] categories = new String[]{"Прием", "Наличные", "Безналичные", "Физ.лица",
                "Юр.лица", "Задолженность"};

        XAxis x = new XAxis();
        x.setCategories(categories);
        x.setTitle((String) null);
        compChartConf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        Title title = new Title("");
        title.setVerticalAlign(VerticalAlign.HIGH);
        y.setTitle(title);
        compChartConf.addyAxis(y);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name +': '+ this.y");
        compChartConf.setTooltip(tooltip);

        PlotOptionsBar plot = new PlotOptionsBar();
        plot.setDataLabels(new Labels(true));
        compChartConf.setPlotOptions(plot);
        compChartConf.disableCredits();
    }

    public void setRegularChartSeries(List<Number> cashList, List<Number> noCashList, List<Number> debtList,
                                      List<Number> totals, Date date) {

        ListSeries cash = new ListSeries("Наличные");
        ListSeries noCash = new ListSeries("Безналичные");
        ListSeries debt = new ListSeries("Задолженность");

        /* Setting series colors */

        PlotOptionsAreaSpline cashOptions = new PlotOptionsAreaSpline();
        cashOptions.setColor(colors[0]);
        cash.setPlotOptions(cashOptions);

        PlotOptionsAreaSpline noCashOptions = new PlotOptionsAreaSpline();
        noCashOptions.setColor(colors[1]);
        noCash.setPlotOptions(noCashOptions);

        PlotOptionsAreaSpline debtOptions = new PlotOptionsAreaSpline();
        debtOptions.setColor(colors[2]);
        debt.setPlotOptions(debtOptions);

        regChartConf.setSeries(cash, noCash, debt);

        Collections.reverse(cashList);
        Collections.reverse(noCashList);
        Collections.reverse(debtList);

        cash.setData(cashList);
        noCash.setData(noCashList);
        debt.setData(debtList);

        regChart.drawChart();

        // Comp chart setup

        ListSeries compSeries = new ListSeries(sdf.format(date));

        // Color adjustment
        PlotOptionsBar compOptions = new PlotOptionsBar();
        compOptions.setColor(colors[0]);
        compSeries.setPlotOptions(compOptions);

        compChartConf.setSeries(compSeries);

        compSeries.setData(totals);

        compChart.drawChart();

    }

    public void setComparisonChartSeries(List<Date> dates, List<List<Number>> regChartData, List<Number> compChartMainDate,
                                         List<Number> compChartCompDate) {

        // Reg chart setup

        List<Number> mainDateList = regChartData.get(0);
        List<Number> dateToCompareList = regChartData.get(1);

        ListSeries mainDate = new ListSeries(sdf.format(dates.get(0)));
        ListSeries dateToCompare = new ListSeries(sdf.format(dates.get(1)));

        // Colors
        PlotOptionsAreaSpline mainOptions = new PlotOptionsAreaSpline();
        mainOptions.setColor(colors[0]);
        mainDate.setPlotOptions(mainOptions);
        PlotOptionsAreaSpline compOptions = new PlotOptionsAreaSpline();
        compOptions.setColor(colors[1]);
        dateToCompare.setPlotOptions(compOptions);

        regChartConf.setSeries(mainDate, dateToCompare);

        Collections.reverse(mainDateList);
        Collections.reverse(dateToCompareList);

        mainDate.setData(mainDateList);
        dateToCompare.setData(dateToCompareList);

        regChart.drawChart();

        // Comp chart setup

        ListSeries compChartMain = new ListSeries(sdf.format(dates.get(0)));
        ListSeries compChartComp = new ListSeries(sdf.format(dates.get(1)));

        //Colors
        PlotOptionsBar compChartMainOptions = new PlotOptionsBar();
        compChartMainOptions.setColor(colors[0]);
        compChartMain.setPlotOptions(compChartMainOptions);
        PlotOptionsBar compChartCompOptions = new PlotOptionsBar();
        compChartCompOptions.setColor(colors[1]);
        compChartComp.setPlotOptions(compChartCompOptions);

        compChartConf.setSeries(compChartMain, compChartComp);

        compChartMain.setData(compChartMainDate);
        compChartComp.setData(compChartCompDate);

        compChart.drawChart();

    }

}
