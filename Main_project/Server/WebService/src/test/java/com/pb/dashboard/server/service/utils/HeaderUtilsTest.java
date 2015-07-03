package com.pb.dashboard.server.service.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class HeaderUtilsTest {


    @Test
    public void testDaysLess() {
        LocalDate date = new LocalDate(2014, 10, 8);
        String actual = HeaderUtils.getDateDayGMT(date, 10);

        LocalDateTime res = new LocalDateTime(2014, 10, 10, 0, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testDaysMore() {
        LocalDate date = new LocalDate(2014, 10, 16);
        String actual = HeaderUtils.getDateDayGMT(date, 10);

        LocalDateTime res = new LocalDateTime(2014, 10, 20,0,0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testDaysEqual() {
        LocalDate date = new LocalDate(2014, 10, 25);
        String actual = HeaderUtils.getDateDayGMT(date, 5);

        LocalDateTime res = new LocalDateTime(2014, 10, 30,0,0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testDaysEndMonth() {
        LocalDate date = new LocalDate(2014, 5, 31);
        String actual = HeaderUtils.getDateDayGMT(date, 5);

        LocalDateTime res = new LocalDateTime(2014, 6, 1,0,0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testHourLess() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 3, 0);
        String actual = HeaderUtils.getDateHourGMT(time, 5);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 5, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testHourMore() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 14, 0);
        String actual = HeaderUtils.getDateHourGMT(time, 5);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 15, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testHourEqual() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 15, 0);
        String actual = HeaderUtils.getDateHourGMT(time, 5);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 20, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testHourEndDay() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 23, 12, 13, 1);
        String actual = HeaderUtils.getDateHourGMT(time, 10);

        LocalDateTime res = new LocalDateTime(2014, 5, 21, 0, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testEveryHour() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 10, 0);
        String actual = HeaderUtils.getDateHourGMT(time, 1);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 11, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testMinuteLess() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 3, 3);
        String actual = HeaderUtils.getDateMinuteGMT(time, 10);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 3, 10);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testMinuteMore() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 14, 20);
        String actual = HeaderUtils.getDateMinuteGMT(time, 15);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 14, 30);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testMinuteEqual() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 15, 30, 13, 15);
        String actual = HeaderUtils.getDateMinuteGMT(time, 5);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 15, 35);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }

    @Test
    public void testMinuteEndHour() throws Exception {
        LocalDateTime time = new LocalDateTime(2014, 5, 20, 22, 59, 40, 40);
        String actual = HeaderUtils.getDateMinuteGMT(time, 10);

        LocalDateTime res = new LocalDateTime(2014, 5, 20, 23, 0);
        assertEquals(res.toString(HeaderUtils.DATE_PATTERN_GMT, Locale.US), actual);
    }
}