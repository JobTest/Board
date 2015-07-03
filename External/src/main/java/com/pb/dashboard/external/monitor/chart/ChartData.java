package com.pb.dashboard.external.monitor.chart;

import com.vaadin.addon.charts.model.Series;

import java.io.Serializable;
import java.util.List;

public class ChartData implements Serializable {

    private String[] categories;
    private List<Series> series;
    private Series seriesError499;

    public ChartData(String[] categories, List<Series> series) {
        this.categories = categories;
        this.series = series;
    }

    public ChartData(String[] categories, List<Series> series, Series seriesError499) {
        this.categories = categories;
        this.series = series;
        this.seriesError499 = seriesError499;
    }

    public Series getSeriesError499() {
        return seriesError499;
    }

    public void setSeriesError499(Series seriesError499) {
        this.seriesError499 = seriesError499;
    }

    public String[] getCategories() {
        return categories;
    }

    public List<Series> getSeries() {
        return series;
    }
}
