package com.pb.dashboard.monitoring.errors.all.filter;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 05.01.15_14:04
 */
public class FilterModel implements FilterModelI {

    private Observers<Observer, FilterModelI> observers;

    private TopItem topItem;
    private Calendar date;

    public FilterModel() {
        this.observers = new Observers<>();
    }

    @Override
    public void setTopItem(TopItem topItem) {
        this.topItem = topItem;
    }

    @Override
    public void setDate(Calendar calendar) {
        this.date = calendar;
    }

    @Override
    public void update() {
        observers.notifyModified(this);
    }

    @Override
    public void addObserver(Observer<FilterModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<FilterModelI> observer) {
        observers.remove(observer);
    }

    public Calendar getDate() {
        return date;
    }

    public TopItem getTopItem() {
        return topItem;
    }
}
