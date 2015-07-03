package com.pb.dashboard.monitoring.errors.all.filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FilterControllerTest {

    private FilterControllerI controller;
    private FilterModelI model;

    @Before
    public void setUp() throws Exception {
        model = new FilterModel();
        controller = new FilterController(model);
    }

    @After
    public void tearDown() throws Exception {
        model = null;
        controller = null;
    }

    @Test
    public void testGetModel() throws Exception {
        FilterModelI model1 = controller.getModel();
        assertEquals(model1, model);
    }

    @Test
    public void testCalendarRight() throws Exception {
        Calendar cal = getCalendar(2014, 5, 10);
        controller.apply(TopItem.T10, cal);
        assertEquals(cal, model.getDate());
    }

    @Test
    public void testApplyDateAfterNow() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        controller.apply(TopItem.T10, calendar);
        assertNull(model.getDate());
        assertNull(model.getTopItem());
    }

    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);
        return calendar;
    }
}