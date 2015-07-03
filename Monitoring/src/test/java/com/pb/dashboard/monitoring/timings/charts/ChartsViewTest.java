package com.pb.dashboard.monitoring.timings.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.XAxis;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChartsViewTest {

    private static final int METRIC_CHART = 1;
    private static final int TIMINGS_CHART = 2;
    private static final int COUNTS_CHART = 3;
    private ChartsModel model;
    private ChartsController controller;
    private ChartsView view;

    @Before
    public void setUp() throws Exception {
        model = new ChartsModel();
        controller = new ChartsController(model);
        view = new ChartsView(controller, model);
    }

    @Test
    public void testExtremes() throws Exception {
        view.setExtremes(100, 200);
        assertExtremes(100.0, 200.0);
    }

    private void assertExtremes(Number min, Number max) {
        assertEquals(min, getMetricXAxis().getMin());
        assertEquals(max, getMetricXAxis().getMax());
        assertEquals(min, getTimingsXAxis().getMin());
        assertEquals(max, getTimingsXAxis().getMax());
        assertEquals(min, getCountsXAxis().getMin());
        assertEquals(max, getCountsXAxis().getMax());
    }

    private XAxis getMetricXAxis() {
        return ((Chart) view.getComponent(METRIC_CHART)).getConfiguration().getxAxis();
    }

    private XAxis getTimingsXAxis() {
        return ((Chart) view.getComponent(TIMINGS_CHART)).getConfiguration().getxAxis();
    }

    private XAxis getCountsXAxis() {
        return ((Chart) view.getComponent(COUNTS_CHART)).getConfiguration().getxAxis();
    }
}