package com.pb.dashboard.monitoring.errors.all.filter;

import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 15.09.14
 */
public class FilterController implements FilterControllerI {

    private static final Logger log = Logger.getLogger(FilterController.class);

    private FilterModelI model;
    private FilterView view;

    public FilterController(FilterModelI model) {
        this.model = model;
        init();
    }

    private void init() {
        view = new FilterView(this, model);
    }

    public FilterView getView() {
        return view;
    }

    @Override
    public FilterModelI getModel() {
        return model;
    }

    @Override
    public void apply(TopItem topItem, Calendar calendar) {
        if (validateDate(calendar)) {
            model.setTopItem(topItem);
            model.setDate(calendar);
            model.update();
        } else {
            log.warn("", new IllegalArgumentException("date not valid"));
        }
    }

    private boolean validateDate(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        return now.after(calendar);
    }
}