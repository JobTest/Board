package com.pb.dashboard.tester.middleware;

import com.vaadin.ui.Table;

import java.util.List;

public class TableManager {

    private final static String[] columnNames = {"ID","Ping/Status","Server IP","Port","Datasource IP","Datasource Name","Application","Path"};

    public Table buildTable() {
        Table table = new Table();
        table.addStyleName("middleware");
        table.setSizeFull();
        for (String s : columnNames) {
            table.addContainerProperty(s, String.class, null);
        }
        table.setColumnWidth(columnNames[6], 400);

        return table;
    }

    public void fillTable(Table table, List<Object[]> rows) {
        table.removeAllItems();
        for (int i = 0; i < rows.size(); i++) {
            table.addItem(rows.get(i), i + 1);
        }
    }

}
