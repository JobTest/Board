package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimitI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModelI;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pb.dashboard.monitoring.timings.TimingsType.*;

public final class ChartManager implements Serializable {

    public static final SolidColor TRANSPARENT = new SolidColor(0, 0, 0, 0);
    public static final SolidColor YELLOW = new SolidColor("#FFFDD3");
    public static final SolidColor RED = new SolidColor("#FFBDBA");
    private static final long serialVersionUID = -5065655803707950444L;

    private ChartManager() {
    }

    public static void setMetrics(Chart chart, IndicatorsModelI model) {
        List<Series> seriesList = new ArrayList<>();
        Map<InterfaceMetricI, List<MetricItem>> interfaceMetrics = model.getTimingMetricsMap();
        for (Map.Entry<InterfaceMetricI, List<MetricItem>> interfaceMetric : interfaceMetrics.entrySet()) {
            InterfaceMetricI metric = interfaceMetric.getKey();
            DataSeries series = new DataSeries(metric.getDescription());
            series.setPlotOptions(new PlotOptionsLine());
            seriesList.add(series);
            for (MetricItem metricItem : interfaceMetric.getValue()) {
                series.add(new DataSeriesItem(metricItem.getDateTime().toDate(), metricItem.getT95()));
            }
        }
        setPlotBand(chart, model.getLimit());
        setSeries(chart, seriesList);
    }

    public static void setTimings(Chart chart, IndicatorsModelI model) {
        DataSeries min = new DataSeries(MIN.getName());
        DataSeries max = new DataSeries(MAX.getName());
        DataSeries avg = new DataSeries(AVG.getName());
        DataSeries t90 = new DataSeries(P90.getName());
        DataSeries t95 = new DataSeries(P95.getName());
        DataSeries t99 = new DataSeries(P99.getName());
        min.setVisible(false);
        max.setVisible(false);
        Map<InterfaceMetricI, List<MetricItem>> timingMetricsMap = model.getTimingMetricsMap();
        List<MetricItem> metricItems = timingMetricsMap.get(model.getMetricSelected());
        for (MetricItem metric : metricItems) {
            min.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getMin()));
            max.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getMax()));
            avg.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getAvg()));
            t90.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getT90()));
            t95.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getT95()));
            t99.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getT99()));
        }
        setPlotBand(chart, model.getLimit());
        setSeries(chart, min, max, avg, t90, t95, t99);
    }

    public static void setCounts(Chart chart, IndicatorsModelI model) {
        DataSeries querySeries = new DataSeries(QUERIES.getName());
        DataSeries errorSeries = new DataSeries(ERRORS.getName());
        errorSeries.setyAxis(1);
        Map<InterfaceMetricI, List<MetricItem>> timingMetricsMap = model.getTimingMetricsMap();
        List<MetricItem> metricItems = timingMetricsMap.get(model.getMetricSelected());
        for (MetricItem metric : metricItems) {
            querySeries.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getCount()));
            errorSeries.add(new DataSeriesItem(metric.getDateTime().toDate(), metric.getErrorCount()));
        }
        chart.getConfiguration().setSeries(querySeries, errorSeries);
    }

    public static void setSeries(Chart chart, List<Series> seriesList) {
        Configuration conf = chart.getConfiguration();
        conf.setSeries(seriesList);
    }

    public static void setSeries(Chart chart, Series... seriesList) {
        Configuration conf = chart.getConfiguration();
        conf.setSeries(seriesList);
    }

    public static void setPlotBand(Chart chart, InterfaceLimitI limit) {
        PlotBand[] plotBands = createPlotBands(limit);
        chart.getConfiguration().getyAxis().setPlotBands(plotBands);
    }

    private static PlotBand[] createPlotBands(InterfaceLimitI limit) {
        if (limit == null || limit.getCritical() == 0) {
            return new PlotBand[0];
        }
        final PlotBand normal = new PlotBand(0, limit.getWarning(), TRANSPARENT);
        final PlotBand warning = new PlotBand(limit.getWarning(), limit.getCritical(), YELLOW);
        final PlotBand critical = new PlotBand(limit.getCritical(), Integer.MAX_VALUE, RED);
        return new PlotBand[]{normal, warning, critical};
    }

    public static void setLogarithmic(Chart chart, boolean logarithmic) {
        YAxis yAxis = chart.getConfiguration().getyAxis();
        if (logarithmic) {
            yAxis.setType(AxisType.LOGARITHMIC);
            yAxis.setMinorTickInterval(0.1);
            yAxis.setMin((Number) null);
        } else {
            yAxis.setType(null);
            yAxis.setMinorTickInterval((String) null);
            yAxis.setMin(0);
        }
    }
}