package com.pb.dashboard.monitoring.timings.sla.builderchart;

import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterface;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingHp;
import com.pb.dashboard.dao.entity.vitrinametrics.SlaTimingI;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Series;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class TimingsChartTest {

    private TimingsChart timingsChart;
    private int count = 50;
    private Map<SlaInterfaceI, List<SlaTimingI>> map;
    private Map<TimingsType, Number[]> numbersMap = new LinkedHashMap<>();

    @Before
    public void setUp() throws Exception {
        timingsChart = new TimingsChart();
        initNumbers();
        initTimingsMap();
    }

    private void initNumbers() {
        int start = 0;
        for (TimingsType type : TimingsType.values()) {
            Number[] numbers = new Number[count];
            numbersMap.put(type, numbers);
            for (int i = 0; i < count; i++) {
                numbers[i] = start++;
            }
        }
    }

    private void initTimingsMap() {
        map = new LinkedHashMap<>();

        SlaInterface slaInterface = new SlaInterface();
        slaInterface.setPkey(1);
        slaInterface.setName("name");
        slaInterface.setDescription("description");

        DateTime dateTime = new DateTime(2014, 1, 1, 0, 0);
        List<SlaTimingI> timings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SlaTimingHp timing = new SlaTimingHp();
            timing.setDateTime(dateTime);
            dateTime = dateTime.plusMinutes(10);
            timing.setPkeySlaInterface(slaInterface.getPkey());

            timing.setTimingAvg((Integer) numbersMap.get(TimingsType.AVG)[i]);
            timing.setTiming90((Integer) numbersMap.get(TimingsType.PERCENT_90)[i]);
            timing.setTiming95((Integer) numbersMap.get(TimingsType.PERCENT_95)[i]);
            timing.setTiming99((Integer) numbersMap.get(TimingsType.PERCENT_99)[i]);
            timings.add(timing);
        }
        map.put(slaInterface, timings);
    }

    @Test
    public void testRight() throws Exception {
        timingsChart.setTimingsBySlaInterface(map);
        timingsChart.change(map.keySet().iterator().next());
        SlaChart chart = timingsChart.getChart();
        List<Series> series = chart.getConfiguration().getSeries();

        Map<TimingsType, DataSeries> seriesMap = new LinkedHashMap<>();
        for (Series sery : series) {
            DataSeries dataSeries = (DataSeries) sery;
            String name = dataSeries.getName();
            TimingsType timingsType = TimingsType.get(name);
            seriesMap.put(timingsType, dataSeries);
        }

        for (Map.Entry<TimingsType, DataSeries> entry : seriesMap.entrySet()) {
            TimingsType key = entry.getKey();
            DataSeries value = entry.getValue();
            Number[] numbers = numbersMap.get(key);

            List<DataSeriesItem> data = value.getData();
            Number[] convert = convert(data);

            assertArrayEquals(numbers, convert);
        }
    }

    private Number[] convert(List<DataSeriesItem> items) {
        Number[] numbers = new Number[items.size()];
        for (int i = 0; i < items.size(); i++) {
            numbers[i] = items.get(i).getY();
        }
        return numbers;
    }

    @Test
    public void testSetSlaInterfaceWrote() throws Exception {
        timingsChart.setTimingsBySlaInterface(map);
        timingsChart.change(null);
        SlaChart chart = timingsChart.getChart();
        List<Series> series = chart.getConfiguration().getSeries();
        for (Series sery : series) {
            DataSeries dataSeries = (DataSeries) sery;
            assertTrue(dataSeries.getData().isEmpty());
        }
    }
}