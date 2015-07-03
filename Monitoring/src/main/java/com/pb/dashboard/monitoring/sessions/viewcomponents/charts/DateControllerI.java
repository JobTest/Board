package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import org.joda.time.LocalDateTime;

/**
 * Created by petrik on 02.10.14.
 */
public interface DateControllerI {

    boolean isDaily();

    boolean isHourly();

    int getMinute();

    int getHour();

    LocalDateTime getDateFrom();

    LocalDateTime getDateTo();
}