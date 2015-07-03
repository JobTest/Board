package com.pb.dashboard.tickets.view;

import com.pb.dashboard.core.model.Bank;
import com.pb.dashboard.core.model.Month;
import com.pb.dashboard.tickets.db.TicketsDBManager;
import com.pb.dashboard.tickets.entype.ChannelOutput;
import com.pb.dashboard.tickets.entype.SalesDynamicsType;
import com.pb.dashboard.tickets.entype.TicketType;
import com.pb.dashboard.tickets.logic.DataManager;
import com.pb.dashboard.tickets.view.chart.BranchChart;
import com.pb.dashboard.tickets.view.chart.ChannelChart;
import com.pb.dashboard.tickets.view.chart.DynamicsChart;
import com.pb.dashboard.tickets.view.statspanel.StatsPanel;
import com.pb.dashboard.tickets.view.table.TicketTypeTable;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

public class PanelManager implements Filterable, Serializable {

    private static ChannelOutput DEFAULT_CHANNEL = new ChannelOutput(null, "Всего");
    private DataManager dataManager = new DataManager();
    private CssLayout topChartPanel;
    private ChannelChart channelChart = new ChannelChart();
    private BranchChart branchChart = new BranchChart();
    private DynamicsChart dynamicsChart = new DynamicsChart();
    private TicketTypeTable table = new TicketTypeTable();
    private StatsPanel statsPanel = new StatsPanel();

    private Component topPanelContent;

    private final ComboBox branchChannelMenu = new ComboBox();
    private final ComboBox chartTypeMenu = new ComboBox();

    private boolean dynamicsChartEnabled;

    public Component getFilterPanel() {
        return new FilterPanel(this);
    }

    public Component getStatsPanel() {
        return statsPanel;
    }

    public Component getTopChartPanel() {
        topPanelContent = channelChart;
        topChartPanel = createPanel(topPanelContent, getTopChartComboBox(), "Динамика продаж, шт");
        return topChartPanel;
    }

    public Component getBottomChartPanel() {
        return createPanel(branchChart, getBottomChartComboBox(), "Распределение продаж по РП");
    }

    private ComboBox getTopChartComboBox() {
        for (SalesDynamicsType type : SalesDynamicsType.values()) {
            if (type == SalesDynamicsType.DYNAMICS) continue; // Exclude dynamics chart by default
            chartTypeMenu.addItem(type);
            chartTypeMenu.setItemCaption(type, type.getName());
        }
        chartTypeMenu.setNullSelectionAllowed(false);
        chartTypeMenu.setValue(SalesDynamicsType.BY_CHANNEL);
        chartTypeMenu.setImmediate(true);

        chartTypeMenu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent event) {
                SalesDynamicsType dynamicsType = (SalesDynamicsType) chartTypeMenu.getValue();
                updateTopPanelContent(dynamicsType);
            }
        });
        return chartTypeMenu;
    }

    private ComboBox getBottomChartComboBox() {
        Map<Integer, ChannelOutput> channels = TicketsDBManager.getInstance().getChannels();
        channels.put(DEFAULT_CHANNEL.getPkey(), DEFAULT_CHANNEL);
        for (ChannelOutput channel : channels.values()) {
            branchChannelMenu.addItem(channel);
            branchChannelMenu.setItemCaption(channel, channel.getName());
        }
        branchChannelMenu.setNullSelectionAllowed(false);
        if (!channels.isEmpty()) {
            branchChannelMenu.setValue(DEFAULT_CHANNEL);
        }
        branchChannelMenu.setImmediate(true);

        branchChannelMenu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent event) {
                ChannelOutput channel = (ChannelOutput) branchChannelMenu.getValue();
                updateBranchChart(channel);
            }
        });
        return branchChannelMenu;
    }

    private CssLayout createPanel(Component content, ComboBox menu, String title) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.addComponent(content);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("configure");
        horizontalLayout.addComponent(menu);
        panel.addComponent(horizontalLayout);
        content.setCaption(title);
        return panel;
    }

    private void addDynamicsChart() {
        if (!dynamicsChartEnabled) {
            SalesDynamicsType type = SalesDynamicsType.DYNAMICS;
            chartTypeMenu.addItem(type);
            chartTypeMenu.setItemCaption(type, type.getName());
            dynamicsChartEnabled = true;
        }
    }

    private void removeDynamicsChart() {
        if (dynamicsChartEnabled) {
            chartTypeMenu.setValue(SalesDynamicsType.BY_CHANNEL);
            switchComponents(channelChart);
            chartTypeMenu.removeItem(SalesDynamicsType.DYNAMICS);
            dynamicsChartEnabled = false;
        }
    }

    private void updateTopPanelContent(SalesDynamicsType dynamicsType) {
        if (dynamicsType == SalesDynamicsType.BY_CHANNEL) switchComponents(channelChart);
        else if (dynamicsType == SalesDynamicsType.BY_TICKET_TYPE) switchComponents(table);
        else if (dynamicsType == SalesDynamicsType.DYNAMICS) switchComponents(dynamicsChart);
    }

    private void switchComponents(Component newComponent) {
        topChartPanel.removeComponent(topPanelContent);
        topPanelContent = newComponent;
        topChartPanel.addComponent(topPanelContent);
    }

    private void updateBranchChart(ChannelOutput channel) {
        dataManager.updateByBranchChannel(channel);
        branchChart.setSeries(dataManager.getBranchChartData());
    }

    public void init() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        filterUpdated(TicketType.ALL, Bank.UKRAINE, year, Month.YEAR);
    }

    @Override
    public void filterUpdated(TicketType type, Bank bank, int year, Month month) {
        ChannelOutput channel = (ChannelOutput) branchChannelMenu.getValue();
        dataManager.update(type, bank, year, month, channel);
        if (month == Month.YEAR) {
            addDynamicsChart();
            dynamicsChart.setSeries(dataManager.getDynamicsChartData());
        } else removeDynamicsChart();
        channelChart.setSeries(dataManager.getChannelChartData());
        branchChart.setSeries(dataManager.getBranchChartData());
        table.fillTable(dataManager.getTableData());
        statsPanel.setData(dataManager.getStatsPanelData());
    }

}
