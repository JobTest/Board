package com.pb.dashboard.tickets.logic;

import com.pb.dashboard.core.db.DAOException;
import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.core.util.LogUtil;
import com.pb.dashboard.tickets.db.TicketsDBManager;
import com.pb.dashboard.tickets.entype.Branch;
import com.pb.dashboard.tickets.entype.ChannelOutput;
import com.pb.dashboard.tickets.entype.TicketType;
import com.pb.dashboard.tickets.view.chart.BranchChartDataHolder;
import com.pb.dashboard.tickets.view.chart.ChannelChartDataHolder;
import com.pb.dashboard.tickets.view.chart.DynamicsChartDataHolder;
import com.pb.dashboard.tickets.view.statspanel.StatsPanelDataHolder;
import com.pb.dashboard.tickets.view.table.TableDataHolder;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;

public class DataManager {

    private final Logger log = Logger.getLogger(getClass());

    private TicketsViewModel model = new TicketsViewModel();

    private FilterModel filter;

    public void update(TicketType ticketType, Bank bank, int year, Month month, ChannelOutput branchChannel) {
        filter = new FilterModel(ticketType, bank, year, month, branchChannel);
        updateChannelChartData();
        updateBranchChartData();
        updateTableAndStatsPanelData();
        if (filter.getMonth() == Month.YEAR) updateDynamicsChartData();
    }

    public ChannelChartDataHolder getChannelChartData() {
        return model.getChannelChartDataHolder();
    }

    public BranchChartDataHolder getBranchChartData() {
        return model.getBranchChartDataHolder();
    }

    public TableDataHolder getTableData() {
        return model.getTableDataHolder();
    }

    public StatsPanelDataHolder getStatsPanelData() {
        return model.getStatsPanelDataHolder();
    }

    public DynamicsChartDataHolder getDynamicsChartData() {
        return model.getDynamicsChartDataHolder();
    }

    public void updateByBranchChannel(ChannelOutput channel) {
        filter.setBranchChannel(channel);
        updateBranchChartData();
    }

    private void updateChannelChartData() {
        Map<ChannelOutput, Integer> dbResult = null;
        try {
            dbResult = TicketsDBManager.getInstance().getSlsCntByChannel(
                    filter.getYear(), filter.getMonth().getNumber(),
                    filter.getBank(), filter.getTicketType());
        } catch (DAOException e) {
            log.error(LogUtil.stackTraceToString(e));
        }
        ChannelChartDataHolder channelChartDataHolder;
        if (isValidMap(dbResult))
            channelChartDataHolder = new ChannelChartDataHolder(dbResult); // init with repository results
        else channelChartDataHolder = new ChannelChartDataHolder(); // init empty
        model.setChannelChartDataHolder(channelChartDataHolder);
    }

    private void updateTableAndStatsPanelData() {
        Map<TicketType, BigDecimal[]> dbResult = null;
        try {
            dbResult = TicketsDBManager.getInstance().getStatsByTicketType(
                    filter.getYear(), filter.getMonth().getNumber(), filter.getBank());
        } catch (DAOException e) {
            log.error(LogUtil.stackTraceToString(e));
        }
        TableDataHolder tableDataHolder;
        if (isValidMap(dbResult)) tableDataHolder = new TableDataHolder(dbResult); // init with repository results
        else tableDataHolder = new TableDataHolder(); // init empty
        model.setTableDataHolder(tableDataHolder);
        Object[] statsPanelData = null;
        // if filter ticket type = ALL choose footer from table data holder
        if (filter.getTicketType() == TicketType.ALL) statsPanelData = tableDataHolder.getFooter();
            // else find the row for specific ticket type
        else {
            for (Object[] row : tableDataHolder.getRows()) {
                if (row[0].equals(filter.getTicketType().getName())) {
                    statsPanelData = row;
                    break;
                }
            }
        }
        if (statsPanelData != null) model.setStatsPanelDataHolder(new StatsPanelDataHolder(statsPanelData));
    }

    private void updateBranchChartData() {
        Map<Branch, Integer> dbResult = null;
        try {
            dbResult = TicketsDBManager.getInstance().getBranchSlsCnt(filter.getYear(),
                    filter.getMonth().getNumber(), filter.getBank(),
                    filter.getBranchChannel(), filter.getTicketType());
        } catch (DAOException e) {
            log.error(LogUtil.stackTraceToString(e));
        }
        BranchChartDataHolder branchChartDataHolder;
        if (isValidMap(dbResult)) branchChartDataHolder = new BranchChartDataHolder(dbResult);
        else branchChartDataHolder = new BranchChartDataHolder();
        model.setBranchChartDataHolder(branchChartDataHolder);
    }

    private void updateDynamicsChartData() {
        Map<Integer, Integer>[] dbResult = null;
        try {
            dbResult = TicketsDBManager.getInstance().getYearlySlsCntByMonth(
                    filter.getYear(), filter.getBank(), filter.getTicketType());
        } catch (DAOException e) {
            log.error(LogUtil.stackTraceToString(e));
        }
        int[] years = new int[2];
        //TODO change period
        years[0] = filter.getYear();
        years[1] = filter.getYear() - 1;
        DynamicsChartDataHolder dynamicsChartDataHolder;
        if (isValidMapsArray(dbResult)) dynamicsChartDataHolder = new DynamicsChartDataHolder(years, dbResult);
        else dynamicsChartDataHolder = new DynamicsChartDataHolder(years);
        model.setDynamicsChartDataHolder(dynamicsChartDataHolder);
    }

    private boolean isValidMapsArray(Map<Integer, Integer>[] maps) {
        return isValidMap(maps[0]) && isValidMap(maps[1]);
    }

    private boolean isValidMap(Map map) {
        return map != null && map.size() > 0;
    }

}
