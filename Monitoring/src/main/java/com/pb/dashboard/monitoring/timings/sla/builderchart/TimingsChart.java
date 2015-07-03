package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;
import com.pb.dashboard.monitoring.timings.sla.SlaModelI;
import com.vaadin.addon.charts.LegendItemClickEvent;
import com.vaadin.addon.charts.LegendItemClickListener;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by vlad
 * Date: 27.03.15_14:17
 */
public class TimingsChart extends VerticalLayout implements SlaInterfaceListener {

    private static final long serialVersionUID = -8000056951585166636L;
    private static final Logger log = Logger.getLogger(TimingsChart.class);

    private static final String TIMINGS_TITLE = "Тайминги(SLA)";
    private static final String MILLI_SEC = "Миллисекунды";
    private static final TimingsType DEFAULT_TIMINGS_TYPE = TimingsType.PERCENT_95;
    private static final int TIMINGS_MIN = 0;

    private SlaChart chart = new SlaChart();
    private Map<TimingsType, DataSeries> seriesMap = new LinkedHashMap<>();
    private Map<SlaInterfaceI, List<SlaTimingI>> timingsMap = new LinkedHashMap<>();

    public TimingsChart() {
        init();
    }

    private void init() {
        Configuration conf = chart.getConfiguration();
        conf.setTitle(TIMINGS_TITLE);
        conf.getyAxis().setTitle(MILLI_SEC);
        conf.getyAxis().setMin(TIMINGS_MIN);
        for (TimingsType type : TimingsType.values()) {
            DataSeries dataSeries = createDataSeries(type);
            seriesMap.put(type, dataSeries);
        }
        conf.setSeries(getArrayDataSeries());
        chart.addLegendItemClickListener(createLegendClickListener());
        addComponent(chart);
    }

    private DataSeries[] getArrayDataSeries() {
        Collection<DataSeries> values = seriesMap.values();
        return values.toArray(new DataSeries[values.size()]);
    }

    private DataSeries createDataSeries(TimingsType type) {
        DataSeries series = new DataSeries(type.getName());

        PlotOptionsSpline plot = new PlotOptionsSpline();
        plot.setColor(type.getColor());
        series.setPlotOptions(plot);

        boolean isDefaultType = (type == DEFAULT_TIMINGS_TYPE);
        series.setVisible(isDefaultType);

        return series;
    }

    private LegendItemClickListener createLegendClickListener() {
        return new LegendItemClickListener() {
            private static final long serialVersionUID = 1490823603289673216L;

            @Override
            public void onClick(LegendItemClickEvent event) {
                clickOnItemLegend(event);
            }
        };
    }

    private void clickOnItemLegend(LegendItemClickEvent event) {
        try {
            String name = event.getSeries().getName();
            TimingsType type = TimingsType.get(name);

            DataSeries series = seriesMap.get(type);
            if (series != null) {
                boolean visible = series.isVisible();
                series.setVisible(!visible);
            }
        } catch (Exception e) {
            log.error("LegendItemClick[" + event + "]", e);
        }
    }

    public void setTimingsBySlaInterface(Map<SlaInterfaceI, List<SlaTimingI>> map) {
        this.timingsMap = map;
    }

    @Override
    public void change(SlaInterfaceI slaInterface) {
        setSlaInterface(slaInterface);
    }

    private void setSlaInterface(SlaInterfaceI slaInterface) {
        List<SlaTimingI> slaTimings = timingsMap.get(slaInterface);
        clearSeries();
        if (slaTimings != null) {
            fillSeries(slaTimings);
        }
        chart.drawChart();
    }

    private void clearSeries() {
        for (TimingsType type : TimingsType.values()) {
            seriesMap.get(type).setData();
        }
    }

    private void fillSeries(List<SlaTimingI> slaTimings) {
        for (SlaTimingI slaTiming : slaTimings) {
            for (TimingsType type : TimingsType.values()) {
                DataSeriesItem item = createItem(slaTiming, type);
                seriesMap.get(type).add(item);
            }
        }
    }

    private DataSeriesItem createItem(SlaTimingI slaTiming, TimingsType type) {
        Date date = slaTiming.getDateTime().toDate();
        int timing = slaTiming.getTimeTiming(TimingsTypeConvert.toSlaTimings(type));
        return new DataSeriesItem(date, timing);
    }

    public SlaChart getChart() {
        return chart;
    }


    public void setExtremes(SlaModelI model) {
        chart.setExtremes(model);
    }
}
