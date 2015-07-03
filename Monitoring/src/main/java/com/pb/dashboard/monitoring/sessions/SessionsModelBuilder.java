package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionType;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 22.12.14_10:33
 */
public final class SessionsModelBuilder {

    public static final String DATE_PATTERN = "yyyy-MM-dd_HH.mm";
    private static final Logger log = Logger.getLogger(SessionsModelBuilder.class);

    private SessionsModel model;
    private Map<String, String> map;

    public static SessionsModelI build(Map<String, String> map) {
        return new SessionsModelBuilder(map).getModel();
    }

    private SessionsModelBuilder(Map<String, String> map) {
        this.map = map;
        model = new SessionsModel();
        initCountry(getValue(MonitoringParam.COUNTRY));
        initComplex(getValue(MonitoringParam.COMPLEX));
        initInterface(getValue(MonitoringParam.INTERFACE));
        initDates(getValue(MonitoringParam.DATE_FROM), getValue(MonitoringParam.DATE_TO));
        initCategory(getValue(MonitoringParam.CRITERION));
        initSessions();
    }

    private void initCountry(String value) {
        Country country;
        try {
            country = Country.pkeyToCountry(value);
        } catch (Exception e) {
            country = Country.UKRAINE;
        }
        model.setCountry(country);
    }

    private void initComplex(String value) {
        Complex complex;
        try {
            complex = Complex.pkeyToComplex(value);
        } catch (Exception e) {
            complex = Complex.BIPLANE_API2X;
        }
        model.setComplex(complex);
    }

    private void initInterface(String value) {
        Map<Integer, DInterfaceI> interfaces = loadInterfaces(model.getCountry(), model.getComplex());
        model.setInterfaces(interfaces);

        if (IntegerUtil.checkInt(value)) {
            int pkey = Integer.parseInt(value);
            model.setBpInterface(interfaces.get(pkey));
            return;
        }
        if (!interfaces.isEmpty()) {
            model.setBpInterface(interfaces.get(0));
        }
    }

    private void initDates(String from, String to) {
        LocalDateTime dateFrom = parseDate(from);
        LocalDateTime dateTo = parseDate(to);
        model.setDateFrom(dateFrom);
        model.setDateTo(dateTo);
    }

    private LocalDateTime parseDate(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, DateTimeFormat.forPattern(DATE_PATTERN));
        } catch (RuntimeException e) {
            log.warn("Date[" + dateStr + "] is invalid format." + DATE_PATTERN, e);
        }
        return new LocalDateTime(DateTimeZone.UTC);
    }

    private void initCategory(String value) {
        SessionType category;
        try {
            category = SessionType.idToType(value);
        } catch (Exception e) {
            category = SessionType.MAX_OK;
        }
        model.setCategory(category);
    }

    private void initSessions() {
        String interfaceName = model.getBpInterface().getName();
        LocalDateTime from = model.getDateFrom();
        LocalDateTime to = model.getDateTo();
        int complexId = model.getComplex().getId();

        Map<SessionType, List<BpSession>> sessions = loadSessions(interfaceName, from, to, complexId);
        Map<SessionType, List<BpSession>> sessionsWithoutDebt = loadSessionsWithoutDebt(interfaceName, from, to, complexId);
        for (Map.Entry<SessionType, List<BpSession>> item : sessions.entrySet()) {
            model.getSessions(item.getKey()).addAll(item.getValue());
        }
        for (Map.Entry<SessionType, List<BpSession>> item : sessionsWithoutDebt.entrySet()) {
            model.getSessionsWithoutDebt(item.getKey()).addAll(item.getValue());
        }
    }

    private String getValue(MonitoringParam param) {
        if (map == null || map.isEmpty() || param == null) {
            return null;
        }
        return map.get(param.getName());
    }

    public Map<Integer, DInterfaceI> loadInterfaces(Country country, Complex complex) {
        return ServiceFactory.getMonitoring().getInterfaces(complex.getId(), country.getId());
    }

    public Map<SessionType, List<BpSession>> loadSessionsWithoutDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return ServiceFactory.getMonitoring().getSessionsWithoutDebt(bpInterface, dateFrom, dateTo, complexPKey);
    }

    public Map<SessionType, List<BpSession>> loadSessions(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return ServiceFactory.getMonitoring().getSessionsWithDebt(bpInterface, dateFrom, dateTo, complexPKey);
    }

    public SessionsModel getModel() {
        return model;
    }
}