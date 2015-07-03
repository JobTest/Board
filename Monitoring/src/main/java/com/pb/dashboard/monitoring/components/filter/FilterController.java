package com.pb.dashboard.monitoring.components.filter;

import com.vaadin.ui.Notification;
import org.joda.time.LocalDate;

/**
 * Created by vlad
 * Date: 08.12.14_14:12
 */
public class FilterController implements FilterControllerI {

    private FilterModelI model;
    private FilterView view;

    public FilterController(FilterModelI model) {
        this.model = model;
        view = new FilterView(this, model);
    }

    @Override
    public FilterView getView() {
        return view;
    }

    @Override
    public void change(FilterRange filterRange, LocalDate date, LocalDate from, LocalDate to, Boolean reglament) {
        if (filterRange == FilterRange.DAY) {
            changeByDay(filterRange, from, to, reglament);
        } else {
            changeByHourAnd10Min(filterRange, date, reglament);
        }
    }

    private void changeByDay(FilterRange filterRange, LocalDate from, LocalDate to, Boolean reglament) {
        if (!isValidDate(from, to)) {
            Notification.show("Некорректный период!");
            return;
        }
        model.setFilterRange(filterRange);
        model.setDateFrom(from);
        model.setDateTo(to);
        model.setReglament(reglament);
        model.notifyAllObservers();
    }

    private void changeByHourAnd10Min(FilterRange filterRange, LocalDate date, Boolean reglament) {
        if (!isValidDate(date)) {
            Notification.show("Некорректная дата!");
            return;
        }
        model.setFilterRange(filterRange);
        model.setDate(date);
        model.setReglament(reglament);
        model.notifyAllObservers();
    }

    private boolean isValidDate(LocalDate first, LocalDate second) {
        return isValidDate(second) && (second.isAfter(first) || first.equals(second));
    }

    private boolean isValidDate(LocalDate date) {
        LocalDate now = new LocalDate();
        return now.isAfter(date) || now.equals(date);
    }
}