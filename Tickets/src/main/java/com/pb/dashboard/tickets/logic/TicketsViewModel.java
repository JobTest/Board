package com.pb.dashboard.tickets.logic;

import com.pb.dashboard.tickets.view.chart.BranchChartDataHolder;
import com.pb.dashboard.tickets.view.chart.ChannelChartDataHolder;
import com.pb.dashboard.tickets.view.chart.DynamicsChartDataHolder;
import com.pb.dashboard.tickets.view.statspanel.StatsPanelDataHolder;
import com.pb.dashboard.tickets.view.table.TableDataHolder;

public class TicketsViewModel {

    private ChannelChartDataHolder channelChartDataHolder;
    private BranchChartDataHolder branchChartDataHolder;
    private DynamicsChartDataHolder dynamicsChartDataHolder;
    private TableDataHolder tableDataHolder;
    private StatsPanelDataHolder statsPanelDataHolder;

    public ChannelChartDataHolder getChannelChartDataHolder() {
        return channelChartDataHolder;
    }

    public void setChannelChartDataHolder(ChannelChartDataHolder channelChartDataHolder) {
        this.channelChartDataHolder = channelChartDataHolder;
    }

    public TableDataHolder getTableDataHolder() {
        return tableDataHolder;
    }

    public void setTableDataHolder(TableDataHolder tableDataHolder) {
        this.tableDataHolder = tableDataHolder;
    }

    public BranchChartDataHolder getBranchChartDataHolder() {
        return branchChartDataHolder;
    }

    public void setBranchChartDataHolder(BranchChartDataHolder branchChartDataHolder) {
        this.branchChartDataHolder = branchChartDataHolder;
    }

    public StatsPanelDataHolder getStatsPanelDataHolder() {
        return statsPanelDataHolder;
    }

    public void setStatsPanelDataHolder(StatsPanelDataHolder statsPanelDataHolder) {
        this.statsPanelDataHolder = statsPanelDataHolder;
    }

    public DynamicsChartDataHolder getDynamicsChartDataHolder() {
        return dynamicsChartDataHolder;
    }

    public void setDynamicsChartDataHolder(DynamicsChartDataHolder dynamicsChartDataHolder) {
        this.dynamicsChartDataHolder = dynamicsChartDataHolder;
    }
}
