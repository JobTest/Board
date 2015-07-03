package com.pb.dashboard.core.db;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpenerConnections {

    private final static Logger log = Logger.getLogger(OpenerConnections.class);
    private static Map<ResourceNames, DataSource> dataSourceMap = new LinkedHashMap<ResourceNames, DataSource>();

    private static DataSource createDataSource(ResourceNames database) {
        DataSource ds = null;
        try {
            log.info("DB: " + database.getName() + " ...");
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(database.getName());
            Connection conn = ds.getConnection();
            log.info(" success to " + conn.getMetaData().getURL() + " " + conn.getMetaData().getUserName());
            close(conn);
            ctx.close();
        } catch (NamingException e) {
            log.error(" not found.", e);
        } catch (SQLException e) {
            log.error(" Не удалось установить соединение!", e);
        }
        return ds;
    }

    public static Connection getConnection(ResourceNames resourceNames) throws DashSQLException {
        DataSource dataSource = dataSourceMap.get(resourceNames);
        if (dataSource == null) {
            dataSource = createDataSource(resourceNames);
            dataSourceMap.put(resourceNames, dataSource);
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new DashSQLException(ErrorCode.SQL.CONNECT_NOT_CREATED, resourceNames.getName());
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("Connect не закрылся.", e);
            }
        }
    }
}