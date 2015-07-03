package com.pb.dashboard.quality.view.tabsheet;

import com.pb.dashboard.quality.chart.ChartBuilder;
import com.pb.dashboard.quality.chart.ChartDataHolder;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.view.table.RPTableBuilder;
import com.pb.dashboard.quality.view.table.RPTableDataHolder;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class RPTabsheetBuilder {

    private ChartBuilder chartBuilder = new ChartBuilder();
    private RPTableBuilder RPTableBuilder = new RPTableBuilder();

    public static final String[] p24Names = {"Все платежи", "Мобильная связь", "Биплан", "P2P платежи"};
    public static final String[] otdAnd3700Names = {"Экспресс платежи"};
    public static final String[] biplaneNames = {"Выручка", "Платежи физлиц"};
    public static final String[] terminalNames = {"Все платежи", "Мобильная связь", "Биплан"};

    public TabSheet getTabSheet(Channel channel, RPTabsheetDataHolder holder) {
        if (channel == Channel.P24) return buildTabSheet(holder, p24Names);
        else if (channel == Channel.OFFICE || channel == Channel.L3700) return buildTabSheet(holder, otdAnd3700Names);
        else if (channel == Channel.BIPLAN) return buildTabSheet(holder, biplaneNames);
        else return buildTabSheet(holder, terminalNames);
    }

    private TabSheet buildTabSheet(RPTabsheetDataHolder holder, String[] names) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName("quality");
        for (int i = 0; i < names.length; i++) {
            VerticalLayout contentHolder = new VerticalLayout();
            contentHolder.addStyleName("tab-content-holder");
            contentHolder.setSpacing(true);
            ChartDataHolder chartDataHolder = holder.getChartPercentage().get(i);
            RPTableDataHolder RPTableDataHolder = holder.getRPTableDataHolder().get(i);
            contentHolder.addComponent(chartBuilder.getTabSheetChart(names[i], chartDataHolder));
            contentHolder.addComponent(RPTableBuilder.buildTable(RPTableDataHolder));
            tabSheet.addTab(contentHolder, names[i]);
        }

        return tabSheet;
    }

}
