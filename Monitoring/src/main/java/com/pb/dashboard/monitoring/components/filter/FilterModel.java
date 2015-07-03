package com.pb.dashboard.monitoring.components.filter;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Objects;

public class FilterModel implements FilterModelI, Serializable, Cloneable {

    private static final long serialVersionUID = 4144482340550117309L;
    private Observers<Observer, FilterModelI> observers;

    private FilterRange filterRange;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean reglament;

    public FilterModel() {
        observers = new Observers<Observer, FilterModelI>();
        filterRange = FilterRange.R10MIN;
        date = new LocalDate();
        dateFrom = new LocalDate();
        dateTo = new LocalDate();
    }

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isReglament() {
        return reglament;
    }

    public void setFilterRange(FilterRange filterRange) {
        this.filterRange = filterRange;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setReglament(boolean isReglament) {
        this.reglament = isReglament;
    }

    @Override
    public void addObserver(Observer<FilterModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<FilterModelI> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        observers.notifyModified(this);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(observers, filterRange, date, dateFrom, dateTo, reglament);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FilterModel other = (FilterModel) obj;
        return Objects.equals(this.observers, other.observers)
                && Objects.equals(this.filterRange, other.filterRange)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.dateFrom, other.dateFrom)
                && Objects.equals(this.dateTo, other.dateTo)
                && Objects.equals(this.reglament, other.reglament);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}