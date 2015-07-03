package com.pb.dashboard.monitoring.timings.format;

import com.pb.dashboard.monitoring.components.filter.FilterRange;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Created by vlad
 * Date: 16.12.14_12:08
 */
public final class FormatterCalendar {

    public static final String TIME_SEPARATOR = ":";
    public static final int TIME_HOUR = 0;
    public static final int TIME_MINUTE = 1;
    public static final String DATE_SEPARATOR = "-";
    public static final int DATE_YEAR = 0;
    public static final int DATE_MONTH = 1;
    public static final int DATE_DAY = 2;
    public static final int TWO_PARAMS_LIMIT = 2;
    public static final int THREE_PARAMS_LIMIT = 3;
    private static final Logger log = Logger.getLogger(FormatterCalendar.class);

    private FormatterCalendar() {
    }

    public static String format(FilterRange range, LocalDateTime dateTime) {
        if (range != FilterRange.DAY) {
            return dateTime.toString("HH" + TIME_SEPARATOR + "mm");
        } else {
            return dateTime.toString("yyyy" + DATE_SEPARATOR + "MM" + DATE_SEPARATOR + "dd");
        }
    }

    public static LocalDateTime format(FilterRange range, String date) {
        try {
            if (range != FilterRange.DAY) {
                return getTime(date);
            } else {
                return getDate(date);
            }
        } catch (Exception e) {
            log.error("Parsing date[" + date + "] failed.", e);
        }
        return DateTime.now().toLocalDateTime();
    }

    private static LocalDateTime getTime(String date) {
        String[] split = date.split(TIME_SEPARATOR, TWO_PARAMS_LIMIT);
        int hour = Integer.parseInt(split[TIME_HOUR]);
        int minute = Integer.parseInt(split[TIME_MINUTE]);
        return DateTime.now().toLocalDateTime().withTime(hour, minute, 0, 0);
    }

    private static LocalDateTime getDate(String date) {
        String[] split = date.split(DATE_SEPARATOR, THREE_PARAMS_LIMIT);
        int year = Integer.parseInt(split[DATE_YEAR]);
        int month = Integer.parseInt(split[DATE_MONTH]);
        int day = Integer.parseInt(split[DATE_DAY]);
        return new LocalDateTime(year, month, day, 0, 0);
    }
}