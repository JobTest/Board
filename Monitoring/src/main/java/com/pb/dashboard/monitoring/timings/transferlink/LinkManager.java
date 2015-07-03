package com.pb.dashboard.monitoring.timings.transferlink;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionType;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.sessions.SessionsModelBuilder;
import com.pb.dashboard.monitoring.timings.TimingsType;
import com.pb.dashboard.monitoring.timings.filter.FilterModelBuilder;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import org.joda.time.LocalDateTime;

public class LinkManager implements LinkManagerI {

    public static final Complex[] EXCLUDE_COMPLEX = {Complex.REPORTS, Complex.SERVER_AUTO_UPLOAD};

    private FilterModelI filter;
    private NavigatorModelAdapter navigator;

    public LinkManager(FilterModelI filter, NavigatorModelAdapter navigator) {
        this.filter = filter;
        this.navigator = navigator;
    }

    private boolean exclude(Complex currentComplex) {
        for (Complex complex : EXCLUDE_COMPLEX) {
            if (currentComplex == complex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String urlParamsSessions(LocalDateTime fromDateTime, LocalDateTime toDateTime, String category) {
        int countryId = navigator.getCountry().getId();
        int complexId = navigator.getComplex().getId();
        String interfaceId = getBpInterfaceId();
        int categoryId = getCategory(category).getId();
        String pattern = SessionsModelBuilder.DATE_PATTERN;
        UrlParamBuilder params = new UrlParamBuilder()
                .addParam(MonitoringParam.COUNTRY.getName(), countryId)
                .addParam(MonitoringParam.COMPLEX.getName(), complexId)
                .addParam(MonitoringParam.INTERFACE.getName(), interfaceId)
                .addParam(MonitoringParam.DATE_FROM.getName(), fromDateTime.toString(pattern))
                .addParam(MonitoringParam.DATE_TO.getName(), toDateTime.toString(pattern))
                .addParam(MonitoringParam.CRITERION.getName(), categoryId);

        return params.toString();
    }

    private SessionType getCategory(String seriesName) {
        if (TimingsType.MIN.getName().equalsIgnoreCase(seriesName)) {
            return SessionType.MIN_OK;
        }
        if (TimingsType.ERRORS.getName().equalsIgnoreCase(seriesName)) {
            return SessionType.MAX_ERRORS;
        }
        return SessionType.MAX_OK;
    }

    private String getBpInterfaceId() {
        DInterfaceI bpInterface = navigator.getBpInterface();
        if (bpInterface != null) {
            return String.valueOf(bpInterface.getPkey());
        }
        return "";
    }

    @Override
    public String pathToTimings() {
        String mainPath = Dashboard.Biplane.Monitoring.Timings.PATH;
        String urlParams = urlParamsTimings();
        return String.format("%s/%s", mainPath, urlParams);
    }

    @Override
    public String urlParamsTimings() {
        UrlParamBuilder builder = new UrlParamBuilder();
        int country = navigator.getCountry().getId();
        int complex = navigator.getComplex().getId();
        int bpInterface = navigator.getBpInterface().getPkey();
        int metric = navigator.getMetric().getPkey();

        int filterRange = filter.getFilterRange().getPkey();
        String pattern = FilterModelBuilder.DATE_PATTERN;
        String date = filter.getDate().toString(pattern);
        String from = filter.getDateFrom().toString(pattern);
        String to = filter.getDateTo().toString(pattern);
        String reglament = String.valueOf(filter.isReglament());

        builder.addParam(MonitoringParam.COUNTRY.getName(), country)
                .addParam(MonitoringParam.COMPLEX.getName(), complex)
                .addParam(MonitoringParam.INTERFACE.getName(), bpInterface)
                .addParam(MonitoringParam.METRIC.getName(), metric)
                .addParam(MonitoringParam.RANGE.getName(), filterRange);
        if (filter.getFilterRange() == FilterRange.DAY) {
            builder.addParam(MonitoringParam.DATE_FROM.getName(), from)
                    .addParam(MonitoringParam.DATE_TO.getName(), to);
        } else {
            builder.addParam(MonitoringParam.DATE.getName(), date)
                    .addParam(MonitoringParam.REGLAMENT.getName(), reglament);
        }
        return builder.toString();
    }

    @Override
    public String pathToSessions(LocalDateTime fromDateTime, LocalDateTime toDateTime, String category) {
        if (exclude(navigator.getComplex())) {
            return null;
        }
        String mainPath = Dashboard.Biplane.Monitoring.Sessions.PATH;
        String urlParams = urlParamsSessions(fromDateTime, toDateTime, category);
        return String.format("%s/%s", mainPath, urlParams);
    }
}