package com.pb.dashboard.external.monitor.chart;

import com.pb.dashboard.core.component.DPopupWindow;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.external.monitor.chart.parsetime.ParserTime;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.pb.dashboard.external.monitor.logic.ErrorsDataManager;
import com.vaadin.addon.charts.ChartClickEvent;
import com.vaadin.addon.charts.ChartClickListener;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import java.io.Serializable;
import java.util.*;

import static com.pb.dashboard.external.monitor.sessions.SessionsParam.*;

public class ChartBuilder implements Serializable {

    private static final long serialVersionUID = -4195895845460493868L;
    public static final int QUERY = 3;
    private DPopupWindow popup;
    private ErrorsDataManager dataManager;

    public ChartBuilder(ErrorsDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public MonitorChart getChart(String title, String yCategoryTitle, boolean legendEnabled) {
        MonitorChart chart = new MonitorChart(ChartType.COLUMN);
        chart.setHeight("185px");

        Configuration conf = chart.getConfiguration();
        conf.getChart().setBackgroundColor(new SolidColor(255, 255, 255, 0));
        conf.setTitle(title);
        conf.getLegend().setEnabled(legendEnabled);

        XAxis xAxis = conf.getxAxis();
        xAxis.setTickmarkPlacement(TickmarkPlacement.ON);
        xAxis.setLabels(createLabels());
//        conf.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setMinorTickInterval(0.1);
        yAxis.setType(AxisType.LOGARITHMIC);
        yAxis.setTitle(yCategoryTitle);
        conf.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        String format;
        format = "'<b>' + this.series.name + '</b><br/>' + this.y";
        tooltip.setFormatter(format);
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setBorderWidth(0);
        plot.setShadow(false);
        conf.setPlotOptions(plot);
        conf.disableCredits();
        return chart;
    }

    private Labels createLabels() {
        Labels labels = new Labels();
        labels.setRotation(-60);
        labels.setAlign(HorizontalAlign.RIGHT);
        labels.setStyle(createStyle());
        return labels;
    }

    private Style createStyle() {
        Style style = new Style();
        style.setFontSize("12px");
        style.setFontFamily("Verdana, sans-serif");
        return style;
    }

    public void setChartSeries(MonitorChart chart, ChartData dataHolder) {
        chart.getConfiguration().getxAxis().setCategories(dataHolder.getCategories());
        List<Series> series = dataHolder.getSeries();
        if (series.size() == QUERY) {

            PlotOptionsColumn ok = new PlotOptionsColumn();
            ok.setColor(SolidColor.GREEN);
            ListSeries seriesOk = (ListSeries) series.get(0);
            seriesOk.setPlotOptions(ok);

            PlotOptionsColumn err400 = new PlotOptionsColumn();
            err400.setColor(SolidColor.RED);
            ListSeries series400 = (ListSeries) series.get(1);
            series400.setPlotOptions(err400);

            PlotOptionsColumn err500 = new PlotOptionsColumn();
            err500.setColor(SolidColor.ORANGE);
            ListSeries series500 = (ListSeries) series.get(2);
            series500.setPlotOptions(err500);

            chart.getConfiguration().setSeries(seriesOk, series400, series500);
        } else {
            PlotOptionsColumn plot = new PlotOptionsColumn();
            plot.setStacking(Stacking.NORMAL);
            chart.getConfiguration().setPlotOptions(plot);
            List<Series> serieses = new ArrayList<>(series);
            if (dataHolder.getSeriesError499() != null) {
                serieses.add(dataHolder.getSeriesError499());
            }
            chart.getConfiguration().setSeries(serieses);
        }
        chart.drawChart();
    }

    public void setListeners(final MonitorChart chart) {
        removeListeners(chart);
        chart.setPointClickListenerRef(createPointClickListener(chart));
        chart.setChartClickListenerRef(createClickListener());
    }

    private PointClickListener createPointClickListener(final MonitorChart chart) {
        return new PointClickListener() {
            @Override
            public void onClick(PointClickEvent event) {
                String time = event.getCategory();
                if (chart.isForAllInterfaces()) {
                    closeWindow();

                    VerticalLayout panelButtons = createPanelButtons(chart, time);
                    popup = new DPopupWindow();
                    popup.open(event.getAbsoluteX(), event.getAbsoluteY(), panelButtons);
                } else {
                    String bpInterface = ((DInterfaceI) chart.getComboBox().getValue()).getName();
                    String params = buildUrlParams(chart, time, bpInterface);
                    clickMetric(params);
                }
            }
        };
    }

    private ChartClickListener createClickListener() {
        return new ChartClickListener() {
            @Override
            public void onClick(ChartClickEvent chartClickEvent) {
                closeWindow();
            }
        };
    }

    private VerticalLayout createPanelButtons(MonitorChart chart, String time) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);

        ParserTime parser = ParserTime.getParser(chart.getFilterRange());
        if (chart.getDates() != null && chart.getDates().length > 0) {
            parser.setDateDefault(chart.getDates()[0]);
        }
        String format = parser.format(time, parser.getChartPattern());
        Calendar calendar = parser.parse(time);

        Map<String, Integer> map = dataManager.getErrCntByInterface(chart.getComplex(), chart.isSystemErrorsChart(), chart.getFilterRange(), calendar);

        for (Map.Entry<String, Integer> item : map.entrySet()) {
            String bpInterface = item.getKey();
            Integer count = item.getValue();
            String caption = bpInterface + " : " + count;

            Button button = createButton(caption, chart, format, bpInterface);
            layout.addComponent(button);
        }
        return layout;
    }

    private Button createButton(String caption, final MonitorChart chart, final String format, final String bpInterface) {
        Button button = new Button(caption);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                closeWindow();
                String params = buildUrlParams(chart, format, bpInterface);
                clickMetric(params);
            }
        });
        return button;
    }

    private void clickMetric(String params) {
        String path = Dashboard.Biplane.Support.Sessions.PATH;
        UI.getCurrent().getNavigator().navigateTo(path + "/" + params);
    }

    private void removeListeners(MonitorChart chart) {
        chart.removeChartClickListener();
        chart.removePointClickListener();
    }

    private String buildUrlParams(MonitorChart chart, String time, String pbInterface) {
        String date = getFormattedDate(chart.getFilterRange(), time, chart.getDates());

        UrlParamBuilder params = new UrlParamBuilder()
                .addParam(COMPLEX.getName(), chart.getComplex().getId())
                .addParam(SYSTEM.getName(), chart.isSystemErrorsChart())
                .addParam(RANGE.getName(), chart.getFilterRange().getId())
                .addParam(DATE.getName(), date)
                .addParam(INTERFACE.getName(), pbInterface);
        return params.toString();
    }

    private String getFormattedDate(FilterRange range, String time, Date[] dates) {
        ParserTime parser = ParserTime.getParser(range);
        final int firstElement = 0;
        if (checkCorrect(dates)) {
            parser.setDateDefault(dates[firstElement]);
        }
        return parser.format(time, parser.getUrlPattern());
    }

    private boolean checkCorrect(Date[] dates) {
        return dates != null && dates.length > 0;
    }

    private void closeWindow() {
        if (popup != null) {
            popup.close();
        }
    }
}
