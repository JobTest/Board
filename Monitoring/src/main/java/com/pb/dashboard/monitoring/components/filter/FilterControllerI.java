package com.pb.dashboard.monitoring.components.filter;

import org.joda.time.LocalDate;

/**
 * Created by vlad
 * Date: 08.12.14_14:12
 */
public interface FilterControllerI {

    public FilterView getView();

    public void change(FilterRange filterRange, LocalDate date, LocalDate from, LocalDate to, Boolean reglament);
}
