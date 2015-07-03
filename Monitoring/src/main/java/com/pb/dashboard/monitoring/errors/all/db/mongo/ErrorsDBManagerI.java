package com.pb.dashboard.monitoring.errors.all.db.mongo;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionInfo;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;

import java.util.Date;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.12.14_9:24
 */
public interface ErrorsDBManagerI {

    public String getResultBySession(TimingLevel level, Complex complex, String fullSessionId) throws DashSQLException;

    public SessionInfo getSessionInfo(Complex complex, String sessionId) throws DashSQLException;

    public SessionInfo getSessionInfoForDebt(String sessionId) throws DashSQLException;

    public SessionInfo getSessionInfoForApi(String fullSessionId) throws DashSQLException;

    public SessionInfo getSessionInfoForTemp(String fullSessionId) throws DashSQLException;

    public List<TimingT2Data> getTimingT2(Complex complex, String sessionId) throws DashSQLException;

    public List<OuterSessionData> getDataByOuterSession(String outerSessionId, Date date) throws DashSQLException;

    public List<SessionStackTraceData> getStackTraces(Complex complex, String sessionId) throws DashSQLException;
}
