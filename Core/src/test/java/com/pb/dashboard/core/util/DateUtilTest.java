package com.pb.dashboard.core.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void testSameYear() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 4);
        Calendar c2 = new GregorianCalendar(2014, 1, 7);
        boolean sameYear = DateUtil.isSameYear(c1, c2);
        assertTrue(sameYear);
    }

    @Test
    public void testYearDiffYear() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 4);
        Calendar c2 = new GregorianCalendar(2013, 10, 4);
        boolean sameYear = DateUtil.isSameYear(c1, c2);
        assertFalse(sameYear);
    }

    @Test
    public void testYearTwoNull() throws Exception {
        boolean sameYear = DateUtil.isSameYear(null, null);
        assertTrue(sameYear);
    }

    @Test
    public void testYearNull() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 4);
        boolean sameYear = DateUtil.isSameYear(c1, null);
        assertFalse(sameYear);
        sameYear = DateUtil.isSameYear(null, c1);
        assertFalse(sameYear);
    }

    @Test
    public void testSameMonth() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 1);
        Calendar c2 = new GregorianCalendar(2014, 10, 4);
        boolean sameMonth = DateUtil.isSameMonth(c1, c2);
        assertTrue(sameMonth);
    }

    @Test
    public void testMonthDiffYear() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 5);
        Calendar c2 = new GregorianCalendar(2013, 10, 5);
        boolean sameMonth = DateUtil.isSameMonth(c1, c2);
        assertFalse(sameMonth);
    }

    @Test
    public void testMonthDiffMonth() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 9, 10);
        Calendar c2 = new GregorianCalendar(20114, 10, 10);
        boolean sameMonth = DateUtil.isSameMonth(c1, c2);
        assertFalse(sameMonth);
    }

    @Test
    public void testSameDay() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 9, 10, 6);
        Calendar c2 = new GregorianCalendar(2014, 10, 9, 17, 9);
        boolean sameDay = DateUtil.isSameDay(c1, c2);
        assertTrue(sameDay);
    }

    @Test
    public void testDiffMonth() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 9);
        Calendar c2 = new GregorianCalendar(2014, 9, 9);
        boolean sameDay = DateUtil.isSameDay(c1, c2);
        assertFalse(sameDay);
    }

    @Test
    public void testDiffYear() throws Exception {
        Calendar c1 = new GregorianCalendar(2013, 10, 9);
        Calendar c2 = new GregorianCalendar(2014, 10, 9);
        boolean sameDay = DateUtil.isSameDay(c1, c2);
        assertFalse(sameDay);
    }

    @Test
    public void testName() throws Exception {
        Calendar c1 = new GregorianCalendar(2014, 10, 9, 10, 24);
        Calendar c2 = new GregorianCalendar(2014, 10, 9, 9, 4);
        long minute = DateUtil.diffMinute(c1, c2);
        assertEquals(80, minute);
    }
}