package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaCountI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.pb.dashboard.monitoring.timings.tabsheet.ChartDetail;
import com.vaadin.addon.charts.LegendItemClickEvent;
import com.vaadin.addon.charts.LegendItemClickListener;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.VerticalLayout;

import java.util.*;

/**
 * Created by vlad
 * Date: 27.03.15_14:17
 */
public class CountsChart extends VerticalLayout implements SlaInterfaceListener {

    public static final int COLUMNS_MIN = 0;

    private SlaChart chart = new SlaChart();
    private Map<CountsType, Map<SlaInterfaceI, DataSeries>> seriesMap = new LinkedHashMap<>();
    private SlaInterfaceI selectedSla;
    private CountsType showType = CountsType.COUNT;

    private List<SlaInterfaceListener> listeners = new ArrayList<>();

    private static final long serialVersionUID = 6117737461019719204L;
    private ChartDetail chartDetail;

    public CountsChart() {
        init();
    }

    private void init() {
        Configuration conf = chart.getConfiguration();
        conf.getChart().setZoomType(ZoomType.Y);
        conf.setTitle("");

        YAxis yAxis = conf.getyAxis();
        yAxis.setTitle("Кол-во операций");
        yAxis.setMin(COLUMNS_MIN);

        PlotOptionsColumn plotConf = new PlotOptionsColumn();
        plotConf.setStacking(Stacking.NORMAL);
        conf.setPlotOptions(plotConf);

        initSeriesMap();

        chart.addPointClickListener(createPointClickListener());
        chart.addLegendItemClickListener(createLegendClickListener());
        addComponent(chart);
    }

    private void initSeriesMap() {
        for (CountsType type : CountsType.values()) {
            seriesMap.put(type, new LinkedHashMap<SlaInterfaceI, DataSeries>());
        }
    }

    private LegendItemClickListener createLegendClickListener() {
        return new LegendItemClickListener() {
            private static final long serialVersionUID = 1202306070500067429L;

            @Override
            public void onClick(LegendItemClickEvent event) {
                String name = event.getSeries().getName();

                SlaInterfaceI select = null;
                Set<SlaInterfaceI> slaInterfaces = seriesMap.get(CountsType.LEGEND).keySet();
                for (SlaInterfaceI slaInterface : slaInterfaces) {
                    if (slaInterface.getName().equals(name)) {
                        select = slaInterface;
                    }
                }
                if (select != null && select != selectedSla) {
                    for (SlaInterfaceListener listener : listeners) {
                        listener.change(select);
                    }
                }
            }
        };
    }

    private PointClickListener createPointClickListener() {
        return new PointClickListener() {
            private static final long serialVersionUID = -825930145221730008L;

            @Override
            public void onClick(PointClickEvent event) {
                if (chartDetail != null) {
                    Series series = event.getSeries();
                    String category = event.getCategory();
                    chartDetail.updatePeriod(series, category);
                    String interfaceName = series.getName();
                    int x = event.getAbsoluteX();
                    int y = event.getAbsoluteY();
                    chartDetail.showPopup(interfaceName, x, y);
                }
            }
        };
    }

    public void change(SlaInterfaceI slaInterface) {
        selectSla(slaInterface);
    }

    public void selectSla(SlaInterfaceI slaInterface) {
        for (CountsType type : CountsType.values()) {
            if (CountsType.LEGEND == type || type == showType) {
                Map<SlaInterfaceI, DataSeries> map = seriesMap.get(type);
                DataSeries prevSeries = map.get(selectedSla);
                if (prevSeries != null) {
                    prevSeries.setVisible(false);
                }
                DataSeries nowSeries = map.get(slaInterface);
                if (nowSeries != null) {
                    nowSeries.setVisible(true);
                }
            }
        }

        this.selectedSla = slaInterface;
    }

    public void setSeriesMap(Map<SlaInterfaceI, List<SlaCountI>> map) {
        clearSeries();
        fillSeries(map);
        setSeriesInChart();
        chart.drawChart();
    }

    private void clearSeries() {
        for (CountsType type : CountsType.values()) {
            seriesMap.get(type).clear();
        }
    }

    private void fillSeries(Map<SlaInterfaceI, List<SlaCountI>> map) {
        //for color
        int number = 0;
        for (Map.Entry<SlaInterfaceI, List<SlaCountI>> entry : map.entrySet()) {
            SlaInterfaceI slaInterface = entry.getKey();
            List<SlaCountI> slaCounts = entry.getValue();

            for (CountsType type : CountsType.values()) {
                DataSeries series = createDataSeries(slaInterface.getName(), type, number);
                seriesMap.get(type).put(slaInterface, series);
            }

            for (SlaCountI slaCount : slaCounts) {
                Date date = slaCount.getDateTime().toDate();
                DataSeriesItem countItem = new DataSeriesItem(date, slaCount.getCount());
                seriesMap.get(CountsType.COUNT).get(slaInterface).add(countItem);
                DataSeriesItem errorItem = new DataSeriesItem(date, slaCount.getErrorCount());
                seriesMap.get(CountsType.ERROR).get(slaInterface).add(errorItem);
            }
            number++;
        }
    }

    private void setSeriesInChart() {
        List<Series> array = new ArrayList<>();
        for (CountsType type : CountsType.values()) {
            array.addAll(seriesMap.get(type).values());
        }
        chart.getConfiguration().setSeries(array);
    }

    private DataSeries createDataSeries(String name, CountsType type, int number) {
        DataSeries dataSeries = new DataSeries(name);
        dataSeries.setVisible(false);
        PlotOptionsColumn plot = new PlotOptionsColumn();
        if (type == CountsType.ERROR) {
            plot.setColor(SolidColor.RED);
        } else {
            if (CollectionColors.COLORS.length > number) {
                plot.setColor(CollectionColors.COLORS[number]);
            }
        }
        if (type != CountsType.LEGEND) {
            plot.setShowInLegend(false);
        }
        dataSeries.setPlotOptions(plot);
        return dataSeries;
    }

    public List<SlaInterfaceListener> getListeners() {
        return listeners;
    }

    public void setShowType(CountsType type) {
        if (type != CountsType.LEGEND && showType != type) {
            setVisible(showType, false);
            setVisible(type, true);
            showType = type;
        }
    }

    private void setVisible(CountsType type, boolean visible) {
        Map<SlaInterfaceI, DataSeries> series = seriesMap.get(type);
        if (series != null) {
            DataSeries dataSeries = series.get(selectedSla);
            if (dataSeries != null) {
                dataSeries.setVisible(visible);
            }
        }
    }

    public void setChartDetail(ChartDetail chartDetail) {
        this.chartDetail = chartDetail;
    }

    public void setExtremes(SlaModelI model) {
        chart.setExtremes(model);
    }
}