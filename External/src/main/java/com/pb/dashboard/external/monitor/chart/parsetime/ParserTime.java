package com.pb.dashboard.external.monitor.chart.parsetime;

import com.pb.dashboard.external.monitor.entype.FilterRange;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vlad
 * Date: 10.11.14_12:02
 */
public abstract class ParserTime {

    protected Date date;

    public void setDateDefault(Date date) {
        this.date = date;
    }

    public String format(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = parse(time);
        return sdf.format(calendar.getTime());
    }

    public static ParserTime getParser(FilterRange range) {
        switch (range) {
            case R10MIN:
                return new ParserBy10Min();
            case HOUR:
                return new ParserByHours();
            case DAY:
                return new ParserByDay();
            case MONTH:
                return new ParserByMonth();
        }
        throw new IllegalArgumentException("id=" + range.getId() + ",name=" + range.getName());
    }

    public abstract String getUrlPattern();

    public abstract String getChartPattern();

    public abstract Calendar parse(String time);
}