package com.pb.dashboard.vitrina.core.db;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BiplaneDBManager extends DBManager implements Serializable {

    private static final Logger LOG = Logger.getLogger(BiplaneDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.REDMINE;
    private QueryBuilder queryBuilder = new QueryBuilder();
    private SimpleDateFormat sdf = new SimpleDateFormat("k");

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public Integer[] getHourlyThreshold(String lang) {
        Integer[] thresholds = new Integer[4];
        int currentHour = 1 + Integer.parseInt(sdf.format(new Date()));
        String query = queryBuilder.selectThreshold(lang, currentHour);
        LOG.debug("Query: " + query);
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            int i = 0;
            while (rs.next()) {
                thresholds[i] = rs.getInt(queryBuilder.getThresholdValue());
                i++;
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        return thresholds;
    }
}