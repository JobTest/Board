package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionType;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SessionsModel implements SessionsModelI {

    private Country country;
    private Complex complex;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private DInterfaceI bpInterface;
    private Map<Integer, DInterfaceI> bpInterfaces;
    private SessionType category;
    private Map<SessionType, List<BpSession>> sessions = new LinkedHashMap<>();
    private Map<SessionType, List<BpSession>> sessionsWithoutDebt = new LinkedHashMap<>();

    public SessionsModel() {
        for (SessionType type : SessionType.values()) {
            sessions.put(type, new ArrayList<BpSession>());
            sessionsWithoutDebt.put(type, new ArrayList<BpSession>());
        }
    }

    @Override
    public void addSessionsWithoutDebt(SessionType type, BpSession session) {
        getSessionsWithoutDebt(type).add(session);
    }

    @Override
    public List<BpSession> getSessionsWithoutDebt(SessionType type) {
        return sessionsWithoutDebt.get(type);
    }

    @Override
    public void addSessions(SessionType type, BpSession session) {
        getSessions(type).add(session);
    }

    @Override
    public List<BpSession> getSessions(SessionType type) {
        return sessions.get(type);
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public Complex getComplex() {
        return complex;
    }

    @Override
    public DInterfaceI getBpInterface() {
        return bpInterface;
    }

    @Override
    public Map<Integer, DInterfaceI> getInterfaces() {
        return bpInterfaces;
    }

    @Override
    public SessionType getCategory() {
        return category;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public void setInterfaces(Map<Integer, DInterfaceI> interfaces) {
        this.bpInterfaces = interfaces;
    }

    public void setBpInterface(DInterfaceI bpInterface) {
        this.bpInterface = bpInterface;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public void setCategory(SessionType category) {
        this.category = category;
    }
}