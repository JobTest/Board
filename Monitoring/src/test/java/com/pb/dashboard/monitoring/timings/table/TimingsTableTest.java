package com.pb.dashboard.monitoring.timings.table;

import com.pb.dashboard.dao.entity.vitrinametrics.db.MetricItem;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.timings.TimingsType;
import com.pb.dashboard.monitoring.timings.format.FormatterCalendar;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import org.joda.time.LocalDateTime;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TimingsTableTest {

    private TimingsTable table;
    private static List<MetricItem> items;
    private static LocalDateTime date;

    @BeforeClass
    public static void setUpClass() throws Exception {
        date = new LocalDateTime(2014, 11, 10, 16, 52, 10);
        items = new ArrayList<>();
        items.add(new MetricItem(date, 3, 4, 6, 7, 9, 9, 1, 3));
        items.add(new MetricItem(date.plusMinutes(10), 1, 3, 10, 6, 2, 8, 3, 6));
        items.add(new MetricItem(date.plusMinutes(20), 1, 3, 10, 6, 2, 8, 3, 8));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        items = null;
        date = null;
    }

    @Before
    public void setUp() throws Exception {
        table = new TimingsTable();
    }

    @After
    public void tearDown() throws Exception {
        table = null;
    }

    @Test
    public void testTotal() throws Exception {
        table.setItems(items);
        assertItemAndValue(TimingsTable.PERIOD, TimingsTable.TOTAL);
        assertItemAndValue(TimingsType.MIN.getName(), "5");
        assertItemAndValue(TimingsType.MAX.getName(), "10");
        assertItemAndValue(TimingsType.AVG.getName(), "26");
        assertItemAndValue(TimingsType.P90.getName(), "19");
        assertItemAndValue(TimingsType.P95.getName(), "13");
        assertItemAndValue(TimingsType.P99.getName(), "25");
        assertItemAndValue(TimingsType.QUERIES.getName(), "7");
        assertItemAndValue(TimingsType.ERRORS.getName(), "17");
    }

    public void assertItemAndValue(String columnId, Object value) {
        String footer = table.getColumnFooter(columnId);
        assertEquals(value, footer);
    }

    @Test
    public void testCountColumns() throws Exception {
        int size = table.getColumnHeaders().length;
        assertEquals(9, size);
    }

    @Test
    public void testCountRows() throws Exception {
        table.setItems(items);
        int size = table.getItemIds().size();
        assertEquals(3, size);
    }

    @Test
    public void testRemoveAllItems() throws Exception {
        table.setItems(items);
        testCountRows();
    }

    @Test
    public void testClearTotal() throws Exception {
        table.setItems(items);
        testTotal();
    }

    @Test
    public void testFormatDateFor10Min() throws Exception {
        String expected = FormatterCalendar.format(FilterRange.R10MIN, date);
        table.setFormatterCalendar(FilterRange.R10MIN);
        table.setItems(items);
        Object firstPeriod = getFirstPeriod();
        assertEquals(expected, firstPeriod);
    }

    @Test
    public void testFormatDateForHour() throws Exception {
        String expected = FormatterCalendar.format(FilterRange.HOUR, date);
        table.setFormatterCalendar(FilterRange.R10MIN);
        table.setItems(items);
        Object firstPeriod = getFirstPeriod();
        assertEquals(expected, firstPeriod);
    }

    @Test
    public void testFormatDateForDay() throws Exception {
        String expected = FormatterCalendar.format(FilterRange.DAY, date);
        table.setFormatterCalendar(FilterRange.DAY);
        table.setItems(items);
        Object firstPeriod = getFirstPeriod();
        assertEquals(expected, firstPeriod);
    }

    private Object getFirstPeriod() {
        Object itemId = table.getCurrentPageFirstItemId();
        Item item = table.getItem(itemId);
        Property property = item.getItemProperty(TimingsTable.PERIOD);
        return property.getValue();
    }
}