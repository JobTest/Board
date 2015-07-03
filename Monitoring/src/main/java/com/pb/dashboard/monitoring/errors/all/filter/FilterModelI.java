package com.pb.dashboard.monitoring.errors.all.filter;

import com.pb.dashboard.monitoring.components.observers.Observer;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 05.01.15_14:03
 */
public interface FilterModelI {

    void setTopItem(TopItem topItem);

    void setDate(Calendar calendar);

    Calendar getDate();

    TopItem getTopItem();

    void update();

    void addObserver(Observer<FilterModelI> observer);

    void removeObserver(Observer<FilterModelI> observer);

}
