package com.pb.dashboard.monitoring.errors.all.filter;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 05.01.15_14:03
 */
public interface FilterControllerI {

    void apply(TopItem topItem, Calendar calendar);

    FilterView getView();

    FilterModelI getModel();
}
