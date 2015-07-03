package com.pb.dashboard.core.util;

import java.util.Calendar;

/**
 * Created by vlad
 * Date: 20.01.15_10:05
 */
public final class DateUtil {

    public static final int MILLIS_IN_SEC = 1000;
    public static final int SEC_IN_MINUTE = 60;

    private DateUtil() {
    }

    public static boolean isSameDay(Calendar c1, Calendar c2) {
        if (!isSameMonth(c1, c2)) {
            return false;
        }
        return c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        if (!isSameYear(c1, c2)) {
            return false;
        }
        return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }

    public static boolean isSameYear(Calendar c1, Calendar c2) {
        if (c1 == null && c2 == null) {
            return true;
        }
        if (c1 == null || c2 == null) {
            return false;
        }
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    public static long diffMinute(Calendar c1, Calendar c2) {
        long millis1 = c1.getTimeInMillis();
        long millis2 = c2.getTimeInMillis();
        long diffMillis = Math.abs(millis1 - millis2);
        long diffSec = diffMillis / MILLIS_IN_SEC;
        return diffSec / SEC_IN_MINUTE;
    }
}