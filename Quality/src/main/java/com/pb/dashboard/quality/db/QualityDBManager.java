package com.pb.dashboard.quality.db;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.quality.holder.PaymentsQualityItem;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.PaymentType;
import com.pb.dashboard.quality.types.ValueType;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QualityDBManager extends DBManager {

    private static final Logger LOG = Logger.getLogger(QualityDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.DASHBOARD;
    private QueryBuilder queryBuilder = new QueryBuilder();

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public List<PaymentsQualityItem> getItems(Bank bank, ValueType valueType, Channel channel, PaymentType paymentType, String dateFrom, String dateTo) {
        List<PaymentsQualityItem> list = new ArrayList<PaymentsQualityItem>();
        String query = queryBuilder.selectItemsByPeriod(bank, valueType, channel, paymentType, dateFrom, dateTo);
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);//stmt.executeQuery(query);
            while (rs.next()) {
                PaymentsQualityItem item = new PaymentsQualityItem();
                item.setBank(rs.getString(queryBuilder.getBank()));
                item.setChannel(rs.getString(queryBuilder.getChannel()));
                item.setValueType(rs.getString(queryBuilder.getValueType()));
                item.setPaymentType(rs.getString(queryBuilder.getPaymentType()));
                item.setRejectName(rs.getString(queryBuilder.getRejectName()));
                item.setDate(rs.getString(queryBuilder.getDate()));
                item.setValue(rs.getDouble(queryBuilder.getValue()));
                list.add(item);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return list;
    }

    public Map<String, Double> getValueByDate(Bank bank, ValueType valueType, Channel channel, PaymentType paymentType,
                                              String dateFrom, String dateTo) {
        Map<String, Double> map = new HashMap<String, Double>();
        String query = queryBuilder.selectValueByDate(bank, valueType, channel, paymentType, dateFrom, dateTo);
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                if (rs.getString(queryBuilder.getTotalValue()) != null) {
                    String date = rs.getString(queryBuilder.getDate());
                    double value = Double.parseDouble(rs.getString(queryBuilder.getTotalValue()));
                    map.put(date, value);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return map;
    }

    public Map<String, Double> getTotalValueByChannel(Bank bank, ValueType valueType, Channel channel,
                                                      String dateFrom, String dateTo) {
        Map<String, Double> map = new HashMap<String, Double>();
        String query = queryBuilder.selectTotalValueByChannel(bank, valueType, channel, dateFrom, dateTo);
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                if (rs.getString(queryBuilder.getTotalValue()) != null) {
                    String date = rs.getString(queryBuilder.getDate());
                    double value = Double.parseDouble(rs.getString(queryBuilder.getTotalValue()));
                    map.put(date, value);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return map;
    }

    public double getAvgProcessingTime(Bank bank, ValueType valueType, Channel channel, PaymentType paymentType,
                                       String dateFrom, String dateTo) {
        String query = queryBuilder.selectAvgProcessingTime(bank, valueType, channel, paymentType, dateFrom, dateTo);
        double value = 0;
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            if (rs.next()) {
                if (rs.getString(queryBuilder.getAvgTime()) != null)
                    value = Double.parseDouble(rs.getString(queryBuilder.getAvgTime()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return value;
    }

    public double getSumValue(Bank bank, ValueType valueType, Channel channel, PaymentType paymentType,
                              String dateFrom, String dateTo) {
        String query = queryBuilder.selectSumValue(bank, valueType, channel, paymentType, dateFrom, dateTo);
        double value = 0;
        ResultSet rs= null;
        try {
            rs = getRSByStmt(query);
            if (rs.next()) {
                if (rs.getString(queryBuilder.getTotalValue()) != null)
                    value = Double.parseDouble(rs.getString(queryBuilder.getTotalValue()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return value;
    }
}