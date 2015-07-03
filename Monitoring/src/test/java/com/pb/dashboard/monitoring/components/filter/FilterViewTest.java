package com.pb.dashboard.monitoring.components.filter;

import com.vaadin.ui.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FilterViewTest {

    private static final int OPTION_GROUP = 0;
    private static final int DATE_FIELD = 0;
    private static final int DATE_FIELD_FROM = 1;
    private static final int DATE_FIELD_TO = 2;
    private static final int CHECK_BOX = 0;
    private static final int BUTTON = 3;

    private static final int DATE_PANEL = 1;
    private static final int CHECK_BOX_PANEL = 2;

    private FilterView view;
    private FilterControllerI controller;
    private FilterModelI model;

    private FilterRange range = FilterRange.HOUR;
    private static LocalDate date;
    private static LocalDate from;
    private static LocalDate to;
    private boolean reglament = true;


    @BeforeClass
    public static void up() throws Exception {
        date = new LocalDate(2013, 1, 4);
        from = new LocalDate(2013, 5, 21);
        to = new LocalDate(2014, 3, 22);
    }

    @Before
    public void setUp() throws Exception {
        model = new FilterModel();
        model.setFilterRange(range);
        model.setDate(date);
        model.setDateFrom(from);
        model.setDateTo(to);
        model.setReglament(reglament);
        controller = mock(FilterController.class);
        view = new FilterView(controller, model);
    }

    @Test
    public void testRange10Min() throws Exception {
        getOptionGroup().setValue(FilterRange.R10MIN);
        assertTrue(getDateField().isVisible());
        assertTrue(getCheckBox().isVisible());
        assertFalse(getDateFieldFrom().isVisible());
        assertFalse(getDateFieldTo().isVisible());
    }

    @Test
    public void testRangeHour() throws Exception {
        getOptionGroup().setValue(FilterRange.HOUR);
        assertTrue(getDateField().isVisible());
        assertTrue(getCheckBox().isVisible());
        assertFalse(getDateFieldFrom().isVisible());
        assertFalse(getDateFieldTo().isVisible());
    }

    @Test
    public void testRangeDay() throws Exception {
        getOptionGroup().setValue(FilterRange.DAY);
        assertTrue(getDateFieldFrom().isVisible());
        assertTrue(getDateFieldTo().isVisible());
        assertFalse(getDateField().isVisible());
        assertFalse(getCheckBox().isVisible());
    }

    @Test
    public void testInitValueComponents() throws Exception {
        assertEquals(range, getOptionGroup().getValue());
        assertEquals(date.toDate(), getDateField().getValue());
        assertEquals(from.toDate(), getDateFieldFrom().getValue());
        assertEquals(to.toDate(), getDateFieldTo().getValue());
        assertEquals(reglament, getCheckBox().getValue());
    }

    @Test
    public void testCallMethodByClickButton() throws Exception {
        getButton().click();
        verify(controller).change(range, date, from, to, reglament);
    }

    private OptionGroup getOptionGroup() {
        return (OptionGroup) view.getComponent(OPTION_GROUP);
    }

    private PopupDateField getDateField() {
        return (PopupDateField) getDatePanel().getComponent(DATE_FIELD);
    }

    private PopupDateField getDateFieldFrom() {
        return (PopupDateField) getDatePanel().getComponent(DATE_FIELD_FROM);
    }

    private PopupDateField getDateFieldTo() {
        return (PopupDateField) getDatePanel().getComponent(DATE_FIELD_TO);
    }

    private HorizontalLayout getDatePanel() {
        return (HorizontalLayout) view.getComponent(DATE_PANEL);
    }

    private CheckBox getCheckBox() {
        HorizontalLayout checkBoxPanel = (HorizontalLayout) view.getComponent(CHECK_BOX_PANEL);
        return (CheckBox) checkBoxPanel.getComponent(CHECK_BOX);
    }

    private Button getButton() {
        return (Button) view.getComponent(BUTTON);
    }
}