package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.timings.charts.reglament.ChartPeriodManager;
import com.pb.dashboard.monitoring.timings.chooser.Chooser;
import com.pb.dashboard.monitoring.timings.chooser.ChooserItem;
import com.pb.dashboard.monitoring.timings.chooser.ChooserListener;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;
import com.vaadin.addon.charts.*;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


/**
 * Created by vlad
 * Date: 12.12.14_15:08
 */
public class ChartsView extends VerticalLayout implements Observer<ChartsModelI> {

    private static final String METRICS = "Метрики";
    private static final String TIMINGS = "Тайминги";
    private static final String COUNTS_QUERY_ERROR = "Количество запросов и ошибок в разрезе временных периодов";
    private static final String MILLISEC = "Миллисекунды";
    private static final String COUNTS = "Количество, шт.";
    private static final String RESET_ZOOM = "Reset zoom";

    private static final long serialVersionUID = 6528423449877082159L;

    private ChartsControllerI controller;
    private ChartsModelI model;

    private Chooser chooser;
    private Button resetButton;
    private Chart metrics;
    private Chart timings;
    private Chart counts;

    private ChartPeriodManager periodManager;

    public void setPeriodManager(ChartPeriodManager periodManager) {
        this.periodManager = periodManager;
    }

    public ChartsView(ChartsControllerI controller, ChartsModelI model) {
        this.controller = controller;
        this.model = model;
        init();
        model.addObserver(this);
    }

    private void init() {
        initChooserAndReset();
        initMetrics();
        initTimings();
        initCounts();
        initPointClickListener();
        initSectionListeners();
    }

    private void initChooserAndReset() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        chooser = new Chooser();
        chooser.setListener(new ChooserListener() {
            @Override
            public void change(ChooserItem item) {
                controller.setChooserItem(item);
            }
        });
        layout.addComponent(chooser);

        resetButton = new Button(RESET_ZOOM);
        resetButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.resetZoom();
            }
        });
        layout.addComponent(resetButton);
        layout.setComponentAlignment(resetButton, Alignment.MIDDLE_RIGHT);
        addComponent(layout);
    }

    private void initMetrics() {
        metrics = ChartBuilder.createLineChart(METRICS, MILLISEC);
        addComponent(metrics);
    }

    private void initTimings() {
        timings = ChartBuilder.createLineChart(TIMINGS, MILLISEC);
        addComponent(timings);
    }

    private void initCounts() {
        counts = ChartBuilder.createLineChart(COUNTS_QUERY_ERROR, COUNTS);
        YAxis yAxisRight = new YAxis();
        yAxisRight.setTitle(COUNTS);
        yAxisRight.setMin(0);
        yAxisRight.setOpposite(true);
        counts.getConfiguration().addyAxis(yAxisRight);
        addComponent(counts);
    }

    private void initPointClickListener() {
        metrics.addPointClickListener(createPointClickListener());
        timings.addPointClickListener(createPointClickListener());
        counts.addPointClickListener(createPointClickListener());
    }

    private PointClickListener createPointClickListener() {
        return new PointClickListener() {
            private static final long serialVersionUID = -4277102610909548035L;

            @Override
            public void onClick(PointClickEvent event) {
                controller.pointClick(event);
            }
        };
    }

    private void initSectionListeners() {
        metrics.addChartSelectionListener(createSelectionListener());
        counts.addChartSelectionListener(createSelectionListener());
        timings.addChartSelectionListener(createSelectionListener());
    }

    private ChartSelectionListener createSelectionListener() {
        return new ChartSelectionListener() {
            @Override
            public void onSelection(ChartSelectionEvent event) {
                controller.selectionChart(event.getSelectionStart(), event.getSelectionEnd());
            }
        };
    }

    @Override
    public void modified(ChartsModelI model) {
        updateMetrics(model);
        updateTimings(model);
        updateCounts(model);
        updateInterval(model);
        redrawCharts();
    }

    private void updateMetrics(ChartsModelI model) {
        IndicatorsModelI indicatorsModel = model.getIndicatorsModel();
        boolean visible = indicatorsModel.getMetricSelected().isMain();
        if (visible) {
            ChartManager.setLogarithmic(metrics, model.isLogarithmic());
            ChartManager.setMetrics(metrics, indicatorsModel);
        }
        metrics.setVisible(visible);
    }

    private void updateTimings(ChartsModelI model) {
        ChartManager.setTimings(timings, model.getIndicatorsModel());
    }

    private void updateCounts(ChartsModelI model) {
        ChartManager.setCounts(counts, model.getIndicatorsModel());
    }

    public void updateInterval(ChartsModelI model) {
        periodManager.setExtremes(metrics, model.getSlaModel());
        periodManager.setTickInterval(metrics, model.getSlaModel());
        periodManager.setExtremes(timings, model.getSlaModel());
        periodManager.setTickInterval(timings, model.getSlaModel());
        periodManager.setExtremes(counts, model.getSlaModel());
        periodManager.setTickInterval(counts, model.getSlaModel());
    }

    public void setExtremes(double min, double max) {
        metrics.getConfiguration().getxAxis().setExtremes(min, max);
        timings.getConfiguration().getxAxis().setExtremes(min, max);
        counts.getConfiguration().getxAxis().setExtremes(min, max);
        redrawCharts();
    }

    public void redrawCharts() {
        metrics.drawChart();
        timings.drawChart();
        counts.drawChart();
    }
}