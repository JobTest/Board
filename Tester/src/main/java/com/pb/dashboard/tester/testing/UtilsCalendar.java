package com.pb.dashboard.tester.testing;

import java.util.Calendar;

public class UtilsCalendar {

    public static int getCountWorkingDaysPrevWeek() {
        return 5;
    }

    public static int getCountWorkingDaysPresWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek <= Calendar.FRIDAY) {
            return dayOfWeek - 1;
        }
        return 5;
    }

    public static int getCountWorkingDaysPrevMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY) {
            month = Calendar.DECEMBER;
            year--;
        } else {
            month--;
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return culcWorkingDaysInMonth(calendar, maxDays);
    }

    public static int getCountWorkingDaysPresMonth() {
        Calendar calendar = Calendar.getInstance();
        int maxDays = calendar.get(Calendar.DAY_OF_MONTH);
        return culcWorkingDaysInMonth(calendar, maxDays);
    }

    private static int culcWorkingDaysInMonth(Calendar calendar, int maxDays) {
        int countWorkingDays = 0;

        for (int i = 1; i <= maxDays; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                countWorkingDays++;
            }
        }
        return countWorkingDays;
    }

    public static int getCountWorkingDaysPrevQuarter() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int quarter = month / 3;
        if (quarter == 0) {
            quarter = 3;
            calendar.set(Calendar.YEAR, --year);
        } else {
            quarter--;
        }
        int res = 0;
        for (int i = 0; i < 3; i++) {
            calendar.set(Calendar.MONTH, quarter * 3 + i);
            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            res += culcWorkingDaysInMonth(calendar, maxDays);
        }
        return res;
    }

    public static int getCountWorkingDaysPresQuarter() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int quarter = month / 3;

        int monthInQuarter = month % 3;

        int res = 0;
        for (int i = 0; i < monthInQuarter; i++) {
            calendar.set(Calendar.MONTH, quarter * 3 + i);
            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            res += culcWorkingDaysInMonth(calendar, maxDays);
        }
        res += getCountWorkingDaysPresMonth();
        return res;
    }
}
