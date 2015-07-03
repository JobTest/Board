package com.pb.dashboard.monitoring.timings.charts.reglament;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.XAxis;
import org.joda.time.*;

/**
 * Created by vlad
 * Date: 09.04.15_9:33
 */
public class ChartPeriodManager implements ChartPeriodManagerI {

    public static final int TIME_10_MIN = 1000 * 60 * 10;
    public static final int TIME_1_HOUR = TIME_10_MIN * 6;
    public static final int TIME_1_DAY = TIME_1_HOUR * 24;

    public static final int REGLAMENT_HOUR = 3;

    public void setExtremes(Chart chart, DateModelI model) {
        XAxis xAxis = chart.getConfiguration().getxAxis();
        LocalDateTime min = DateTime.now().toLocalDateTime();
        LocalDateTime max = DateTime.now().toLocalDateTime();
        LocalDateTime now = DateTime.now().toLocalDateTime();

        switch (model.getFilterRange()) {
            case R10MIN:
            case HOUR:
                min = convertInStartDay(model.getDate());
                if (!model.isReglament()) {
                    min = min.plusHours(REGLAMENT_HOUR);
                }
                max = convertInStartDay(model.getDate().plusDays(1));
                break;
            case DAY:
                min = convertInStartDay(model.getFromDate());
                max = convertInStartDay(model.getToDate());
        }
        max = (max.isBefore(now)) ? max : now;
        if (model.getFilterRange() == FilterRange.DAY) {
            max = max.withTime(0, 0, 0, 0);
        }
        xAxis.setMin(min.toDate());
        xAxis.setMax(max.toDate());
    }

    private LocalDateTime convertInStartDay(LocalDate date) {
        LocalTime time = new LocalTime(0, 0);
        return date.toLocalDateTime(time);
    }

    public void setTickInterval(Chart chart, DateModelI model) {
        XAxis xAxis = chart.getConfiguration().getxAxis();
        switch (model.getFilterRange()) {
            case R10MIN:
                xAxis.setTickInterval(TIME_10_MIN);
                break;
            case HOUR:
                xAxis.setTickInterval(TIME_1_HOUR);
                break;
            case DAY:
                xAxis.setTickInterval(TIME_1_DAY);
        }
    }
}