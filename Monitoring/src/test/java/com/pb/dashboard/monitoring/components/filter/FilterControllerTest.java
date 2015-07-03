package com.pb.dashboard.monitoring.components.filter;

import com.vaadin.ui.Notification;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Notification.class})
public class FilterControllerTest {

    private FilterControllerI controller;
    private FilterModelI model;

    private LocalDate now = new LocalDate();

    @Before
    public void setUp() throws Exception {
        model = new FilterModel();
        model.setDate(now);
        model.setDateFrom(now);
        model.setDateTo(now);
        controller = new FilterController(model);

        PowerMockito.mockStatic(Notification.class);
    }

    @After
    public void tearDown() throws Exception {
        model = null;
        controller = null;
    }

    @Test
    public void testChangeByR10MinPositive() throws Exception {

        LocalDate c1 = createDate(2014, 10, 1);
        LocalDate c2 = createDate(2014, 12, 1);
        LocalDate c3 = createDate(2014, 11, 1);
        controller.change(FilterRange.R10MIN, c1, c2, c3, true);
        assertModel(FilterRange.R10MIN, c1, now, now, true);
    }

    @Test
    public void testChangeByR10MinNegative() throws Exception {
        FilterModelI clone = (FilterModelI) ((FilterModel) model).clone();
        LocalDate c1 = new LocalDate().plusDays(1);
        LocalDate c2 = createDate(2014, 10, 1);
        LocalDate c3 = createDate(2014, 10, 1);
        controller.change(FilterRange.R10MIN, c1, c2, c3, true);
        assertEquals(clone, model);
    }

    @Test
    public void testChangeByHourPositive() throws Exception {
        LocalDate c1 = createDate(2014, 10, 1);
        LocalDate c2 = createDate(2014, 11, 1);
        LocalDate c3 = createDate(2014, 11, 2);
        controller.change(FilterRange.HOUR, c1, c2, c3, true);
        assertModel(FilterRange.HOUR, c1, now, now, true);
    }

    @Test
    public void testChangeByHourNegative() throws Exception {
        LocalDate c1 = createDate(2015, 10, 1);
        LocalDate c2 = createDate(2014, 11, 1);
        LocalDate c3 = createDate(2014, 11, 2);
        controller.change(FilterRange.HOUR, c1, c2, c3, true);
        assertModel(FilterRange.R10MIN, now, now, now, false);
    }

    @Test
    public void testChangeByDayPositive() throws Exception {
        LocalDate c1 = createDate(2015, 10, 1);
        LocalDate c2 = createDate(2014, 11, 1);
        LocalDate c3 = createDate(2014, 11, 2);
        controller.change(FilterRange.DAY, c1, c2, c3, true);
        assertModel(FilterRange.DAY, now, c2, c3, true);
    }

    @Test
    public void testChangeByDayTwoDatesEqual() throws Exception {
        LocalDate c1 = createDate(2015, 10, 1);
        LocalDate c2 = createDate(2014, 11, 1);
        LocalDate c3 = createDate(2014, 11, 1);
        controller.change(FilterRange.DAY, c1, c2, c3, true);
        assertModel(FilterRange.DAY, now, c2, c3, true);
    }

    @Test
    public void testChangeByDayFirstDayMoreSecond() throws Exception {
        LocalDate c1 = createDate(2015, 10, 1);
        LocalDate c2 = createDate(2014, 11, 2);
        LocalDate c3 = createDate(2014, 11, 1);
        controller.change(FilterRange.DAY, c1, c2, c3, true);
        assertModel(FilterRange.R10MIN, now, now, now, false);
    }

    @Test
    public void testChangeByDayDayMoreNow() throws Exception {
        LocalDate c1 = createDate(2015, 10, 1);
        LocalDate c2 = createDate(2014, 11, 1);
        LocalDate c3 = new LocalDate().plusDays(1);
        controller.change(FilterRange.DAY, c1, c2, c3, true);
        assertModel(FilterRange.R10MIN, now, now, now, false);
    }

    private void assertModel(FilterRange filterRange, LocalDate date, LocalDate from, LocalDate to, Boolean reglament) {
        assertEquals(filterRange, model.getFilterRange());
        assertEquals(date, model.getDate());
        assertEquals(from, model.getDateFrom());
        assertEquals(to, model.getDateTo());
        assertEquals(reglament, model.isReglament());
    }

    private LocalDate createDate(int year, int month, int day) {
        return new LocalDate(year, month, day);
    }
}