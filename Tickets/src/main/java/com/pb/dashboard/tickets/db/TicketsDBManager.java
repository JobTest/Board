package com.pb.dashboard.tickets.db;

import com.pb.dashboard.core.db.DAOException;
import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.tickets.entype.Branch;
import com.pb.dashboard.tickets.entype.ChannelOutput;
import com.pb.dashboard.tickets.entype.TicketType;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TicketsDBManager extends DBManager {

    private static final Logger log = Logger.getLogger(TicketsDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.DASHBOARD;
    private static TicketsDBManager instance;

    private TicketsDBManager() {
    }

    public static TicketsDBManager getInstance() {
        if (instance == null) {
            instance = new TicketsDBManager();
        }
        return instance;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public Map<Integer, ChannelOutput> getChannels() {
        Map<Integer, ChannelOutput> channels = new HashMap<Integer, ChannelOutput>();
        String query = QueryBuilder.getChannelsOutputQuery();
        try {
            ResultSet rs = getRSByPrepStmt(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                channels.put(id, new ChannelOutput(id, name));
            }
        } catch (SQLException e) {
            log.error("Channels not found", e);
        }
        return channels;
    }

    public Map<ChannelOutput, Integer> getSlsCntByChannel(int year, Integer month, Bank bank, TicketType ticketType) throws DAOException {
        if (month < 0 || month > 12) {
            throw new DAOException("Month is not included [0..12]. Month: " + month);
        }
        if (bank == null) {
            throw new DAOException("bank is null");
        }
        if (ticketType == null) {
            throw new DAOException("ticketType is null");
        }
        String query = QueryBuilder.getCountSalesByChannelOutput();
        if (!isMonth(month)) {
            month = null;
        }
        Integer ticketId = ticketType.getId() == 0 ? null : ticketType.getId();
        try {
            ResultSet rs = getRSByPrepStmt(query, year, month, bank.getAttrName(), ticketId);
            return getSlsCntByChannelMap(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Map<ChannelOutput, Integer> getSlsCntByChannelMap(ResultSet rs) throws SQLException {
        Map<Integer, ChannelOutput> channels = getChannels();
        Map<ChannelOutput, Integer> map = new LinkedHashMap<ChannelOutput, Integer>();
        while (rs.next()) {
            int pkey = rs.getInt(1);
            Double value = rs.getDouble(2);
            ChannelOutput channelOutput = channels.get(pkey);
            map.put(channelOutput, value.intValue());
        }
        return map;
    }

    public Map<TicketType, BigDecimal[]> getStatsByTicketType(int year, Integer month, Bank bank) throws DAOException {
        if (month < 0 || month > 12) {
            throw new DAOException("Month is not included [0..12]. Month: " + month);
        }
        if (bank == null) {
            throw new DAOException("bank is null");
        }
        String query = QueryBuilder.getCountSalesByTicketType();
        if (!isMonth(month)) {
            month = null;
        }
        try {
            ResultSet rs = getRSByPrepStmt(query, year, month, bank.getAttrName());
            return getStatsByTicketTypeMap(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Map<Branch, Integer> getBranchSlsCnt(int year, Integer month, Bank bank, ChannelOutput channel, TicketType ticketType) throws DAOException {
        if (month < 0 || month > 12) {
            throw new DAOException("Month is not included [0..12]. Month: " + month);
        }
        if (bank == null) {
            throw new DAOException("bank is null");
        }
        if (ticketType == null) {
            throw new DAOException("ticketType is null");
        }
        String query = QueryBuilder.getCountSalesByBranch();
        if (!isMonth(month)) {
            month = null;
        }
        Integer ticketId = ticketType.getId() == 0 ? null : ticketType.getId();
        Integer channelId = channel == null ? null : channel.getPkey();
        try {
            ResultSet rs = getRSByPrepStmt(query, year, month, bank.getAttrName(), ticketId, channelId);
            return getBranchSlsCntMap(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Map[] getYearlySlsCntByMonth(int year, Bank bank, TicketType ticketType) throws DAOException {
        if (bank == null) {
            throw new DAOException("bank is null");
        }
        if (ticketType == null) {
            throw new DAOException("ticketType is null");
        }
        String query = QueryBuilder.getCountSalesByMonth();
        Map[] maps = new LinkedHashMap[2]; // maps for current and previous year
        ResultSet rs = null;
        Integer ticketId = ticketType.getId() == 0 ? null : ticketType.getId();
        try {
            rs = getRSByPrepStmt(query, year, bank.getAttrName(), ticketId);
            maps[0] = getYearlySlsCntByMonthMap(rs);

            rs = getRSByPrepStmt(query, year - 1, bank.getAttrName(), ticketId);
            maps[1] = getYearlySlsCntByMonthMap(rs);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return maps;
    }

    private Map<TicketType, BigDecimal[]> getStatsByTicketTypeMap(ResultSet rs) throws SQLException {
        Map<TicketType, BigDecimal[]> map = new LinkedHashMap<TicketType, BigDecimal[]>();
        while (rs.next()) {
            TicketType type = TicketType.idToTicketType(rs.getInt(1));
            BigDecimal[] values = new BigDecimal[3]; // values for sales_cnt, revenue, turnover
            values[0] = rs.getBigDecimal(2); // sales_cnt
            values[1] = rs.getBigDecimal(3); // revenue
            values[2] = rs.getBigDecimal(4); // turnover
            map.put(type, values);
        }
        return map;
    }

    private Map<Branch, Integer> getBranchSlsCntMap(ResultSet rs) throws SQLException {
        Map<Branch, Integer> map = new LinkedHashMap<Branch, Integer>();
        while (rs.next()) {
            Branch branch = Branch.attrIdToBranch(rs.getString(1));
            Double value = rs.getDouble(2);
            map.put(branch, value.intValue());
        }
        return map;
    }

    private Map<Integer, Integer> getYearlySlsCntByMonthMap(ResultSet rs) throws SQLException {
        Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
        // init map with zero val for each of 12 months
        for (int i = 1; i <= 12; i++) map.put(i, 0);
        while (rs.next()) {
            Integer month = rs.getInt(1);
            Double value = rs.getDouble(2);
            map.put(month, value.intValue());
        }
        return map;
    }

    private boolean isMonth(int numberMonth) {
        return Month.YEAR.getNumber() != numberMonth;
    }
}
