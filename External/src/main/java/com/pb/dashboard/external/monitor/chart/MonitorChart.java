package com.pb.dashboard.external.monitor.chart;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartClickListener;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.ui.ComboBox;

import java.util.Date;

public class MonitorChart extends Chart {

    private Complex complex;
    private FilterRange filterRange;
    private boolean isSystemErrorsChart;
    private ChartClickListener chartClickListenerRef;
    private PointClickListener pointClickListenerRef;
    private boolean isForAllInterfaces;
    private Date[] dates;
    private ComboBox comboBox;

    public MonitorChart(ChartType type) {
        super(type);
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public void setFilterRange(FilterRange filterRange) {
        this.filterRange = filterRange;
    }

    public boolean isSystemErrorsChart() {
        return isSystemErrorsChart;
    }

    public void setSystemErrorsChart(boolean systemErrorsChart) {
        isSystemErrorsChart = systemErrorsChart;
    }

    public ChartClickListener getChartClickListenerRef() {
        return chartClickListenerRef;
    }

    public void setChartClickListenerRef(ChartClickListener chartClickListenerRef) {
        this.chartClickListenerRef = chartClickListenerRef;
        addChartClickListener(chartClickListenerRef);
    }

    public PointClickListener getPointClickListenerRef() {
        return pointClickListenerRef;
    }

    public void setPointClickListenerRef(PointClickListener pointClickListenerRef) {
        this.pointClickListenerRef = pointClickListenerRef;
        addPointClickListener(pointClickListenerRef);
    }

    public boolean isForAllInterfaces() {
        return isForAllInterfaces;
    }

    public void setForAllInterfaces(boolean forAllInterfaces) {
        isForAllInterfaces = forAllInterfaces;
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }

    public void removeChartClickListener() {
        if (chartClickListenerRef != null)
            removeChartClickListener(chartClickListenerRef);
    }

    public void removePointClickListener() {
        if (pointClickListenerRef != null)
            removePointClickListener(pointClickListenerRef);
    }
}
