package com.pb.dashboard.monitoring.timings.filter;

import com.pb.dashboard.monitoring.components.filter.FilterModel;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FilterModelBuilderTest {

    private Map<String, String> map;

    @Before
    public void setUp() throws Exception {
        map = new LinkedHashMap<>();
    }

    @Test
    public void testFilterRangeEmpty() throws Exception {
        FilterModel model = FilterModelBuilder.build(map);
        assertEquals(FilterRange.R10MIN, model.getFilterRange());
    }

    @Test
    public void testFilterRangeHour() throws Exception {
        FilterRange expected = FilterRange.HOUR;
        addParam(MonitoringParam.RANGE, expected.getPkey());
        FilterModel model = FilterModelBuilder.build(map);
        assertEquals(expected, model.getFilterRange());
    }

    @Test
    public void testFilterRangeIsNotValid() throws Exception {
        addParam(MonitoringParam.RANGE, "not_valid");
        FilterModel model = FilterModelBuilder.build(map);
        assertEquals(FilterRange.R10MIN, model.getFilterRange());
    }

    @Test
    public void testDateEmpty() throws Exception {
        FilterModel model = FilterModelBuilder.build(map);
        assertNotNull(model.getDate());
    }

    @Test
    public void testDateIsNotValid() throws Exception {
        FilterRange range = FilterRange.R10MIN;
        addParam(MonitoringParam.RANGE, range.getPkey());
        addParam(MonitoringParam.DATE, "not_valid_date");
        FilterModel model = FilterModelBuilder.build(map);
        assertNotNull(model.getDate());
    }

    @Test
    public void testDate10Min() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(FilterModelBuilder.DATE_PATTERN);
        LocalDate calendar = createCalendar(10);
        FilterRange range = FilterRange.R10MIN;
        addParam(MonitoringParam.RANGE, range.getPkey());
        addParam(MonitoringParam.DATE, sdf.format(calendar.toDate()));
        FilterModel model = FilterModelBuilder.build(map);
        assertCalendarDays(calendar, model.getDate());
    }

    @Test
    public void testDateDaysEmpty() throws Exception {
        FilterRange range = FilterRange.DAY;
        addParam(MonitoringParam.RANGE, range.getPkey());
        FilterModel model = FilterModelBuilder.build(map);
        LocalDate from = model.getDateFrom();
        from = from.plusDays(FilterModelBuilder.DIFFERENT_BETWEEN_DATE);
        LocalDate to = model.getDateTo();
        assertCalendarDays(from, to);
    }

    @Test
    public void testDateDays() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(FilterModelBuilder.DATE_PATTERN);
        LocalDate from = createCalendar(10);
        LocalDate to = createCalendar(20);
        FilterRange range = FilterRange.R10MIN;
        addParam(MonitoringParam.RANGE, range.getPkey());
        addParam(MonitoringParam.DATE_FROM, sdf.format(from.toDate()));
        addParam(MonitoringParam.DATE_TO, sdf.format(to.toDate()));
        FilterModel model = FilterModelBuilder.build(map);
        assertCalendarDays(from, model.getDateFrom());
        assertCalendarDays(to, model.getDateTo());
    }

    @Test
    public void testReglamentTrue() throws Exception {
        boolean reglament = true;
        addParam(MonitoringParam.REGLAMENT, reglament);
        FilterModel model = FilterModelBuilder.build(map);
        assertEquals(reglament, model.isReglament());
    }

    @Test
    public void testReglamentFalse() throws Exception {
        boolean reglament = false;
        addParam(MonitoringParam.REGLAMENT, reglament);
        FilterModel model = FilterModelBuilder.build(map);
        assertEquals(reglament, model.isReglament());
    }

    private LocalDate createCalendar(int addDay) {
        return new LocalDate(2013, 10, 23).plusDays(addDay);
    }

    private void assertCalendarDays(LocalDate from, LocalDate to) {
        assertEquals(from.getYear(), to.getYear());
        assertEquals(from.getMonthOfYear(), to.getMonthOfYear());
        assertEquals(from.getDayOfMonth(), to.getDayOfMonth());
    }

    private void addParam(Object key, Object value) {
        map.put(String.valueOf(key), String.valueOf(value));
    }
}