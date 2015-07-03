package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by petrik on 02.10.14.
 */
public class DateControllerTest {

    private DateControllerI controller;
    private LocalDateTime from = new LocalDateTime(2014, 10, 2, 9, 10);
    private LocalDateTime to = new LocalDateTime(2014, 10, 2, 9, 20);

    @Before
    public void setUp() throws Exception {
        controller = new DateController(from, to);
    }

    @Test
    public void testGetFirstMinute() throws Exception {
        int expected = 10;
        assertEquals(expected, controller.getMinute());
    }

    @Test
    public void getGetFirstHour() {
        int expected = 9;
        assertEquals(expected, controller.getHour());
    }

    @Test
    public void testIsHourly() throws Exception {
        assertFalse(controller.isHourly());
    }

    @Test
    public void testIsDaily() throws Exception {
        assertFalse(controller.isDaily());
    }
}