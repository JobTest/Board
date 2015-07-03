package com.pb.dashboard.external.monitor.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.external.monitor.chart.parsetime.ParserTime;
import com.pb.dashboard.external.monitor.entype.FilterRange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.pb.dashboard.external.monitor.sessions.SessionsParam.*;

/**
 * Created by vlad
 * Date: 10.11.14_16:29
 */
public class SessionsModelBuilder {

    private static final Complex COMPLEX_DEFAULT = Complex.BIPLANE_API2X;
    private static final FilterRange FILTER_RANGE_DEFAULT = FilterRange.R10MIN;
    private static final String NULL_VALUE = "null";

    public static SessionsModel build(Map<String, String> params) {
        SessionsModel model = new SessionsModel();
        initComplex(model, params.get(COMPLEX.getName()));
        initSystem(model, params.get(SYSTEM.getName()));
        initFilter(model, params.get(RANGE.getName()));
        initDate(model, params.get(DATE.getName()));
        initMetric(model, params.get(INTERFACE.getName()));
        return model;
    }

    private static void initComplex(SessionsModel model, String complexId) {
        Complex complex;
        try {
            complex = Complex.pkeyToComplex(complexId);
        } catch (Exception e) {
            complex = COMPLEX_DEFAULT;
        }
        model.setComplex(complex);
    }

    private static void initSystem(SessionsModel model, String systemId) {
        boolean error = Boolean.parseBoolean(systemId);
        model.setSystem(error);
    }

    private static void initFilter(SessionsModel model, String filterId) {
        FilterRange filterRange = FILTER_RANGE_DEFAULT;
        if (IntegerUtil.checkInt(filterId)) {
            int id = Integer.parseInt(filterId);
            try {
                filterRange = FilterRange.idToFilter(id);
            } catch (IllegalArgumentException e) {
            }
        }
        model.setFilterRange(filterRange);
    }

    private static void initDate(SessionsModel model, String time) {
        FilterRange filterRange = model.getFilterRange();
        ParserTime parser = ParserTime.getParser(filterRange);
        String pattern = parser.getUrlPattern();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            calendar = Calendar.getInstance();
        }
        setData(model, calendar);
    }

    private static void setData(SessionsModel model, Calendar calendar) {
        FilterRange filter = model.getFilterRange();
        model.setYear(calendar.get(Calendar.YEAR));
        model.setMonth(calendar.get(Calendar.MONTH));
        model.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        if (filter == FilterRange.HOUR || filter == FilterRange.R10MIN) {
            model.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        }
        if (filter == FilterRange.R10MIN) {
            model.setMinute(calendar.get(Calendar.MINUTE));
        }
    }

    private static void initMetric(SessionsModel model, String bpInterface) {
        if (bpInterface != null) {
            if (bpInterface.isEmpty() || NULL_VALUE.equalsIgnoreCase(bpInterface)) {
                bpInterface = null;
            }
        }
        model.setInterfaceName(bpInterface);
    }
}
