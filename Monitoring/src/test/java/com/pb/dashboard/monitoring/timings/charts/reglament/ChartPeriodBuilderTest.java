package com.pb.dashboard.monitoring.timings.charts.reglament;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.timings.sla.SlaModel;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.XAxis;
import org.joda.time.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChartPeriodBuilderTest {

    private int reglamentHours = ChartPeriodManager.REGLAMENT_HOUR;
    private Chart chart;
    private Configuration conf;
    private XAxis xAxis;
    private SlaModel model;
    private LocalDate date = new LocalDate(2015, 2, 10);
    private LocalDate startDate = new LocalDate(2014, 4, 10);
    private LocalDate endDate = new LocalDate(2014, 4, 20);

    private ChartPeriodManagerI manager = new ChartPeriodManager();

    @Before
    public void setUp() throws Exception {
        chart = mock(Chart.class);
        conf = mock(Configuration.class);
        xAxis = new XAxis();

        when(conf.getxAxis()).thenReturn(xAxis);
        when(chart.getConfiguration()).thenReturn(conf);

        model = new SlaModel();
        model.setDate(date);
        model.setFromDate(startDate);
        model.setToDate(endDate);
    }

    @Test
    public void test10Min() throws Exception {
        assertEquals(1000 * 60 * 10, ChartPeriodManager.TIME_10_MIN);
    }

    @Test
    public void test1Hour() throws Exception {
        assertEquals(1000 * 60 * 60, ChartPeriodManager.TIME_1_HOUR);
    }

    @Test
    public void test1Day() throws Exception {
        assertEquals(1000 * 60 * 60 * 24, ChartPeriodManager.TIME_1_DAY);
    }

    @Test
    public void testTickIntervalFor10Min() throws Exception {
        model.setFilterRange(FilterRange.R10MIN);
        manager.setTickInterval(chart, model);
        assertEquals(ChartPeriodManager.TIME_10_MIN, xAxis.getTickInterval());
    }

    @Test
    public void testTickIntervalFor1Hour() throws Exception {
        model.setFilterRange(FilterRange.HOUR);
        manager.setTickInterval(chart, model);
        assertEquals(ChartPeriodManager.TIME_1_HOUR, xAxis.getTickInterval());
    }

    @Test
    public void testTickIntervalFor1Day() throws Exception {
        model.setFilterRange(FilterRange.DAY);
        manager.setTickInterval(chart, model);
        assertEquals(ChartPeriodManager.TIME_1_DAY, xAxis.getTickInterval());
    }

    @Test
    public void testExtremesFor10MinAndReglament() throws Exception {
        model.setFilterRange(FilterRange.R10MIN);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(date, 0), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(date.plusDays(1), 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testExtremesFor10MinAndNotReglament() throws Exception {
        model.setFilterRange(FilterRange.R10MIN);
        model.setReglament(false);
        manager.setExtremes(chart, model);
        assertEquals(convert(date, reglamentHours), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(date.plusDays(1), 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testExtremesFor1HourAndReglament() throws Exception {
        model.setFilterRange(FilterRange.HOUR);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(date, 0), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(date.plusDays(1), 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testExtremesFor1HourAndNotReglament() throws Exception {
        model.setFilterRange(FilterRange.HOUR);
        model.setReglament(false);
        manager.setExtremes(chart, model);
        assertEquals(convert(date, reglamentHours), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(date.plusDays(1), 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testExtremesFor1DayAndReglament() throws Exception {
        model.setFilterRange(FilterRange.DAY);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(startDate, 0), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(endDate, 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testExtremesFor1DayAndNotReglament() throws Exception {
        model.setFilterRange(FilterRange.DAY);
        model.setReglament(false);
        manager.setExtremes(chart, model);
        assertEquals(convert(startDate, 0), getLocalDateTime(xAxis.getMin()));
        assertEquals(convert(endDate, 0), getLocalDateTime(xAxis.getMax()));
    }

    @Test
    public void testMaxNotMorePresentFor10Min() throws Exception {
        LocalDateTime now = DateTime.now().toLocalDateTime();
        model.setDate(now.toLocalDate());
        model.setFilterRange(FilterRange.R10MIN);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(model.getDate(), 0), getLocalDateTime(xAxis.getMin()));
        assertTrue(equalsExp10Mills(now, getLocalDateTime(xAxis.getMax())));
    }

    @Test
    public void testMaxNotMorePresentFor1Hour() throws Exception {
        LocalDateTime now = DateTime.now().toLocalDateTime();
        model.setDate(now.toLocalDate());
        model.setFilterRange(FilterRange.HOUR);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(model.getDate(), 0), getLocalDateTime(xAxis.getMin()));
        assertTrue(equalsExp10Mills(now, getLocalDateTime(xAxis.getMax())));
    }

    @Test
    public void testMaxNotMorePresentFor1Day() throws Exception {
        LocalDate now = LocalDate.now();
        model.setToDate(now.plusDays(1));
        model.setFilterRange(FilterRange.DAY);
        model.setReglament(true);
        manager.setExtremes(chart, model);
        assertEquals(convert(startDate, 0), getLocalDateTime(xAxis.getMin()));
        assertTrue(equalsExp10Mills(convert(now, 0), getLocalDateTime(xAxis.getMax())));
    }

    private LocalDateTime getLocalDateTime(Number number) {
        return new LocalDateTime(number, DateTimeZone.UTC);
    }

    private LocalDateTime convert(LocalDate date, int hour) {
        return date.toLocalDateTime(new LocalTime(hour, 0));
    }

    private boolean equalsExp10Mills(LocalDateTime t1, LocalDateTime t2) {
        int exp = 50;
        long millis = t1.toDate().getTime() - t2.toDate().getTime();
        return exp >= Math.abs(millis);
    }
}