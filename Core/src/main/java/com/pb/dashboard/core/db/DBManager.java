package com.pb.dashboard.core.db;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.util.TimeUtil;
import org.apache.log4j.Logger;

import java.sql.*;

public abstract class DBManager {

    protected abstract Logger getLog();

    protected abstract ResourceNames getResource();

    protected ResultSet getRSByStmt(String query) throws DashSQLException {
        Connection conn = null;
        try {
            conn = OpenerConnections.getConnection(getResource());
            return execQuery(conn, query);
        } catch (DashSQLException e) {
            showNotification(e, query);
            throw e;
        } finally {
            close(conn);
        }
    }

    protected ResultSet getRSByPrepStmt(String query, Object... params) throws DashSQLException {
        Connection conn = null;
        try {
            conn = OpenerConnections.getConnection(getResource());
            return execQuery(conn, true, query, params);
        } catch (DashSQLException e) {
            showNotification(e, query, params);
            throw e;
        } finally {
            close(conn);
        }
    }

    protected ResultSet getRSByCallStmt(String query, Object... params) throws DashSQLException {
        Connection conn = null;
        try {
            conn = OpenerConnections.getConnection(getResource());
            return execQuery(conn, false, query, params);
        } catch (DashSQLException e) {
            showNotification(e, query, params);
            throw e;
        } finally {
            close(conn);
        }
    }

    private Statement getStatement(Connection conn) throws DashSQLException {
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.STATEMENT_NOT_CREATED, getResource().getName());
        }
    }

    private PreparedStatement getPreparedStatement(Connection conn, String query) throws DashSQLException {
        try {
            return conn.prepareStatement(query);
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.STATEMENT_NOT_CREATED, getResource().getName());
        }
    }

    private CallableStatement getCallableStatement(Connection conn, String query) throws DashSQLException {
        try {
            return conn.prepareCall(query);
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.STATEMENT_NOT_CREATED, getResource().getName());
        }
    }

    private ResultSet execQuery(Connection conn, String query) throws DashSQLException {
        try {
            long t1 = System.nanoTime();
            Statement stmt = getStatement(conn);
            ResultSet rs = stmt.executeQuery(query);
            long millisTime = TimeUtil.nanoToMillis((System.nanoTime() - t1));
            getLog().debug(getLogQuery(query, millisTime));
            return rs;
        } catch (SQLException e) {
            throw new DashSQLException(e, ErrorCode.SQL.ERROR_EXECUTE);
        }
    }

    private ResultSet execQuery(Connection conn, boolean prepared, String query, Object... params) throws DashSQLException {
        PreparedStatement stmt;
        if (prepared) {
            stmt = getPreparedStatement(conn, query);
        } else {
            stmt = getCallableStatement(conn, query);
        }
        int i = 1;
        for (Object param : params) {
            setObject(stmt, i, param);
            i++;
        }
        return executeQuery(query, stmt, params);
    }

    private void setObject(PreparedStatement stmt, int index, Object obj) throws DashSQLException {
        try {
            if (obj != null) {
                stmt.setObject(index, obj);
            } else {
                stmt.setNull(index, Types.INTEGER);
            }
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.INCORRECT_PARAMETERS, "index: " + index + " = " + obj);
        }
    }

    private ResultSet executeQuery(String query, PreparedStatement statement, Object... params) throws DashSQLException {
        try {
            long t1 = System.nanoTime();
            ResultSet rs = statement.executeQuery();
            long time = TimeUtil.nanoToMillis(System.nanoTime() - t1);
            getLog().debug(getLogQuery(query, time, params));
            return rs;
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE, e.getMessage());
        }
    }

    public String getLogQuery(String query, long mills, Object... params) {
        StringBuilder debug = new StringBuilder("Query: ");
        debug.append(query);
        try {
            for (Object p : params) {
                addParam(debug, p);
            }
            debug.append("    Time: ").append(mills).append(" ms.");
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
        }
        return debug.toString();
    }

    private void addParam(StringBuilder sb, Object p) {
        String querySymbol = "?";
        int begin = sb.indexOf(querySymbol);
        if (begin == -1) {
            return;
        }
        int end = begin + querySymbol.length();
        String param = String.valueOf(p);
        if (p instanceof String) {
            sb.replace(begin, end, '\'' + param + '\'');
        } else {
            sb.replace(begin, end, param);
        }
    }

    private void showNotification(DashSQLException e, String query, Object... params) {
        new DBNotification(e, getLogQuery(query, 0, params));
    }

    protected void close(Connection connection) {
        OpenerConnections.close(connection);
    }
}