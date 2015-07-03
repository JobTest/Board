package com.pb.dashboard.monitoring.timings.tabsheet;

import com.pb.dashboard.monitoring.timings.transferlink.LinkManagerI;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ChartDetailTest {

    private ChartDetail detail;
    private static DataSeriesItem[] items;

    @BeforeClass
    public static void up() {
        List<DataSeriesItem> list = new ArrayList<>();
        LocalDateTime dateTime = new LocalDateTime(2015, 3, 22, 0, 0);
        for (int i = 0; i < 144; i++) {
            list.add(new DataSeriesItem(dateTime.toDate(), 0));
            dateTime = dateTime.plusMinutes(10);
        }
        items = list.toArray(new DataSeriesItem[list.size()]);
    }

    @Before
    public void setUp() {
        LinkManagerI linkManager = mock(LinkManagerI.class);
        detail = new ChartDetail(linkManager);
    }

    @Test
    public void testSelectedCenter() throws Exception {
        DataSeries dataSeries = new DataSeries(items);
        int index = items.length / 2;
        DataSeriesItem seriesItem = items[index];
        String category = String.valueOf(seriesItem.getX());

        detail.updatePeriod(dataSeries, category);

        Number fromX = items[index].getX();
        LocalDateTime fromDate = new LocalDateTime(fromX, DateTimeZone.UTC);
        LocalDateTime toDate = fromDate.plusMinutes(10);
        assertEquals(fromDate, detail.getFromDateTime());
        assertEquals(toDate, detail.getToDateTime());
    }

    @Test
    public void testSelectedLastItem() throws Exception {
        DataSeries dataSeries = new DataSeries(items);
        int index = items.length - 1;
        DataSeriesItem seriesItem = items[index];
        String category = String.valueOf(seriesItem.getX());

        detail.updatePeriod(dataSeries, category);

        Number fromX = items[index].getX();
        LocalDateTime fromDate = new LocalDateTime(fromX, DateTimeZone.UTC);
        LocalDateTime toDate = fromDate.plusMinutes(10);
        assertEquals(fromDate, detail.getFromDateTime());
        assertEquals(toDate, detail.getToDateTime());
    }

    @Test
    public void testSelectedFirstItem() throws Exception {
        DataSeries dataSeries = new DataSeries(items);
        int index = 0;
        DataSeriesItem seriesItem = items[index];
        String category = String.valueOf(seriesItem.getX());

        detail.updatePeriod(dataSeries, category);

        Number fromX = items[index].getX();
        LocalDateTime fromDate = new LocalDateTime(fromX, DateTimeZone.UTC);
        LocalDateTime toDate = fromDate.plusMinutes(10);
        assertEquals(fromDate, detail.getFromDateTime());
        assertEquals(toDate, detail.getToDateTime());
    }

    @Test
    public void testListWithOneElement() throws Exception {
        DataSeries series = new DataSeries(items[0]);
        int index = 0;
        DataSeriesItem item = items[index];
        String category = String.valueOf(item.getX());

        LocalDate fromDefault = new LocalDate(item.getX());
        LocalDate toDefault = fromDefault.plusDays(1);
        detail.setDefault(fromDefault, toDefault);

        detail.updatePeriod(series, category);

        Number fromX = item.getX();
        LocalDateTime fromDate = new LocalDateTime(fromX, DateTimeZone.UTC);
        LocalDateTime toDate = fromDate.plusDays(1);
        assertTrue(fromDate.equals(detail.getFromDateTime()));
        assertTrue(toDate.equals(detail.getToDateTime()));
    }
}