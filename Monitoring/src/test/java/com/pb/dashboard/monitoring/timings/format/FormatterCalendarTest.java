package com.pb.dashboard.monitoring.timings.format;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatterCalendarTest {

    private LocalDateTime date = new LocalDateTime(2014, 11, 10, 16, 52, 10);

    @Test
    public void testFormatBy10Min() throws Exception {
        String expected = "16" + FormatterCalendar.TIME_SEPARATOR + "52";
        String actual = FormatterCalendar.format(FilterRange.R10MIN, date);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatByHour() throws Exception {
        String expected = "16" + FormatterCalendar.TIME_SEPARATOR + "52";
        String actual = FormatterCalendar.format(FilterRange.HOUR, date);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatByDay() throws Exception {
        String s = FormatterCalendar.DATE_SEPARATOR;
        String expected = "2014" + s + "11" + s + "10";
        String actual = FormatterCalendar.format(FilterRange.DAY, date);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatBy10MinFromStr() throws Exception {
        LocalDateTime dateTime = FormatterCalendar.format(FilterRange.R10MIN, "10:23");
        int hour = dateTime.getHourOfDay();
        int minute = dateTime.getMinuteOfHour();
        assertEquals(10, hour);
        assertEquals(23, minute);
    }

    @Test
    public void testFormatByHourFromStr() throws Exception {
        LocalDateTime cal = FormatterCalendar.format(FilterRange.HOUR, "10:23");
        int hour = cal.getHourOfDay();
        int minute = cal.getMinuteOfHour();
        assertEquals(10, hour);
        assertEquals(23, minute);
    }

    @Test
    public void testFormatByDayFromStr() throws Exception {
        LocalDateTime cal = FormatterCalendar.format(FilterRange.DAY, "2013-03-11");
        int year = cal.getYear();
        int month = cal.getMonthOfYear();
        int day = cal.getDayOfMonth();
        assertEquals(2013, year);
        assertEquals(3, month);
        assertEquals(11, day);
    }

    @Test
    public void testFormatByNegative() throws Exception {
        FormatterCalendar.format(FilterRange.R10MIN, "22-166-0");
    }
}