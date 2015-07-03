package com.pb.dashboard.server.service.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Locale;

/**
 * Created by vlad
 * Date: 12.05.15_12:35
 */
public class HeaderUtils {

    public static final int HOUR_IN_SECONDS = 60 * 60;
    public static final int MINUTES_10_IN_SECONDS = 60 * 10;
    public static final int DAY_1_IN_SECONDS = HOUR_IN_SECONDS * 24;
    public static final int MONTH_1_IN_SECONDS = DAY_1_IN_SECONDS * 30;
    public static final int WEEK_1_IN_SECONDS = DAY_1_IN_SECONDS * 7;

    public static final String DATE_PATTERN_GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final String EXPIRES = "Expires";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String MAX_AGE = "max-age=";

    public static String getDateDayGMT(LocalDate date, int days) {
        int diff = around(date.getDayOfMonth(), days, date.dayOfMonth().getMaximumValue());
        LocalDateTime dateTime = date.withDayOfMonth(1).plusDays(diff - 1).toLocalDateTime(new LocalTime(0, 0));
        return dateTime.toString(DATE_PATTERN_GMT, Locale.US);
    }

    public static String getDateHourGMT(LocalDateTime time, int hours) {
        int diff = around(time.getHourOfDay(), hours, time.hourOfDay().getMaximumValue());
        time = new LocalDateTime(time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), 0, 0).plusHours(diff);
        return time.toString(DATE_PATTERN_GMT, Locale.US);
    }

    public static String getDateMinuteGMT(LocalDateTime time, int minutes) {
        int diff = around(time.getMinuteOfHour(), minutes, time.minuteOfHour().getMaximumValue());
        time = new LocalDateTime(time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(), 0).plusMinutes(diff);
        return time.toString(DATE_PATTERN_GMT, Locale.US);
    }

    private static int around(int value, int degree, int maxValue) {
        int diff = value + degree - value % degree;
        if (diff > maxValue) {
            diff = maxValue + 1;
        }
        return diff;
    }
}