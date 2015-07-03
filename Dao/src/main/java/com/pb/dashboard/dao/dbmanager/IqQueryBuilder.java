package com.pb.dashboard.dao.dbmanager;

import com.pb.dashboard.core.model.Complex;

public final class IqQueryBuilder {

    private IqQueryBuilder() {
    }

    /**
     * Get query sessions by Interface
     * <p/>
     * interface name. Example: "searchPS".
     * date from, in format yyyy-MM-dd_mm:dd. Example: "2014-10-12_09:00".
     * date to, in format yyyy-MM-dd_mm:dd. Example: "2014-10-12_09:10".
     * complex pkey
     *
     * @return list Sessions
     * @see com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI
     * @see com.pb.dashboard.core.model.Complex
     * @see com.pb.dashboard.dao.entity.biplanesupport.db.BpSession
     */
    public static String getSessionByInterfaceQuery() {
        return "call bpl.metric_GetDataByInterface(?,?,?,?,?)";
    }

    /**
     * Get query session details
     * <p/>
     * session id. Example: "kas02_n6.fb2b3d57c0199d".
     * complex pkey
     *
     * @return list Sessions
     * @see com.pb.dashboard.core.model.Complex
     * @see com.pb.dashboard.dao.entity.biplanesupport.db.BpSession
     */
    public static String getDataDetailsBySessionId() {
        return "call bpl.metric_GetDataBySessionID(?,?)";
    }

    public static String getQueryErrCnt() {
        return "call bpl.db_system_GetQueryErrCnt(?,?,?,?,?,?,?)";
    }

    public static String getDbSystemGetSessionByErrQuery() {
        return "call bpl.db_system_GetSessionByErrQuery(?,?,?,?,?,?,?,?,?)";
    }

    public static String getErrCntByQuery() {
        return "call bpl.GetErrCntByQuery()";
    }

    public static String getTemplErrCntByQuery() {
        return "call bpl.GetTemplErrCntByQuery()";
    }

    public static String getSystemGetErrCntByQuery() {
        return "call bpl.db_system_GetErrCntByQuery(?,?,?,?,?,?,?,?,?)";
    }

    public static String getUserErrTotal() {
        return "call bpl.db_GetUserErrTotal(?,?,?,?,?,?,?,?,?,?)";
    }

    public static String getErrTotal() {
        return "call bpl.db_GetErrTotal(?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    public static String getUserCompanyByErrTotal() {
        return "call bpl.db_GetUserCompanyByErrTotal(?,?,?,?,?,?,?,?,?)";
    }

    public static String getCompanyByErrTotal() {
        return "call bpl.db_GetCompanyByErrTotal(?,?,?,?,?,?,?,?,?)";
    }

    public static String getUserSessionByCompanyErrTotal() {
        return "call bpl.db_GetUserSessionByCompanyErrTotal(?,?,?,?,?,?,?,?,?)";
    }

    public static String getSessionByCompanyErrTotal() {
        return "call bpl.db_GetSessionByCompanyErrTotal(?,?,?,?,?,?,?,?,?)";
    }

    public static String getTimingQuery(Complex complex, String sessionId) {
        String query = "select logger_name, interface_name, start_time, duration, status,dop_info, error_code, module, method from %s where session_id = '%s'";
        return String.format(query, getTableDba(complex), sessionId);
    }

    private static String getTableDba(Complex complex) {
        switch (complex) {
            case BIPLANE_API2X:
                return "dba.webkas_timing";
            case DEBT:
                return "dba.debt_timing";
            case TEMPLATES:
                return "dba.template_timing";
        }
        throw new IllegalArgumentException(complex.getName());
    }

    public static String getWebkasErrorsQuery(String sessionId) {
        String query = "SELECT e.logger_name,e.interface_name,e.start_time,e.module,e.error_text,e.client_error_code,e.text_message,e.service_id,e.alias,e.client_ip_address from dba.webkas_errors e where session_id='%s'";
        return String.format(query, sessionId);
    }

    public static String getSessionByOuterSession() {
        return "call bpl.db_GetSessionsByOuterSession(?,?,?)";
    }
}