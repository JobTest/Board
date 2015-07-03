package com.pb.dashboard.monitoring.errors.all.db.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pb.dashboard.core.db.mongo.DAOMongo;
import com.pb.dashboard.core.db.mongo.datasource.DMongoDatasource;
import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionInfo;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vlad
 * Date: 20.10.14_17:19
 */
public class ErrorsDBManager extends DAOMongo implements ErrorsDBManagerI {

    private static final Logger LOG = Logger.getLogger(ErrorsDBManager.class);

    private static final String FULL_SESSION_ID = "full_session_id";
    private static final String SESSION_ID = "session_id";
    private static final String MESSAGE = "message";
    private static final String TIMING_LEVEL = "timing_level";
    private static final String APP_TIMESTAMP = "app_timestamp";
    private static final String JPKG_NAME = "jpkg_name";
    private static final String DURATION = "duration";
    private static final String STATUS = "status";
    private static final String MODULE = "module";
    private static final String DOP_INFO = "dop_info";
    private static final String METHOD = "method";
    private static final String IN_NAME = "interface_name";
    private static final String START_TIME = "start_time";
    private static final String ERROR_CODE = "error_code";
    private static final String COMPANY_ID = "company_id";
    private static final String OUTER_SESSION_ID = "outer_session_id";

    private static final String SDF_YEAR_MONTH_DAY = "yyyy.MM.dd";
    private static final String SDF_DATE_TIME = "yyyy.MM.dd HH:mm:ss.SSS";

    @Override
    public Logger getLog() {
        return LOG;
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
        String sessionField = (complex == Complex.DEBT) ? SESSION_ID : FULL_SESSION_ID;
        BasicDBObject query = new BasicDBObject(sessionField, fullSessionId);
        if (!(complex == Complex.DEBT && level == TimingLevel.ANSWER)) {
            query.put(TIMING_LEVEL, level.getName());
        }
        BasicDBObject fields = createFields(MESSAGE);
        DMongoDatasource ds = dsForQueryAnswer(complex, level);

        DBObject dbObject = execOne(ds, query, fields);
        if (dbObject != null) {
            return getParam(dbObject, MESSAGE);
        }
        throw new DashSQLException(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING);
    }

    private DMongoDatasource dsForQueryAnswer(Complex complex, TimingLevel level) {
        switch (complex) {
            case BIPLANE_API2X:
                return DMongoDatasource.LOGSTASH_LOGS;
            case DEBT:
                if (level == TimingLevel.QUERY) {
                    return DMongoDatasource.DEBT_LOGS;
                } else {
                    return DMongoDatasource.DEBT_ERROR;
                }
            case TEMPLATES:
                return DMongoDatasource.TEMPLATE_LOGS;
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
        query.put(TIMING_LEVEL, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(APP_TIMESTAMP, DURATION, JPKG_NAME);

        DBObject dbObject = execOne(DMongoDatasource.DEBT_LOGS, query, fields);
        if (dbObject != null) {
            String appTimestamp = getParam(dbObject, APP_TIMESTAMP);
            String bpInterface = getParam(dbObject, JPKG_NAME);
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
        BasicDBObject fields = createFields(APP_TIMESTAMP, DURATION, JPKG_NAME);

        List<DBObject> list = execList(DMongoDatasource.TIMING_T2_T2_LOGS, query, fields);

        String duration = getDuration(list);
        info.setDuration(duration);

        String appTimeStamp = getAppTimeStampForApi(fullSessionId);
        info.setStartTimestamp(appTimeStamp);

        DBObject earlyDBObject = getEarlyDBObject(list);
        if (earlyDBObject != null) {
            String bpInterface = getParam(earlyDBObject, JPKG_NAME);
            info.setBpInterface(bpInterface);
        }
        return info;
    }

    private String getAppTimeStampForApi(String fullSessionId) throws DashSQLException {
        BasicDBObject query = new BasicDBObject(FULL_SESSION_ID, fullSessionId);
        query.put(TIMING_LEVEL, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(APP_TIMESTAMP);
        DBObject dbObject = execOne(DMongoDatasource.LOGSTASH_LOGS, query, fields);
        if (dbObject != null) {
            return getParam(dbObject, APP_TIMESTAMP);
        }
        return "";
    }

    public SessionInfo getSessionInfoForTemp(String fullSessionId) throws DashSQLException {
        if (fullSessionId == null || fullSessionId.isEmpty()) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "fullSessionId is null");
        }

        SessionInfo info = new SessionInfo();
        BasicDBObject query = new BasicDBObject(SESSION_ID, fullSessionId);
        BasicDBObject fields = createFields(APP_TIMESTAMP, DURATION, JPKG_NAME);
        List<DBObject> list = execList(DMongoDatasource.TEMPLATE_TIMING_T2, query, fields);

        String duration = getDuration(list);
        info.setDuration(duration);

        String appTimeStamp = getAppTimeStampForTemp(fullSessionId);
        info.setStartTimestamp(appTimeStamp);

        DBObject earlyDBObject = getEarlyDBObject(list);
        if (earlyDBObject != null) {
            String bpInterface = getParam(earlyDBObject, JPKG_NAME);
            info.setBpInterface(bpInterface);
        }
        return info;
    }

    private String getAppTimeStampForTemp(String fullSessionId) throws DashSQLException {
        BasicDBObject query = new BasicDBObject(FULL_SESSION_ID, fullSessionId);
        query.put(TIMING_LEVEL, TimingLevel.ANSWER.getName());
        BasicDBObject fields = createFields(APP_TIMESTAMP);
        DBObject dbObject = execOne(DMongoDatasource.TEMPLATE_LOGS, query, fields);
        return getParam(dbObject, APP_TIMESTAMP);
    }

    private DBObject getEarlyDBObject(List<DBObject> list) {
        SimpleDateFormat sdf = new SimpleDateFormat(SDF_DATE_TIME);
        DBObject earlyObject = null;
        Date earlyDate = null;
        for (DBObject dbObject : list) {
            try {
                String appTimeStamp = getParam(dbObject, APP_TIMESTAMP);
                Date date = sdf.parse(appTimeStamp);
                if (earlyDate == null || earlyDate.after(date)) {
                    earlyObject = dbObject;
                    earlyDate = date;
                }
            } catch (ParseException ignore) {
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

    public List<TimingT2Data> getTimingT2(@Nonnull Complex complex, @Nonnull String sessionId) throws DashSQLException {
        if (complex == Complex.DEBT) {
            return new ArrayList<TimingT2Data>();
        }
        List<TimingT2Data> timingsT2 = new ArrayList<TimingT2Data>();
        BasicDBObject query = new BasicDBObject(SESSION_ID, sessionId);
        BasicDBObject fields = createFields(MESSAGE, APP_TIMESTAMP, DURATION, STATUS, MODULE, DOP_INFO, METHOD);
        DMongoDatasource ds = convertComplexForTimingT2(complex);


        List<DBObject> result = execList(ds, query, fields);
        for (DBObject dbObject : result) {
            TimingT2Data timing = new TimingT2Data();
            timing.setMessage(getParam(dbObject, MESSAGE));
            timing.setStartTime(getParam(dbObject, APP_TIMESTAMP));
            timing.setDuration(getParam(dbObject, DURATION));
            timing.setStatus(getParam(dbObject, STATUS));
            timing.setModule(getParam(dbObject, MODULE));
            String dopInfo = getParam(dbObject, DOP_INFO);
            String convertDopInfo = DopInfoUtils.convert(dopInfo);
            timing.setDopInfo(convertDopInfo);
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
        BasicDBObject fields = createFields(SESSION_ID, IN_NAME, START_TIME, DURATION, STATUS, COMPANY_ID, ERROR_CODE);
        List<DBObject> result = execList(DMongoDatasource.API_TIMING_T0, query, fields);
        for (DBObject dbObject : result) {
            String sessionId = getParam(dbObject, SESSION_ID);
            String inName = getParam(dbObject, IN_NAME);
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

    private DMongoDatasource convertComplexForTimingT2(Complex complex) {
        switch (complex) {
            case TEMPLATES:
                return DMongoDatasource.TEMPLATE_TIMING_T2;
            case BIPLANE_API2X:
                return DMongoDatasource.TIMING_T2_T2_LOGS;
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

        DMongoDatasource ds = convertComplexForStackTrace(complex);
        List<DBObject> result = execList(ds, query, fields);
        for (DBObject dbObject : result) {
            SessionStackTraceData error = new SessionStackTraceData();
            error.setMessage(getParam(dbObject, MESSAGE));
            errors.add(error);
        }
        return errors;
    }

    private DMongoDatasource convertComplexForStackTrace(Complex complex) {
        switch (complex) {
            case TEMPLATES:
                return DMongoDatasource.TEMPLATE_ERROR;
            case BIPLANE_API2X:
                return DMongoDatasource.ERRORS_ERROR;
        }
        throw new IllegalArgumentException("Complex " + complex + " not valid.");
    }

    @Nonnull
    private String getParam(DBObject object, String param) {
        if (object == null || object.get(param) == null) {
            return "";
        }
        return String.valueOf(object.get(param));
    }
}