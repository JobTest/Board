package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionType;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 22.12.14_11:33
 */
public interface SessionsModelI {

    public void addSessionsWithoutDebt(SessionType type, BpSession session);

    public List<BpSession> getSessionsWithoutDebt(SessionType type);

    public void addSessions(SessionType type, BpSession session);

    public List<BpSession> getSessions(SessionType type);

    public LocalDateTime getDateTo();

    public LocalDateTime getDateFrom();

    public Country getCountry();

    public Complex getComplex();

    public DInterfaceI getBpInterface();

    public Map<Integer, DInterfaceI> getInterfaces();

    public SessionType getCategory();
}
