package com.pb.dashboard.external.debtload.repository.jdbc;

import com.pb.dashboard.core.db.DAOException;
import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.external.debtload.model.DebtLinesModel;
import com.pb.dashboard.external.debtload.model.DebtLoadModel;
import com.pb.dashboard.external.debtload.model.DebtQueueModel;
import com.pb.dashboard.external.debtload.repository.LoadItemsDao;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DebtloadDbManager extends DBManager implements LoadItemsDao {

    // Constants-------------------------
    private static final String SQL_SELECT_QITEMS = "SELECT * FROM debtload_queue_items WHERE bank_id = " +
            "(SELECT id FROM bank WHERE attr_id = ?) ORDER BY file_date;";
    private static final String SQL_SELECT_LITEMS_BY_DATE = "SELECT * FROM debtload_items WHERE bank_id = " +
            "(SELECT id FROM bank WHERE attr_id = ?) AND date LIKE ? AND is_manual_load = ? ORDER BY load_start DESC;";
    private static final String SQL_SELECT_MDATE = "SELECT timestamp FROM debtload_ts;";

    private static final String SQL_SELECT_LINES_BY_DATE = "SELECT `load_start`, `lines` FROM debtload_items WHERE bank_id = ? AND `date` BETWEEN ? AND ?  ;";

    private static final ResourceNames DS = ResourceNames.DASHBOARD;

    private static final Logger LOG = Logger.getLogger(DebtloadDbManager.class);

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return DS;
    }

    @Override
    public Collection<DebtLinesModel> getLinesModelByFromDateAndToDate(int bank, String dateFrom, String dateTo) {
        Collection<DebtLinesModel> lines = new ArrayList<DebtLinesModel>();
        ResultSet rs = null;
        try {
            String query = SQL_SELECT_LINES_BY_DATE;
            Object[] params = new Object[]{String.valueOf(bank), dateFrom, dateTo};
            LOG.debug(String.format("Executing query: %s with parameters: %s, %s",SQL_SELECT_LINES_BY_DATE,dateFrom, dateTo));
            rs = getRSByPrepStmt(query, params);
            while (rs.next()){
                DebtLinesModel model = new DebtLinesModel(rs.getString(1), rs.getString(2));
                lines.add(model);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return lines;
    }

    @Override
    public Collection<DebtQueueModel> getQueueModelsByBank(Bank bank) throws DAOException {
        Collection<DebtQueueModel> models = new ArrayList<DebtQueueModel>();
        ResultSet rs = null;
        try {
            String query = SQL_SELECT_QITEMS;
            Object[] params = new Object[]{bank.getAttrName()};
            LOG.debug(String.format("Executing query: %s with parameter: %s", SQL_SELECT_QITEMS, bank.getAttrName()));
            rs = getRSByPrepStmt(query, params);
            while (rs.next()) {
                DebtQueueModel m = new DebtQueueModel();
                m.setBank(bank);
                m.setRecipientId(rs.getInt(3));
                m.setRecipientName(rs.getString(4));
                m.setFileDate(rs.getString(5));
                m.setLoadFull(rs.getInt(6));
                m.setLoadPart(rs.getInt(7));
                m.setLoadMulti(rs.getInt(8));
                m.setLoadMultiPart(rs.getInt(9));
                m.setLoadTest(rs.getInt(10));
                m.setFtpFull(rs.getInt(11));
                m.setFtpPart(rs.getInt(12));
                m.setFtpMulti(rs.getInt(13));
                m.setFtpTest(rs.getInt(14));
                m.setBranch(rs.getString(15));
                m.setSigned(rs.getInt(16) == 1);
                m.setDbPathOk(rs.getInt(17) == 1);
                m.setInList(rs.getInt(18) == 1);
                models.add(m);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return models;
    }

    @Override
    public Collection<DebtLoadModel> getLoadModelsByBankAndDate(Bank bank, String date, boolean isManualLoad) throws DAOException {
        Collection<DebtLoadModel> models = new ArrayList<DebtLoadModel>();
        ResultSet rs = null;
        try {
            String query = SQL_SELECT_LITEMS_BY_DATE;
            Object[] params = new Object[]{bank.getAttrName(), date + "%", isManualLoad ? 1 : 0};
            LOG.debug(String.format("Executing query: %s with parameters: %s, %s", SQL_SELECT_LITEMS_BY_DATE, bank.getAttrName(), date));
            rs = getRSByPrepStmt(query, params);
            models = getRsDebtLoadModels(bank, rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return models;
    }

    @Override
    public String getModifyDate() throws DAOException {
        String mDate = null;
        ResultSet rs = null;
        try {
            LOG.debug(String.format("Executing query: %s", SQL_SELECT_MDATE));
            rs = getRSByStmt(SQL_SELECT_MDATE);
            if (rs.next()) mDate = rs.getString(1);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return mDate;
    }

    private Collection<DebtLoadModel> getRsDebtLoadModels(Bank bank, ResultSet rs) throws SQLException {
        Collection<DebtLoadModel> models = new ArrayList<DebtLoadModel>();
        while (rs.next()) {
            DebtLoadModel m = new DebtLoadModel();
            m.setBank(bank);
            m.setDate(rs.getString(2));
            m.setRecipientId(rs.getInt(3));
            m.setRecipientName(rs.getString(5));
            m.setLoadStartTime(rs.getString(6));
            m.setLoadEndTime(rs.getString(7));
            m.setLinesLoaded(rs.getInt(8));
            m.setDiff(rs.getInt(9));
            m.setType(rs.getString(10));
            m.setDiffLink(rs.getString(11));
            m.setErrorText(rs.getString(12));
            m.setBranch(rs.getString(13));
            models.add(m);
        }
        return models;
    }
}