package com.pb.dashboard.monitoring.errors.all.filter;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.PopupDateField;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilterViewTest {

    private static final int COMBOBOX = 1;
    private static final int DATE_FIELD = 2;
    private FilterView view;
    private FilterControllerI controller;
    private FilterModelI model;

    @Before
    public void setUp() throws Exception {
        model = FilterModelBuilder.build();
        controller = mock(FilterController.class);
        when(controller.getModel()).thenReturn(model);
        view = new FilterView(controller, model);
    }

    @Test
    public void testInitTop() throws Exception {
        assertEquals(model.getTopItem(), getTopItem());
    }

    @Test
    public void testInitDate() throws Exception {
        assertTrue(equalsDateByYearMonthDay(model.getDate(), getDate()));
    }

    @Test
    public void testChangeModel() throws Exception {
        model.setDate(new GregorianCalendar(2011, 10, 1));
        model.setTopItem(TopItem.T20);
        model.update();
        testInitDate();
        testInitTop();
    }

    private boolean equalsDateByYearMonthDay(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
            return false;
        }
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
            return false;
        }
        return c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    private TopItem getTopItem() {
        ComboBox comboBox = getComboBox();
        return (TopItem) comboBox.getValue();
    }

    private ComboBox getComboBox() {
        return (ComboBox) view.getComponent(COMBOBOX);
    }

    private Calendar getDate() {
        PopupDateField dateField = getDateField();
        Date date = dateField.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        return calendar;
    }

    private PopupDateField getDateField() {
        return (PopupDateField) view.getComponent(DATE_FIELD);
    }
}