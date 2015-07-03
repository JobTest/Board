package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StrategyFactoryTest {

    private static LoadData loadData;
    private static List<InterfaceMetricI> metrics;
    private static LocalDate date;
    private static LocalDate from;
    private static LocalDate to;

    @BeforeClass
    public static void up() throws Exception {
        loadData = mock(LoadData.class);

        metrics = new ArrayList<>();
        date = new LocalDate(2014, 10, 1);
        from = new LocalDate(2014, 11, 2);
        to = new LocalDate(2014, 12, 10);
    }

    @Test
    public void testCallGetMetricsBy10Min() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.R10MIN, loadData);
        strategy.getTimingsMetrics(metrics, date, from, to);
        verify(loadData).getTimingMetricsBy10Min(metrics, date);
    }

    @Test
    public void testCallGetMetricsByHour() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.HOUR, loadData);
        strategy.getTimingsMetrics(metrics, date, from, to);
        verify(loadData).getTimingMetricsByHour(metrics, date);
    }

    @Test
    public void testCallGetMetricsByDay() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.DAY, loadData);
        strategy.getTimingsMetrics(metrics, date, from, to);
        verify(loadData).getTimingMetricsByDay(metrics, from, to);
    }

    @Test
    public void test10MinReloadTime() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.R10MIN, loadData);
        assertEquals(StrategyBy10Min.MILLISEC_IN_10MIN, strategy.getReloadTimeMSec());
    }

    @Test
    public void testHourReloadTime() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.HOUR, loadData);
        assertEquals(StrategyByHour.MILLISEC_IN_HOUR, strategy.getReloadTimeMSec());
    }

    @Test
    public void testDayReloadTime() throws Exception {
        StrategyI strategy = StrategyFactory.build(FilterRange.DAY, loadData);
        assertEquals(StrategyByDay.MILLISEC_IN_DAY, strategy.getReloadTimeMSec());
    }

    @Test
    public void testDefault() throws Exception {
        StrategyI strategy = StrategyFactory.build(null, loadData);
        assertEquals(StrategyBy10Min.MILLISEC_IN_10MIN, strategy.getReloadTimeMSec());
    }
}