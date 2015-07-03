package com.pb.dashboard.external.monitor.chart;

import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.pb.dashboard.external.monitor.logic.DataQueue;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChartDataWrapper {

    //TODO Error. NullPointer. Нет проверок
    private Map<Integer, ChartData[]> data = new HashMap<Integer, ChartData[]>();
    private DataQueue hourlyQueue;
    private DataQueue dailyQueue;
    private boolean atFirst = true;
    private boolean atFirst1 = true;
    private int lastInQueue = 15;

    public boolean contains(FilterRange range,DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        return data.containsKey(getDataKey(range, bpInterface, datesByDay, interfaceKey));
    }

    public ChartData getPaymentsBusinessErrors(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        //TODO error
        return data.get(getDataKey(range, bpInterface, datesByDay, interfaceKey))[0];
    }

    public ChartData getPaymentsSystemErrors(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        return data.get(getDataKey(range, bpInterface, datesByDay, interfaceKey))[1];
    }

    public ChartData getTemplBusinessErrors(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        return data.get(getDataKey(range, bpInterface, datesByDay, interfaceKey))[0];
    }

    public ChartData getTemplSystemErrors(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        return data.get(getDataKey(range, bpInterface, datesByDay, interfaceKey))[1];
    }

    public void addChartData(FilterRange range, ChartData[] chartData, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        checkDailyOrHourlyRange(range, bpInterface, datesByDay, interfaceKey);
        data.put(getDataKey(range, bpInterface, datesByDay, interfaceKey), chartData);

    }

    private void checkDailyOrHourlyRange(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        if (range.equals(FilterRange.HOUR)) {
            if (atFirst) {
                hourlyQueue = new DataQueue(lastInQueue);
                hourlyQueue.setBuffered(getDataKey(range, bpInterface, datesByDay, interfaceKey));
                atFirst = false;
            }
            if (!hourlyQueue.isFilled()){
                hourlyQueue.addNewToHourlyQueue();
                hourlyQueue.setBuffered(getDataKey(range, bpInterface, datesByDay, interfaceKey));
            }
            else{
                reWright(range, bpInterface, datesByDay, interfaceKey, hourlyQueue);
            }
        }
        if (range.equals(FilterRange.DAY)) {
            if (atFirst1) {
                dailyQueue = new DataQueue(lastInQueue);
                dailyQueue.setBuffered(getDataKey(range, bpInterface, datesByDay, interfaceKey));
                atFirst1 = false;
            }
            if (!dailyQueue.isFilled()){
                dailyQueue.addNewToHourlyQueue();
                dailyQueue.setBuffered(getDataKey(range, bpInterface, datesByDay, interfaceKey));
            }
            else{
                reWright(range, bpInterface, datesByDay, interfaceKey, dailyQueue);
            }
        }
    }

    private void reWright(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey, DataQueue dataQueue) {
        data.remove(dataQueue.getToRemove());
        dataQueue.updateAfterRemove();
        dataQueue.setBuffered(getDataKey(range, bpInterface, datesByDay, interfaceKey));
        dataQueue.addNewToHourlyQueue();
    }

    private Integer getDataKey(FilterRange range, DInterfaceI bpInterface, Date[] datesByDay, String interfaceKey) {
        Collection<Window> windows = UI.getCurrent().getWindows();
        for (Window window : windows){
            window.close();
        }
        int dataKey=0;
        dataKey+=range.hashCode();
        dataKey+=bpInterface.hashCode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < datesByDay.length; i++) {
            dataKey += sdf.format(datesByDay[i]).toString().hashCode();//для автообновления распарсить дату вытащить часы и минуты и создавать дату для 5 минутного интервала ну и пару тройку условий
        }
        dataKey+=interfaceKey.hashCode();
        return dataKey;
    }
}
