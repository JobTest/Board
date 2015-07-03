package com.pb.dashboard.monitoring.errors.all.db.pl;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeCustom;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeI;
import com.pb.dashboard.monitoring.errors.all.db.container.InfoError;
import com.pb.dashboard.monitoring.errors.all.db.container.SimpleObject;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PLDBManager extends DBManager {

    private static final Logger log = Logger.getLogger(PLDBManager.class);
    private static final ResourceNames RESOURCE_NAMES = ResourceNames.TRANSCRIPT;

    private static PLDBManager instance;

    public static PLDBManager getInstance() {
        if (instance == null) {
            instance = new PLDBManager();
        }
        return instance;
    }

    @Override
    public ResourceNames getResource() {
        return RESOURCE_NAMES;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    public List<ErrorsTypeI> getTypes() {
        List<ErrorsTypeI> types = new ArrayList<>();
        String query = PLQueryBuilder.getTypesSelect();
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                Integer pkey = rs.getInt("pkey");
                String type = rs.getString("type");
                types.add(new ErrorsTypeCustom(pkey, type));
            }
            return types;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return types;
    }

    public List<SimpleObject> getGroups() {
        List<SimpleObject> groups = new ArrayList<SimpleObject>();
        String query = PLQueryBuilder.getGroupsSelect();
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                groups.add(new SimpleObject(rs.getInt("pkey"), rs.getString("group")));
            }
            return groups;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return groups;
    }

    public List<SimpleObject> getResponsibility() {
        List<SimpleObject> responsibility = new ArrayList<SimpleObject>();
        String query = PLQueryBuilder.getResponsibilitySelect();
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                SimpleObject responsible = new SimpleObject(
                        rs.getInt("pkey"),
                        rs.getString("responsible")
                );
                responsibility.add(responsible);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return responsibility;
    }

    public List<SimpleObject> getConsumers() {
        List<SimpleObject> objects = new ArrayList<SimpleObject>();
        String query = PLQueryBuilder.getConsumersSelect();
        try {
            ResultSet rs = getRSByStmt(query);
            while (rs.next()) {
                objects.add(new SimpleObject(rs.getInt("pkey"), rs.getString("name")));
            }
            return objects;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return objects;
    }
}