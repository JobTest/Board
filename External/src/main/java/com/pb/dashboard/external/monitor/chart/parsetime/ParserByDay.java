package com.pb.dashboard.external.monitor.chart.parsetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vlad
 * Date: 10.11.14_12:05
 */
public class ParserByDay extends ParserTime {

    public static final String CHART_TIME_PATTERN = "yyyy-MM-dd";
    public static final String URL_TIME_PATTERN = "yyyy-MM-dd";

    @Override
    public String getUrlPattern() {
        return URL_TIME_PATTERN;
    }

    @Override
    public String getChartPattern() {
        return CHART_TIME_PATTERN;
    }

    @Override
    public Calendar parse(String time) {
        Calendar res = Calendar.getInstance();
        if (date != null) {
            res.setTime(date);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CHART_TIME_PATTERN);
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            res.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            res.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            res.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
        }
        return res;
    }
}
