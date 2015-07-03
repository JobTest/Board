package com.pb.dashboard.monitoring.errors.all.db.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pb.dashboard.core.db.mongo.DAOMongo;
import com.pb.dashboard.core.db.mongo.DMongoCollection;
import com.pb.dashboard.core.db.mongo.client.DMongoClientCustom;
import com.pb.dashboard.core.db.mongo.client.DMongoClientI;
import com.pb.dashboard.core.db.mongo.datasource.DMongoDatasourceCustom;
import com.pb.dashboard.core.db.mongo.datasource.DMongoDatasourceI;
import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionInfo;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.12.14_10:18
 */
public class ErrorsDashDBManager extends DAOMongo implements ErrorsDBManagerI {

    private static final Logger log = Logger.getLogger(ErrorsDashDBManager.class);

    private static final String SESSION_ID = "session_id";
    private static final String MESSAGE = "message";
    private static final String LOGGER_NAME = "logger_name";
    private static final String DURATION = "duration";
    private static final String STATUS = "status";
    private static final String MODULE = "module";
    private static final String DOP_INFO = "dop_info";
    private static final String METHOD = "method";
    private static final String INTERFACE_NAME = "interface_name";
    private static final String START_TIME = "start_time";
    private static final String ERROR_CODE = "error_code";
    private static final String COMPANY_ID = "company_id";
    private static final String OUTER_SESSION_ID = "outer_session_id";

    private static final String SDF_YEAR_MONTH_DAY = "yyyy.MM.dd";
    private static final String SDF_DATE_TIME = "yyyy.MM.dd HH:mm:ss.SSS";

    private DMongoClientI dMongoClient;

    public ErrorsDashDBManager(String host, int port) {
        dMongoClient = new DMongoClientCustom(host, port);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    public String getResultBySession(TimingLevel level, Complex complex, String fullSessionId) throws DashSQLException {
        if (level == null) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "level is null");
        }
        if (complex == null) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "complex is null");
        }
        if (fullSessionId == null || fullSessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "fullSessionId is null/empty");
        }
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        query.put(LOGGER_NAME, level.getName());
        BasicDBObject fields = createFields(MESSAGE);
        DMongoDatasourceI ds = collectionForQueryAnswer(complex);

        DBObject dbObject = execOne(ds, query, fields);
        if (dbObject != null) {
            return getParam(dbObject, MESSAGE);
        }
        throw new DashSQLException(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING);
    }

    private DMongoDatasourceI collectionForQueryAnswer(Complex complex) {
        switch (complex) {
            case BIPLANE_API2X:
                return getDatasource(DMongoCollection.API_XML_T0);
            case DEBT:
                return getDatasource(DMongoCollection.DEBT_XML_T0);
            case TEMPLATES:
                return getDatasource(DMongoCollection.TEMP_XML_T0);
        }
        throw new IllegalArgumentException("Complex " + complex + " not valid.");
    }

    public SessionInfo getSessionInfo(Complex complex, String sessionId) throws DashSQLException {
        switch (complex) {
            case BIPLANE_API2X:
                return getSessionInfoForApi(sessionId);
            case DEBT:
                return getSessionInfoForDebt(sessionId);
            case TEMPLATES:
                return getSessionInfoForTemp(sessionId);
        }
        throw new IllegalArgumentException("Complex " + complex + " not valid.");
    }

    public SessionInfo getSessionInfoForDebt(String sessionId) throws DashSQLException {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "sessionId is null");
        }
        BasicDBObject query = new BasicDBObject(SESSION_ID, sessionId);
        query.put(LOGGER_NAME, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(START_TIME, DURATION, INTERFACE_NAME);

        DBObject dbObject = execOne(getDatasource(DMongoCollection.DEBT_XML_T0), query, fields);
        if (dbObject != null) {
            String appTimestamp = getParam(dbObject, START_TIME);
            String bpInterface = getParam(dbObject, INTERFACE_NAME);
            String duration = getParam(dbObject, DURATION);
            return new SessionInfo(appTimestamp, duration, bpInterface);
        }
        return new SessionInfo();
    }

    public SessionInfo getSessionInfoForApi(String fullSessionId) throws DashSQLException {
        if (fullSessionId == null || fullSessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "fullSessionId is null");
        }
        SessionInfo info = new SessionInfo();
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        BasicDBObject fields = createFields(START_TIME, DURATION, INTERFACE_NAME);

        DMongoDatasourceI ds = getDatasource(DMongoCollection.API_TIMING_T2);
        List<DBObject> list = execList(ds, query, fields);

        String duration = getDuration(list);
        info.setDuration(duration);

        String appTimeStamp = getAppTimeStampForApi(fullSessionId);
        info.setStartTimestamp(appTimeStamp);

        DBObject earlyDBObject = getEarlyDBObject(list);
        if (earlyDBObject != null) {
            String bpInterface = getParam(earlyDBObject, INTERFACE_NAME);
            info.setBpInterface(bpInterface);
        }
        return info;
    }

    private String getAppTimeStampForApi(String fullSessionId) throws DashSQLException {
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        query.put(LOGGER_NAME, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(START_TIME);

        DMongoDatasourceI ds = getDatasource(DMongoCollection.API_XML_T0);
        DBObject dbObject = execOne(ds, query, fields);
        if (dbObject != null) {
            return getParam(dbObject, START_TIME);
        }
        return "";
    }

    public SessionInfo getSessionInfoForTemp(String fullSessionId) throws DashSQLException {
        if (fullSessionId == null || fullSessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "fullSessionId is null");
        }

        SessionInfo info = new SessionInfo();
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        BasicDBObject fields = createFields(START_TIME, DURATION, INTERFACE_NAME);
        DMongoDatasourceI ds = getDatasource(DMongoCollection.TEMP_TIMING_T2);
        List<DBObject> list = execList(ds, query, fields);

        String duration = getDuration(list);
        info.setDuration(duration);

        String appTimeStamp = getAppTimeStampForTemp(fullSessionId);
        info.setStartTimestamp(appTimeStamp);

        DBObject earlyDBObject = getEarlyDBObject(list);
        if (earlyDBObject != null) {
            String bpInterface = getParam(earlyDBObject, INTERFACE_NAME);
            info.setBpInterface(bpInterface);
        }
        return info;
    }

    private String getAppTimeStampForTemp(String fullSessionId) throws DashSQLException {
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        query.put(LOGGER_NAME, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(START_TIME);
        DMongoDatasourceI ds = getDatasource(DMongoCollection.TEMP_XML_T0);
        DBObject dbObject = execOne(ds, query, fields);
        if (dbObject != null) {
            return getParam(dbObject, START_TIME);
        }
        return "";
    }

    private DBObject getEarlyDBObject(List<DBObject> list) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_DATE_TIME);
        DBObject earlyObject = null;
        Date earlyDate = null;
        for (DBObject dbObject : list) {
            try {
                String appTimeStamp = getParam(dbObject, START_TIME);
                Date date = sdf.parse(appTimeStamp);
                if (earlyDate == null || earlyDate.after(date)) {
                    earlyObject = dbObject;
                    earlyDate = date;
                }
            } catch (ParseException e) {
            }
        }
        return earlyObject;
    }

    private String getDuration(List<DBObject> list) {
        int duration = 0;
        for (DBObject dbObject : list) {
            String durationS = getParam(dbObject, DURATION);
            if (IntegerUtil.checkInt(durationS)) {
                duration += Integer.parseInt(durationS);
            }
        }
        return String.valueOf(duration);
    }

    public List<TimingT2Data> getTimingT2(Complex complex, String sessionId) throws DashSQLException {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "sessionId is null");
        }
        if (complex == Complex.DEBT) {
            return new ArrayList<TimingT2Data>();
        }
        List<TimingT2Data> timingsT2 = new ArrayList<TimingT2Data>();
        BasicDBObject query = new BasicDBObject(SESSION_ID, sessionId);
        BasicDBObject fields = createFields(MESSAGE, START_TIME, DURATION, STATUS, MODULE, DOP_INFO, METHOD);
        DMongoDatasourceI ds = convertComplexForTimingT2(complex);

        List<DBObject> result = execList(ds, query, fields);
        for (DBObject dbObject : result) {
            TimingT2Data timing = new TimingT2Data();
            timing.setMessage(getParam(dbObject, MESSAGE));
            timing.setStartTime(getParam(dbObject, START_TIME));
            timing.setDuration(getParam(dbObject, DURATION));
            timing.setStatus(getParam(dbObject, STATUS));
            timing.setModule(getParam(dbObject, MODULE));
            String dopInfo = getParam(dbObject, DOP_INFO);
            timing.setDopInfo(DopInfoUtils.convert(dopInfo));
            timing.setMethod(getParam(dbObject, METHOD));
            timingsT2.add(timing);
        }
        return timingsT2;
    }

    public List<OuterSessionData> getDataByOuterSession(String outerSessionId, Date date) throws DashSQLException {
        if (outerSessionId == null || outerSessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "outerSessionId is null/empty. Res = " + outerSessionId);
        }
        List<OuterSessionData> data = new ArrayList<OuterSessionData>();
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_YEAR_MONTH_DAY);
        String formattedDate = sdf.format(date);
        BasicDBObject query = new BasicDBObject();
        query.put(OUTER_SESSION_ID, outerSessionId);
        query.put(START_TIME, dateLike(formattedDate));
        BasicDBObject fields = createFields(SESSION_ID, INTERFACE_NAME, START_TIME, DURATION, STATUS, COMPANY_ID, ERROR_CODE);

        DMongoDatasourceI ds = getDatasource(DMongoCollection.API_TIMING_T0);
        List<DBObject> result = execList(ds, query, fields);
        for (DBObject dbObject : result) {
            String sessionId = getParam(dbObject, SESSION_ID);
            String inName = getParam(dbObject, INTERFACE_NAME);
            String startTime = getParam(dbObject, START_TIME);
            String duration = getParam(dbObject, DURATION);
            String status = getParam(dbObject, STATUS);
            String companyId = getParam(dbObject, COMPANY_ID);
            String errorCode = getParam(dbObject, ERROR_CODE);
            data.add(new OuterSessionData(sessionId, inName, startTime, duration, status, companyId, errorCode));
        }
        return data;
    }

    private BasicDBObject dateLike(String date) {
        return new BasicDBObject("$regex", date + ".*");
    }

    private DMongoDatasourceI convertComplexForTimingT2(Complex complex) {
        switch (complex) {
            case TEMPLATES:
                return getDatasource(DMongoCollection.TEMP_TIMING_T2);
            case BIPLANE_API2X:
                return getDatasource(DMongoCollection.API_TIMING_T2);
        }
        throw new IllegalArgumentException("Complex " + complex + " not valid.");
    }

    public List<SessionStackTraceData> getStackTraces(Complex complex, String sessionId) throws DashSQLException {
        if (complex == null) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "complex is null");
        }
        if (sessionId == null || sessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "sessionId is null/empty");
        }
        List<SessionStackTraceData> errors = new ArrayList<SessionStackTraceData>();
        BasicDBObject query = new BasicDBObject(SESSION_ID, sessionId);
        BasicDBObject fields = new BasicDBObject(MESSAGE, true);

        DMongoDatasourceI ds = convertComplexForStackTrace(complex);
        List<DBObject> result = execList(ds, query, fields);
        for (DBObject dbObject : result) {
            SessionStackTraceData error = new SessionStackTraceData();
            error.setMessage(getParam(dbObject, MESSAGE));
            errors.add(error);
        }
        return errors;
    }

    private DMongoDatasourceI convertComplexForStackTrace(Complex complex) {
        switch (complex) {
            case BIPLANE_API2X:
                return getDatasource(DMongoCollection.API_STACKTRACE);
            case TEMPLATES:
                return getDatasource(DMongoCollection.TEMP_STACKTRACE);
        }
        throw new IllegalArgumentException("Complex " + complex + " not valid.");
    }

    private DMongoDatasourceI getDatasource(DMongoCollection collection) {
        return new DMongoDatasourceCustom(dMongoClient, collection);
    }

    private String getParam(DBObject object, String param) {
        if (object == null || object.get(param) == null) {
            return "";
        }
        return object.get(param).toString();
    }
}
