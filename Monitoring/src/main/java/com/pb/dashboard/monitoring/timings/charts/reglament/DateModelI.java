package com.pb.dashboard.monitoring.timings.charts.reglament;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.joda.time.LocalDate;

/**
 * Created by vlad
 * Date: 09.04.15_9:31
 */
public interface DateModelI {

    FilterRange getFilterRange();

    LocalDate getDate();

    LocalDate getFromDate();

    LocalDate getToDate();

    boolean isReglament();

}
