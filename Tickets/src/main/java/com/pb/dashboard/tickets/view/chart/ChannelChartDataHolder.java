package com.pb.dashboard.tickets.view.chart;

import com.pb.dashboard.tickets.db.TicketsDBManager;
import com.pb.dashboard.tickets.entype.ChannelOutput;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Series;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelChartDataHolder {

    private List<Series> series;

    public ChannelChartDataHolder() {
        setEmpty();
    }

    public ChannelChartDataHolder(Map<ChannelOutput, Integer> dbResult) {
        setData(dbResult);
    }

    public List<Series> getSeries() {
        return series;
    }

    private void setData(Map<ChannelOutput, Integer> dbResult) {
        series = new ArrayList<Series>();
        for (Map.Entry<ChannelOutput, Integer> entry : dbResult.entrySet()) {
            series.add(new ListSeries(entry.getKey().getName(), entry.getValue()));
        }
    }

    private void setEmpty() {
        Map<ChannelOutput, Integer> map = new HashMap<ChannelOutput, Integer>();
        Map<Integer, ChannelOutput> channels = TicketsDBManager.getInstance().getChannels();
        for (ChannelOutput channel : channels.values()) {
            if (channel == null) continue;
            map.put(channel, 0);
        }
        setData(map);
    }
}
