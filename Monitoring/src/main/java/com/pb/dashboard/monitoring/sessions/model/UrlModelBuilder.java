package com.pb.dashboard.monitoring.sessions.model;

import com.pb.dashboard.core.util.DateUtil;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.sessions.SessionsModelBuilder;
import com.pb.dashboard.monitoring.timings.filter.FilterModelBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by vlad
 * Date: 19.01.15_12:16
 */
public class UrlModelBuilder {

    public static final int R10MIN = 10;
    public static final int R60MIN = 60;
    private UrlModel model;
    private Map<String, String> params;

    public UrlModel buildModel(Map<String, String> params) {
        this.params = params;
        model = new UrlModel();
        init();
        return model;
    }

    private void init() {
        model.setCountry(initInt(MonitoringParam.COUNTRY));
        model.setComplex(initInt(MonitoringParam.COMPLEX));
        model.setBpInterface(initInt(MonitoringParam.INTERFACE));
        String dateFrom = getValue(params, MonitoringParam.DATE_FROM);
        String dateTo = getValue(params, MonitoringParam.DATE_TO);
        initRange(dateFrom, dateTo);
    }

    private void initRange(String dateFrom, String dateTo) {
        String datePattern = SessionsModelBuilder.DATE_PATTERN;
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        Calendar from = initDate(sdf, dateFrom);
        Calendar to = initDate(sdf, dateTo);
        FilterRange range;
        long diff = DateUtil.diffMinute(from, to);
        if (diff <= R10MIN) {
            range = FilterRange.R10MIN;
        } else if (diff <= R60MIN) {
            range = FilterRange.HOUR;
        } else {
            range = FilterRange.DAY;
        }
        model.setRange(range);
        initDates(from, to);
    }

    private void initDates(Calendar from, Calendar to) {
        if (model.getRange() == FilterRange.DAY) {
            model.setDateFrom(from);
            model.setDateTo(to);
        } else {
            model.setDate(from);
        }
    }

    private Integer initInt(MonitoringParam param) {
        String value = getValue(params, param);
        if (IntegerUtil.checkInt(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    private Calendar initDate(SimpleDateFormat sdf, String value) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(value);
            calendar.setTime(date);
            return calendar;
        } catch (Exception ignore) {
        }
        return calendar;
    }


    private String getValue(Map<String, String> map, MonitoringParam param) {
        if (map == null || map.isEmpty() || param == null) {
            return null;
        }
        return map.get(param.getName());
    }

    public static String buildUrl(UrlModel model) {
        String datePattern = FilterModelBuilder.DATE_PATTERN;
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        UrlParamBuilder builder = new UrlParamBuilder();
        builder.addParam(MonitoringParam.COUNTRY, model.getCountry())
                .addParam(MonitoringParam.COMPLEX, model.getComplex())
                .addParam(MonitoringParam.INTERFACE, model.getBpInterface())
                .addParam(MonitoringParam.RANGE, model.getRange().getPkey());
        if (model.getRange() == FilterRange.DAY) {
            builder.addParam(MonitoringParam.DATE_FROM, sdf.format(model.getDateFrom().getTime()))
                    .addParam(MonitoringParam.DATE_TO, sdf.format(model.getDateTo().getTime()));
        } else {
            builder.addParam(MonitoringParam.DATE, sdf.format(model.getDate().getTime()));
        }
        return builder.toString();
    }
}