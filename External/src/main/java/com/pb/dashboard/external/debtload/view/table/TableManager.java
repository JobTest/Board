package com.pb.dashboard.external.debtload.view.table;

import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

import java.util.List;

public class TableManager {

    private static TableManager instance = new TableManager();
    private final String tableStyle = "load-table";

    private TableManager() {}

    public static TableManager getInstance() {
        return instance;
    }

    public Table buildLoadTable() {
        Table table = new Table();
        table.setStyleName(tableStyle);
        table.setSizeFull();
        table.setPageLength(0);
        for (String columnName: LoadViewDataHolder.COLUMN_NAMES) {
            // add link column to table for diff file pathToSessions
            if (columnName.equals(LoadViewDataHolder.COLUMN_NAMES[8])) table.addContainerProperty(columnName, Link.class, null);
            //set integer colum for normalSort
            if (columnName.equals(LoadViewDataHolder.COLUMN_NAMES[5])) table.addContainerProperty(columnName, Integer.class, null);
            // set colum content as String otherwise
            else table.addContainerProperty(columnName, String.class, null);

            // setting column expand ratios:
            // for column "Текст ошибки"
            if (columnName.equals(LoadViewDataHolder.COLUMN_NAMES[9]))
                table.setColumnExpandRatio(columnName, 3.0f);
            // for column "Наименование получателя"
            else if (columnName.equals(LoadViewDataHolder.COLUMN_NAMES[1]))
                table.setColumnExpandRatio(columnName, 2.0f);
            else table.setColumnExpandRatio(columnName, 1.0f);
        }

        return table;
    }

    public Table buildQueueTable(QueueTableDataHolder holder) {
        Table table = new Table();
        table.setStyleName(tableStyle);
        table.setSizeFull();
        table.setPageLength(0);
        for (String columnName: QueueTableDataHolder.COLUMN_NAMES) {
            table.addContainerProperty(columnName, String.class, null);
            if (columnName.equals(QueueTableDataHolder.COLUMN_NAMES[1]) ||
                    columnName.equals(QueueTableDataHolder.COLUMN_NAMES[12]) ||
                    columnName.equals(QueueTableDataHolder.COLUMN_NAMES[2]))
                table.setColumnExpandRatio(columnName, 2.0f);
            else
                table.setColumnExpandRatio(columnName, 1.0f);
        }
        fillTable(table, holder.getRows());

        return table;
    }

    public void fillTable(Table table, List<Object[]> rows) {
        table.removeAllItems();
        for (int i = 0; i < rows.size(); i++) {
            table.addItem(rows.get(i), i+1);
        }
    }

}
