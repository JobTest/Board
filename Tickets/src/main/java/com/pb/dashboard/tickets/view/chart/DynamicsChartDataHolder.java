package com.pb.dashboard.tickets.view.chart;

import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DynamicsChartDataHolder {

    private List<Series> series;

    public DynamicsChartDataHolder(int[] years) {
        setEmpty(years);
    }

    public DynamicsChartDataHolder(int[] years, Map<Integer, Integer>[] dbResult) {
        setData(years, dbResult);
    }

    public List<Series> getSeries() {
        return series;
    }

    private void setData(int[] years, Map<Integer, Integer>[] maps) {
        series = new ArrayList<Series>();
        series.add(getListSeries(years[1], maps[1]));
        series.add(getListSeries(years[0], maps[0]));
    }

    private ListSeries getListSeries(int year, Map<Integer, Integer> map) {
        ListSeries listSeries = new ListSeries(year + " год");
        for (Integer value : map.values()) {
            listSeries.addData(value);
        }
        return listSeries;
    }

    private void setEmpty(int[] years) {
        Map[] maps =  new LinkedHashMap[2];
        maps[0] = initMap();
        maps[1] = initMap();
        setData(years, maps);
    }

    private Map<Integer, Integer> initMap() {
        Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
        for (int i = 1; i < 12; i++) {
            map.put(i, 0); // init with zeroes for each month
        }
        return map;
    }
}
