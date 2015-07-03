package com.pb.dashboard.monitoring.timings.charts;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceLimit;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetric;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.timings.TimingsType;
import com.pb.dashboard.monitoring.timings.tabsheet.IndicatorsModel;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChartManagerTest {

    private static final InterfaceMetric METRIC1 = new InterfaceMetric(5, "metric1", 1);
    private static final InterfaceMetric METRIC2 = new InterfaceMetric(6, "metric2", 0);
    private static final LocalDateTime DATE_TIME1 = new LocalDateTime(2014, 5, 6, 10, 13, 0);
    private static final LocalDateTime DATE_TIME2 = new LocalDateTime(2014, 5, 6, 10, 13, 10);
    private static final LocalDateTime DATE_TIME3 = new LocalDateTime(2014, 5, 6, 10, 13, 20);

    private MetricItem metricItem;
    private Chart chart;
    private Configuration conf;
    private YAxis yAxis;

    private Map<InterfaceMetricI, List<MetricItem>> timingMetricsMap = new LinkedHashMap<>();

    @Before
    public void setUp() throws Exception {
        chart = mock(Chart.class);
        conf = new Configuration();
        yAxis = new YAxis();
        conf.addyAxis(yAxis);
        when(chart.getConfiguration()).thenReturn(conf);

        initTimingMetricsMap();
    }

    private void initTimingMetricsMap() {
        List<MetricItem> items1 = new ArrayList<>();
        items1.add(new MetricItem(DATE_TIME1, 1, 11, 21, 22, 23, 24, 25, 26));
        items1.add(metricItem = new MetricItem(DATE_TIME2, 2, 12, 22, 32, 42, 52, 62, 72));
        items1.add(new MetricItem(DATE_TIME3, 3, 13, 23, 33, 43, 53, 63, 73));

        List<MetricItem> items2 = new ArrayList<>();
        items2.add(new MetricItem(DATE_TIME1, 11, 111, 211, 221, 231, 241, 251, 261));
        items2.add(new MetricItem(DATE_TIME2, 22, 122, 222, 322, 422, 522, 622, 722));
        items2.add(new MetricItem(DATE_TIME3, 33, 133, 233, 333, 433, 533, 633, 733));

        timingMetricsMap.put(METRIC1, items1);
        timingMetricsMap.put(METRIC2, items2);
    }

    @Test
    public void testLogarithmicType() throws Exception {
        ChartManager.setLogarithmic(chart, true);
        assertEquals(AxisType.LOGARITHMIC, yAxis.getType());
    }

    @Test
    public void testArithmeticType() throws Exception {
        ChartManager.setLogarithmic(chart, false);
        assertNull(yAxis.getType());
    }

    @Test
    public void testLogarithmicMinorTickInterval() throws Exception {
        ChartManager.setLogarithmic(chart, true);
        assertEquals(0.1, yAxis.getMinorTickInterval());
    }

    @Test
    public void testArithmeticMinorTickInterval() throws Exception {
        ChartManager.setLogarithmic(chart, false);
        assertNull(yAxis.getMinorTickInterval());
    }

    @Test
    public void testLogarithmicMin() throws Exception {
        ChartManager.setLogarithmic(chart, true);
        assertNull(yAxis.getMin());
    }

    @Test
    public void testArithmeticMin() throws Exception {
        ChartManager.setLogarithmic(chart, false);
        assertEquals(0, yAxis.getMin());
    }

    @Test
    public void testPlotBands() throws Exception {
        InterfaceLimit limit = new InterfaceLimit();
        limit.setWarning(50);
        limit.setCritical(100);
        ChartManager.setPlotBand(chart, limit);
        assertPlots(yAxis.getPlotBands(), 0, 50, 100, Integer.MAX_VALUE);
    }

    @Test
    public void testPlotBandsCritical0() throws Exception {
        InterfaceLimit limit = new InterfaceLimit();
        limit.setWarning(50);
        limit.setCritical(0);
        ChartManager.setPlotBand(chart, limit);
        assertPlots(yAxis.getPlotBands(), 0);
    }

    @Test
    public void testPlotBandsWarning0() throws Exception {
        InterfaceLimit limit = new InterfaceLimit();
        limit.setWarning(0);
        limit.setCritical(50);
        ChartManager.setPlotBand(chart, limit);
        assertPlots(yAxis.getPlotBands(), 0, 0, 50, Integer.MAX_VALUE);
    }

    @Test
    public void testPlotBandsNull() throws Exception {
        ChartManager.setPlotBand(chart, null);
        assertPlots(yAxis.getPlotBands(), 0);
    }

    @Test
    public void testSetMetrics() throws Exception {
        IndicatorsModel model = new IndicatorsModel();
        model.setTimingMetricsMap(timingMetricsMap);
        ChartManager.setMetrics(chart, model);
        List<Series> series = conf.getSeries();
        assertEquals(2, series.size());
        DataSeries dataSeries1 = (DataSeries) series.get(0);
        DataSeries dataSeries2 = (DataSeries) series.get(1);
        assertEquals(METRIC1.getDescription(), dataSeries1.getName());
        assertEquals(METRIC2.getDescription(), dataSeries2.getName());
        DataSeriesItem item1 = dataSeries1.get(1);
        assertEquals(metricItem.getT95(), item1.getY());
        assertEquals(DATE_TIME2, new LocalDateTime(item1.getX(), DateTimeZone.UTC));
    }

    @Test
    public void testSetTimings() throws Exception {
        IndicatorsModel model = new IndicatorsModel();
        model.setTimingMetricsMap(timingMetricsMap);
        ChartManager.setTimings(chart, model);
        List<Series> series = conf.getSeries();
        assertEquals(6, series.size());
        assertEquals(TimingsType.MIN.getName(), series.get(0).getName());
        assertEquals(TimingsType.MAX.getName(), series.get(1).getName());
        assertEquals(TimingsType.AVG.getName(), series.get(2).getName());
        assertEquals(TimingsType.P90.getName(), series.get(3).getName());
        assertEquals(TimingsType.P95.getName(), series.get(4).getName());
        assertEquals(TimingsType.P99.getName(), series.get(5).getName());
        assertEquals(metricItem.getMin(), ((DataSeries) series.get(0)).get(1).getY());
        assertEquals(metricItem.getMax(), ((DataSeries) series.get(1)).get(1).getY());
        assertEquals(metricItem.getAvg(), ((DataSeries) series.get(2)).get(1).getY());
        assertEquals(metricItem.getT90(), ((DataSeries) series.get(3)).get(1).getY());
        assertEquals(metricItem.getT95(), ((DataSeries) series.get(4)).get(1).getY());
        assertEquals(metricItem.getT99(), ((DataSeries) series.get(5)).get(1).getY());
    }

    @Test
    public void testSetCounts() throws Exception {
        IndicatorsModel model = new IndicatorsModel();
        model.setTimingMetricsMap(timingMetricsMap);
        ChartManager.setCounts(chart, model);
        List<Series> series = conf.getSeries();
        assertEquals(2, series.size());
        assertEquals(TimingsType.QUERIES.getName(), series.get(0).getName());
        assertEquals(TimingsType.ERRORS.getName(), series.get(1).getName());
        assertEquals(metricItem.getCount(), ((DataSeries) series.get(0)).get(1).getY());
        assertEquals(metricItem.getErrorCount(), ((DataSeries) series.get(1)).get(1).getY());
    }

    private void assertPlots(List<PlotBand> plots, int... t) {
        assertEquals(t.length - 1, plots.size());
        for (int i = 0; i < plots.size(); i++) {
            assertEquals(t[i], plots.get(i).getFrom());
            assertEquals(t[i + 1], plots.get(i).getTo());
        }
    }
}