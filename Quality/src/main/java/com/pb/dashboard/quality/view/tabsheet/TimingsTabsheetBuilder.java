package com.pb.dashboard.quality.view.tabsheet;

import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.view.table.TimingsTableBuilder;
import com.pb.dashboard.quality.view.table.TimingsTableDataHolder;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

public class TimingsTabsheetBuilder {

    private TimingsTableBuilder tableBuilder = new TimingsTableBuilder();

    public TabSheet getTabSheet(Channel channel, List<TimingsTableDataHolder> holders) {
        if (channel == Channel.P24) return buildTabSheet(holders, RPTabsheetBuilder.p24Names);
        else if (channel == Channel.OFFICE || channel == Channel.L3700) return buildTabSheet(holders,
                RPTabsheetBuilder.otdAnd3700Names);
        else if (channel == Channel.BIPLAN) return buildTabSheet(holders, RPTabsheetBuilder.biplaneNames);
        else return buildTabSheet(holders, RPTabsheetBuilder.terminalNames);
    }

    private TabSheet buildTabSheet(List<TimingsTableDataHolder> dataHolders, String[] names) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName("quality");
        for (int i = 0; i < names.length; i++) {
            VerticalLayout contentHolder = new VerticalLayout();
            contentHolder.addStyleName("tab-content-holder");
            contentHolder.setSpacing(true);
            TimingsTableDataHolder tableDataHolder = dataHolders.get(i);
            contentHolder.addComponent(tableBuilder.buildTable(tableDataHolder));
            tabSheet.addTab(contentHolder, names[i]);
        }

        return tabSheet;
    }


}
