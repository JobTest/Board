package com.pb.dashboard.external.monitor.sessions;

import com.pb.dashboard.dao.entity.biplanesupport.db.TableDataHolder;
import com.vaadin.ui.Table;

import java.util.List;

public class TableManager {

    public Table buildTable(TableDataHolder dataHolder) {
        Table table = new Table();
        table.addStyleName("sessions");
        table.setWidth("100%");
        table.setPageLength(0);
        table.setFooterVisible(false);

        for (String name : dataHolder.getColumnNames()) {
            if (name.equals("duration")) {
                table.addContainerProperty(name, Integer.class, null);
            } else {
                table.addContainerProperty(name, String.class, null);
            }
        }

        fillTable(table, dataHolder.getRows());

        return table;
    }

    public void fillTable(Table table, List<Object[]> rows) {
        for (int i = 0; i < rows.size(); i++) {
            table.addItem(rows.get(i), i + 1);
        }
    }
}
