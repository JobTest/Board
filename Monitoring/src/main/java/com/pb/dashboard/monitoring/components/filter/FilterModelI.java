package com.pb.dashboard.monitoring.components.filter;

import com.pb.dashboard.monitoring.components.observers.Observer;
import org.joda.time.LocalDate;

/**
 * Created by vlad
 * Date: 08.12.14_14:17
 */
public interface FilterModelI extends Cloneable {

    public FilterRange getFilterRange();

    public LocalDate getDateFrom();

    public LocalDate getDateTo();

    public LocalDate getDate();

    public boolean isReglament();

    public void setFilterRange(FilterRange filterRange);

    public void setDate(LocalDate date);

    public void setDateFrom(LocalDate dateFirst);

    public void setDateTo(LocalDate dateSecond);

    public void setReglament(boolean isRerlament);

    public void addObserver(Observer<FilterModelI> observer);

    public void removeObserver(Observer<FilterModelI> observer);

    public void notifyAllObservers();
}
