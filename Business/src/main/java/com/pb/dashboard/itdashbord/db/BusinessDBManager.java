package com.pb.dashboard.itdashbord.db;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusinessDBManager extends DBManager {

    private static final Logger LOG = Logger.getLogger(BusinessDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.DASHBOARD;
    private QueryBuilder qb = new QueryBuilder();

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public Object[] getAuthorizationData() {
        ArrayList<String[]> rows = new ArrayList<String[]>();
        String query = qb.selectAuthStatsAll();
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                String[] row = new String[qb.getCoulmnCnt() + 2]; // 2 slots for percentages
                int total = rs.getInt(qb.getTotal());
                int noAuth = rs.getInt(qb.getNoAuth());
                int phone = rs.getInt(qb.getPhone());
                int noAuthPercent = (int) ((double) noAuth / total * 100);
                int phonePercent = (int) ((double) phone / total * 100);
                row[0] = rs.getString(qb.getDate()).replaceAll("-", ".");
                row[1] = Integer.toString(total);
                row[2] = rs.getString(qb.getQrCode());
                row[3] = Integer.toString(noAuth);
                row[4] = noAuthPercent + "%";
                row[5] = rs.getString(qb.getCard());
                row[6] = Integer.toString(phone);
                row[7] = phonePercent + "%";

                row[8] = rs.getString(qb.getSms1());
                row[9] = rs.getString(qb.getSms3());
                row[10] = rs.getString(qb.getSms5());
                row[11] = rs.getString(qb.getSms10());

                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows.toArray(new Object[rows.size()]);
    }
}