package com.pb.dashboard.dao.dbmanager;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.dao.entity.biplanesupport.db.*;
import com.pb.dashboard.dao.service.container.BpInterface;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class IqDbManager extends DBManager {

    public static final Integer WITH_DEBT = null;
    public static final Integer WITHOUT_DEBT = 1;
    public static final String SESSION_DATE_PATTERN = "yyyy-MM-dd_HH.mm";
    private static final Logger log = Logger.getLogger(IqDbManager.class);
    private static final ResourceNames DATABASE = ResourceNames.BIPLANE_SUPPORT_DB;
    private static IqDbManager instance;

    public static IqDbManager getInstance() {
        if (instance == null) {
            instance = new IqDbManager();
        }
        return instance;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public Map<String, Integer> getQueryErrCnt(Complex complex, boolean isSystem, int year, int month,
                                               Integer day, Integer hour, Integer minute) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            String query = IqQueryBuilder.getQueryErrCnt();
            int system = IntegerUtil.toInt(isSystem);
            ResultSet rs = getRSByCallStmt(query, complex.getId(), system, year, toMonthDb(month), day, hour, minute);
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    public Map<Integer, Integer> getErrorCntByStatus(Complex complex, boolean isSystem, int year, int month,
                                                     Integer day, Integer hour, Integer minute, BpInterface bpInterface) {
        Map<Integer, Integer> map = new HashMap<>();
        String query = IqQueryBuilder.getSystemGetErrCntByQuery();
        try {
            int system = IntegerUtil.toInt(isSystem);
            ResultSet rs = getRSByCallStmt(query, complex.getId(), system,
                    year, toMonthDb(month), day, hour, minute, bpInterface.getMain(), bpInterface.getPart());
            while (rs.next()) {
                map.put(rs.getInt("status"), rs.getInt("err_cnt"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    public List<BpSession> getSessionsByErrStatus(Complex complex, int year, int month, Integer day, Integer hour,
                                                  Integer minute, BpInterface bpInterface, String errorStatus) {
        List<BpSession> list = new ArrayList<>();
        String query = IqQueryBuilder.getDbSystemGetSessionByErrQuery();
        try {
            ResultSet rs = getRSByCallStmt(query, complex.getId(), year, toMonthDb(month), day, hour, minute, errorStatus, bpInterface.getMain(), bpInterface.getPart());
            while (rs.next()) {
                list.add(new BpSession(rs.getString(1)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return list;
    }

    public Map<String, List<InterfaceData>> getRecStatsByHour(String[] interfaces) {
        return getErrStats(IqQueryBuilder.getErrCntByQuery(), interfaces);
    }

    public Map<String, List<InterfaceData>> getTempStatsByHour(String[] interfaces) {
        return getErrStats(IqQueryBuilder.getTemplErrCntByQuery(), interfaces);
    }

    private Map<String, List<InterfaceData>> getErrStats(String query, String[] interfaces) {
        Map<String, List<InterfaceData>> map = new LinkedHashMap<>();
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                int hour = rs.getInt("h");
                List<InterfaceData> list = new ArrayList<>();

                for (String bpInterface : interfaces) {
                    InterfaceData data = new InterfaceData(
                            bpInterface,
                            getInt(rs, bpInterface + "_c"),
                            getInt(rs, bpInterface + "_err_4_cnt"),
                            getInt(rs, bpInterface + "_err_5_cnt"));
                    list.add(data);
                }
                String period = hour + ":00";
                map.put(period, list);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    public Map<SessionType, List<BpSession>> getSessionsWithoutDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return getSessions(bpInterface, dateFrom, dateTo, complexPKey, WITHOUT_DEBT);
    }

    public Map<SessionType, List<BpSession>> getSessionsWithDebt(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey) {
        return getSessions(bpInterface, dateFrom, dateTo, complexPKey, WITH_DEBT);
    }

    private Map<SessionType, List<BpSession>> getSessions(String bpInterface, LocalDateTime dateFrom, LocalDateTime dateTo, int complexPKey, Integer debt) {
        Map<SessionType, List<BpSession>> map = new LinkedHashMap<>();
        String query = IqQueryBuilder.getSessionByInterfaceQuery();
        try {
            ResultSet rs = getRSByCallStmt(query, bpInterface, convertForSession(dateFrom), convertForSession(dateTo), complexPKey, debt);
            while (rs.next()) {
                BpSession session = new BpSession(
                        rs.getString("session_id"),
                        rs.getInt("duration"),
                        rs.getInt("res"));
                List<BpSession> bpSessions = map.get(session.getSessionType());
                if (bpSessions == null) {
                    bpSessions = new ArrayList<>();
                    map.put(session.getSessionType(), bpSessions);
                }
                bpSessions.add(session);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return map;
    }

    private String convertForSession(LocalDateTime date) {
        return date.toString(SESSION_DATE_PATTERN);
    }

    public TableDataHolder getSessionDetails(String sessionId, int complexPKey) {
        TableDataHolder tableDataHolder = new TableDataHolder();
        String query = IqQueryBuilder.getDataDetailsBySessionId();
        try {
            ResultSet rs = getRSByCallStmt(query, sessionId, complexPKey);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCnt = rsmd.getColumnCount();
            String[] columnNames = new String[columnCnt];
            List<Object[]> rows = new ArrayList<Object[]>();
            // index for table "duration" column (used for putting Integer into table data holder, needed for proper table sorting)
            // initially set to negative value
            int durationColIdx = -1;
            for (int i = 0; i < columnCnt; i++) {
                columnNames[i] = rsmd.getColumnName(i + 1);
                // if column name is "duration" save column's index
                if (columnNames[i].equals("duration")) {
                    durationColIdx = i;
                }
            }
            while (rs.next()) {
                Object[] row = new Object[columnCnt];
                for (int i = 0; i < row.length; i++) {
                    // if duration column index was saved get Integer from result set and put it to current table row
                    if (i == durationColIdx) {
                        row[i] = rs.getInt(i + 1);
                    }
                    // else put String to current table row
                    else {
                        row[i] = rs.getString(i + 1);
                    }
                }
                rows.add(row);
            }
            tableDataHolder = new TableDataHolder(columnNames, rows);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return tableDataHolder;
    }

    public List<ErrorData> getErrTotal(Integer pkeyComplex, Integer bpInterface, Calendar calendar, int endDay,
                                       int top, ErrorsTypeI metric, Integer groupId,
                                       Integer responsibleId, Integer consumerId, Integer companyId) {
        if (pkeyComplex == null) {
            throw new IllegalArgumentException("pkeyComplex is null/empty");
        }
        if (metric == null) {
            throw new IllegalArgumentException("metric is null");
        }
        String query;
        Object[] parameters;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (ErrorsType.INPUT_USER.equals(metric)) {
            query = IqQueryBuilder.getUserErrTotal();
            parameters = new Object[]{pkeyComplex, bpInterface, year, month, startDay, endDay,
                    top, groupId, responsibleId, consumerId};
        } else {
            query = IqQueryBuilder.getErrTotal();
            Object metricId = ErrorsType.RECIPIENT.equals(metric) ? null : metric.getId();
            parameters = new Object[]{pkeyComplex, bpInterface, year, month, startDay, endDay,
                    top, metricId, groupId, responsibleId, consumerId, companyId};
        }
        return getErrTotal(query, parameters);
    }

    private List<ErrorData> getErrTotal(String query, Object[] params) {
        List<ErrorData> errors = new ArrayList<>();
        try {
            ResultSet rs = getRSByCallStmt(query, params);
            while (rs.next()) {
                String errorCode = rs.getString(1);
                int cnt = rs.getInt("cnt");
                errors.add(new ErrorData(errorCode, cnt));
            }

        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return errors;
    }

    public Map<String, Integer> getCompanyByErrTotal(ErrorsTypeI metric, Integer pkeyComplex, Integer bpInterface,
                                                     Calendar calendar, int endDay, int top,
                                                     String errorCode, Integer consumerId) {
        if (metric == null) {
            throw new IllegalArgumentException("metric is null");
        }
        if (pkeyComplex == null) {
            throw new IllegalArgumentException("pkeyComplex is null");
        }
        String query;
        Object[] parameters;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (ErrorsType.INPUT_USER.equals(metric)) {
            query = IqQueryBuilder.getUserCompanyByErrTotal();
        } else {
            query = IqQueryBuilder.getCompanyByErrTotal();
        }
        parameters = new Object[]{pkeyComplex, bpInterface, year, month, startDay, endDay, top, errorCode, consumerId};
        return getCompanyByErrTotalExec(query, parameters);
    }

    private Map<String, Integer> getCompanyByErrTotalExec(String query, Object[] params) {
        Map<String, Integer> companyIds = new LinkedHashMap<String, Integer>();
        try {
            ResultSet rs = getRSByCallStmt(query, params);
            while (rs.next()) {
                String companyId = rs.getString("company_id");
                int count = rs.getInt("cnt");
                companyIds.put(companyId, count);
            }
        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return companyIds;
    }

    public List<String> getSessionByCompanyErrTotal(ErrorsTypeI metric, Integer complexId, Integer bpInterface,
                                                    Calendar calendar, int endDay, String errorCode,
                                                    String companyId, Integer consumerId) {
        if (metric == null) {
            throw new IllegalArgumentException("metric is null");
        }
        String query;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (ErrorsType.INPUT_USER.equals(metric)) {
            query = IqQueryBuilder.getUserSessionByCompanyErrTotal();
        } else {
            query = IqQueryBuilder.getSessionByCompanyErrTotal();
        }
        Object[] params = new Object[]{complexId, bpInterface, year, month, startDay, endDay, errorCode, companyId, consumerId};
        return getSessionByCompanyErrTotalExec(query, params);
    }

    private List<String> getSessionByCompanyErrTotalExec(String query, Object... params) {
        List<String> sessions = new ArrayList<String>();
        try {
            ResultSet rs = getRSByCallStmt(query, params);
            while (rs.next()) {
                sessions.add(rs.getString("session_id"));
            }
        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return sessions;
    }

    public List<TimingData> getTimingsData(Complex complex, String sessionId) {
        if (complex == null) {
            throw new IllegalArgumentException("complex is null");
        }
        if (StringUtil.isEmptyOrNull(sessionId)) {
            throw new IllegalArgumentException("sessionId is null/empty");
        }
        List<TimingData> timings = new ArrayList<>();
        String query = IqQueryBuilder.getTimingQuery(complex, sessionId);

        try {
            ResultSet rs = getRSByPrepStmt(query);
            while (rs.next()) {
                TimingData timing = new TimingData();
                timing.setLoggerName(rs.getString("logger_name"));
                timing.setInterfaceName(rs.getString("interface_name"));
                timing.setStartTime(rs.getString("start_time"));
                timing.setDuration(rs.getString("duration"));
                timing.setStatus(rs.getString("status"));
                timing.setDopInfo(rs.getString("dop_info"));
                timing.setErrorCode(rs.getString("error_code"));
                timing.setModule(rs.getString("module"));
                timing.setMethod(rs.getString("method"));
                timings.add(timing);
            }
        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return timings;
    }

    public List<SessionErrorsData> getSessionErrors(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sessionId is null/empty");
        }
        List<SessionErrorsData> errors = new ArrayList<>();
        String query = IqQueryBuilder.getWebkasErrorsQuery(sessionId);
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                SessionErrorsData error = new SessionErrorsData(
                        rs.getString("logger_name"),
                        rs.getString("interface_name"),
                        rs.getString("start_time"),
                        rs.getString("module"),
                        rs.getString("error_text"),
                        rs.getString("client_error_code"),
                        rs.getString("text_message"),
                        rs.getString("service_id"),
                        rs.getString("alias"),
                        rs.getString("client_ip_address")
                );
                errors.add(error);
            }

        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return errors;
    }

    public <T> List<T> getSessionsByOuterSession(Complex complex, Complex outerComplex, String sessionId, Class<T> type) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sessionId is null/empty");
        }
        String query = IqQueryBuilder.getSessionByOuterSession();

        try {
            ResultSet rs = getRSByCallStmt(query, complex.getId(), outerComplex.getId(), sessionId);
            return parserSessions(type, rs);
        } catch (SQLException e) {
            log.error(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private <T> List<T> parserSessions(Class<T> type, ResultSet rs) throws SQLException {
        List list = new ArrayList<T>();
        if (type == SessionDebtData.class) {
            while (rs.next()) {
                SessionDebtData data = new SessionDebtData();
                data.setLoggerName(rs.getString("logger_name"));
                data.setId(rs.getString("session_id"));
                data.setInterfaceName(rs.getString("interface_name"));
                data.setStartTime(rs.getString("start_time"));
                data.setStatus(rs.getString("status"));
                data.setModule(rs.getString("module"));
                data.setMethod(rs.getString("method"));
                data.setDopInfo(rs.getString("dop_info"));
                data.setErrorCode(rs.getString("error_code"));
                data.setCompanyId(rs.getString("company_id"));
                data.setPointType(rs.getString("point_type"));
                data.setDebtType(rs.getString("debt_type"));
                list.add(data);
            }
        } else if (type == SessionTempData.class) {
            while (rs.next()) {
                SessionTempData data = new SessionTempData();
                data.setLoggerName(rs.getString("logger_name"));
                data.setId(rs.getString("session_id"));
                data.setInterfaceName(rs.getString("interface_name"));
                data.setStartTime(rs.getString("start_time"));
                data.setStatus(rs.getString("status"));
                data.setHttpStatus(rs.getString("http_status"));
                data.setDuration(rs.getString("duration"));
                data.setModule(rs.getString("module"));
                data.setMethod(rs.getString("method"));
                data.setDopInfo(rs.getString("dop_info"));
                data.setErrorCode(rs.getString("error_code"));
                data.setClientErrorCode(rs.getString("client_error_code"));
                list.add(data);
            }
        }
        return list;
    }

    private Integer getInt(ResultSet rs, String paramName) throws SQLException {
        try {
            return rs.getInt(paramName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    private int toMonthDb(int month) {
        return month + 1;
    }

}