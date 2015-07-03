package com.pb.dashboard.monitoring.timings.datamanager;

import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetric;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetricI;
import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ReglamentFilterTest {

    private List<MetricItem> expected;
    private Map<InterfaceMetricI, List<MetricItem>> metricListMap;

    @Before
    public void testUp() throws Exception {
        metricListMap = new LinkedHashMap<>();
        metricListMap.put(new InterfaceMetric(), getMetrics(0));

        expected = getMetrics(ReglamentFilter.REGLAMENT_HOUR);
    }

    private List<MetricItem> getMetrics(int hourStart) {
        List<MetricItem> items = new ArrayList<MetricItem>();
        LocalDateTime firstDate = new LocalDateTime(2014, 10, 4, hourStart, 0);
        LocalDateTime secondDate = new LocalDateTime(2014, 10, 4, 23, 59);
        while (firstDate.isBefore(secondDate)) {
            items.add(new MetricItem(new LocalDateTime(firstDate)));
            firstDate = firstDate.plusMinutes(10);
        }
        return items;
    }

    @Test
    public void testFilterRange10Min() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(metricListMap, FilterRange.R10MIN, false);
        for (List<MetricItem> items : actual.values()) {
            assertEquals(expected.size(), items.size());
            int i = 0;
            for (MetricItem item : items) {
                assertEquals(expected.get(i), item);
                i++;
            }
        }
    }

    @Test
    public void testFilterRangeHour() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(metricListMap, FilterRange.HOUR, false);
        for (List<MetricItem> items : actual.values()) {
            int i = 0;
            for (MetricItem item : items) {
                assertEquals(expected.get(i), item);
                i++;
            }
        }
    }

    @Test
    public void testFilterRangeDay() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(metricListMap, FilterRange.DAY, false);
        assertEquals(metricListMap, actual);
    }

    @Test
    public void testFilterTrue() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(metricListMap, FilterRange.R10MIN, true);
        assertEquals(metricListMap, actual);
    }

    @Test
    public void testFilterRangeNull() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(metricListMap, null, false);
        assertEquals(metricListMap, actual);
    }

    @Test
    public void testMapNull() throws Exception {
        Map<InterfaceMetricI, List<MetricItem>> actual = ReglamentFilter.filter(null, null, false);
        assertNull(actual);
    }
}