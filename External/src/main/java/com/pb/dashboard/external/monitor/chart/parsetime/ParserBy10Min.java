package com.pb.dashboard.external.monitor.chart.parsetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vlad
 * Date: 12.11.14_14:40
 */
public class ParserBy10Min extends ParserTime {

    public static final String CHART_TIME_PATTERN = "HH:mm";
    public static final String URL_TIME_PATTERN = "yyyy-MM-dd_HH.mm";

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

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CHART_TIME_PATTERN);
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            res.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            res.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        } catch (ParseException e) {
        }
        return res;
    }
}
