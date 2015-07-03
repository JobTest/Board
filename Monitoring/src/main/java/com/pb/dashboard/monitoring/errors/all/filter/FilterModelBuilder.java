package com.pb.dashboard.monitoring.errors.all.filter;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 05.01.15_14:44
 */
public final class FilterModelBuilder {

    public static final TopItem DEFAULT_TOP = TopItem.T10;
    private FilterModelI model;

    public static FilterModelI build() {
        return new FilterModelBuilder().getModel();
    }

    private FilterModelBuilder() {
        model = new FilterModel();
        initTopItem();
        initDate();
    }

    private void initTopItem() {
        model.setTopItem(DEFAULT_TOP);
    }

    private void initDate() {
        model.setDate(Calendar.getInstance());
    }

    public FilterModelI getModel() {
        return model;
    }
}