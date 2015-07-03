package com.pb.dashboard.monitoring.errors.all.db;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.util.IntegerUtil;
import com.pb.dashboard.monitoring.errors.all.db.container.DescriptionRecipient;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DescriptionDBManager extends DBManager {

    private static final Logger LOG = Logger.getLogger(DescriptionDBManager.class);
    private static final ResourceNames RESOURCE_NAMES = ResourceNames.DESCRIPTION;
    private static DescriptionDBManager instance;

    public static DescriptionDBManager getInstance() {
        if (instance == null) {
            instance = new DescriptionDBManager();
        }
        return instance;
    }

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return RESOURCE_NAMES;
    }

    public DescriptionRecipient getDescriptionRecipient(String companyId) throws DashSQLException {
        String query = "exec dbo.dashboard_GetCompanyName ?";
        Object id = IntegerUtil.checkInt(companyId) ? Integer.parseInt(companyId) : null;
        ResultSet rs = getRSByPrepStmt(query, id);
        try {
            DescriptionRecipient recipient = new DescriptionRecipient();
            if (rs.next()) {
                String companyName = rs.getString("company_name");
                String filialName = rs.getString("filial_name");
                recipient.setCompany(companyName);
                recipient.setFilial(filialName);
            }
            return recipient;
        } catch (SQLException e) {
            throw new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE, e.getMessage());
        }
    }
}