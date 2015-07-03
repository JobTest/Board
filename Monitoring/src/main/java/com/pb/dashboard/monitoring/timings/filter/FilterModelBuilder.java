package com.pb.dashboard.monitoring.timings.filter;

import com.pb.dashboard.monitoring.components.filter.FilterModel;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.Map;

/**
 * Created by vlad
 * Date: 20.11.14_14:59
 */
public final class FilterModelBuilder {

    public static final int DIFFERENT_BETWEEN_DATE = 10;
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final Logger log = Logger.getLogger(FilterModelBuilder.class);
    private static final FilterRange DEFAULT_FILTER_RANGE = FilterRange.R10MIN;
    private FilterModel model;

    public static FilterModel build(Map<String, String> map) {
        return new FilterModelBuilder(map).getModel();
    }

    private FilterModelBuilder(Map<String, String> map) {
        this.model = new FilterModel();
        initRange(getValue(map, MonitoringParam.RANGE));
        String date = getValue(map, MonitoringParam.DATE);
        String from = getValue(map, MonitoringParam.DATE_FROM);
        String to = getValue(map, MonitoringParam.DATE_TO);
        initDates(date, from, to);
        initReglament(getValue(map, MonitoringParam.REGLAMENT));
    }

    private void initRange(String value) {
        if (value == null) {
            model.setFilterRange(DEFAULT_FILTER_RANGE);
            return;
        }
        try {
            FilterRange range = FilterRange.pkeyToFilterRange(value);
            model.setFilterRange(range);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            model.setFilterRange(DEFAULT_FILTER_RANGE);
        }
    }

    private void initDates(String dateValue, String fromValue, String toValue) {
        LocalDate first = new LocalDate().plusDays(-DIFFERENT_BETWEEN_DATE);
        LocalDate date = getDateTime(dateValue, new LocalDate());
        first = getDateTime(fromValue, first);
        LocalDate second = getDateTime(toValue, new LocalDate());
        model.setDate(date);
        model.setDateFrom(first);
        model.setDateTo(second);
    }

    private LocalDate getDateTime(String dateStr, LocalDate dateTime) {
        try {
            if (dateStr != null) {
                return LocalDate.parse(dateStr, DateTimeFormat.forPattern(DATE_PATTERN));
            }
        } catch (Exception e) {
            log.error("Invalid format[" + DATE_PATTERN + "] date. Res=[" + dateStr + "]", e);
        }
        return dateTime;
    }

    private void initReglament(String value) {
        boolean reglament = Boolean.parseBoolean(value);
        model.setReglament(reglament);
    }

    private String getValue(Map<String, String> map, MonitoringParam param) {
        if (map == null || map.isEmpty() || param == null) {
            return null;
        }
        return map.get(param.getName());
    }

    public FilterModel getModel() {
        return model;
    }
}