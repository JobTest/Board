package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import org.joda.time.LocalDateTime;

/**
 * Created by petrik on 02.10.14.
 */
public class DateController implements DateControllerI {

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public DateController(LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public boolean isDaily() {
        return !(dateFrom.toLocalDate().equals(dateTo.toLocalDate()));
    }

    @Override
    public boolean isHourly() {
        if (!isDaily()) {
            int hourFrom = dateFrom.getHourOfDay();
            int hourTo = dateTo.getHourOfDay();
            return hourFrom != hourTo;
        }
        return false;
    }

    @Override
    public int getMinute() {
        return dateFrom.getMinuteOfHour();
    }

    @Override
    public int getHour() {
        return dateFrom.getHourOfDay();
    }


    @Override
    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }
}