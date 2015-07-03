package com.pb.dashboard.quality.chart;

import com.vaadin.addon.charts.model.Series;

import java.util.List;

public class ChartDataHolder {

    String[] categories;
    List<Series> series;
    int size;

    public ChartDataHolder(String[] categories, List<Series> series, int size) {
        this.categories = categories;
        this.series = series;
        this.size = size;
    }

    public String[] getCategories() {
        return categories;
    }

    public List<Series> getSeries() {
        return series;
    }

    public int getSize() {
        return size;
    }
}
