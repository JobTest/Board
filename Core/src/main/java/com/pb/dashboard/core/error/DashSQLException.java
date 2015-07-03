package com.pb.dashboard.core.error;

import java.sql.SQLException;

public class DashSQLException extends SQLException {

    private ErrorCode.SQL sql;
    private String[] params;

    public DashSQLException(SQLException e, ErrorCode.SQL sql) {
        super(e);
        this.sql = sql;
    }

    public DashSQLException(String message, ErrorCode.SQL sql) {
        super(new SQLException(message));
        this.sql = sql;
    }

    public DashSQLException(ErrorCode.SQL sql, String... params) {
        this.params = params;
        this.sql = sql;
    }

    public DashSQLException(ErrorCode.SQL sql) {
        super(sql.getMessage(), "", sql.getCode());
        this.sql = sql;
    }

    public ErrorCode.SQL getSql() {
        return sql;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DashSQLException{");
        sb.append("code=").append(sql.getCode());
        sb.append(" ,message='").append(sql.getMessage());
        if (params != null && params.length != 0) {
            sb.append("' ,params='").append(getParams());
        }
        sb.append("'}");
        return sb.toString();
    }

    public String getParams() {
        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (String param : params) {
                sb.append(param + ',');
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        }
        return "";
    }
}