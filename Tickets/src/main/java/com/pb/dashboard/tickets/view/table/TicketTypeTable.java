package com.pb.dashboard.tickets.view.table;

import com.vaadin.ui.Table;

import java.util.List;

public class TicketTypeTable extends Table {

    private String[] columnNames = { "Тип билета", "Количество продаж, шт", "Доход(грн)", "Оборот(грн)" };

    public TicketTypeTable() {
        setCaption("Динамика продаж");
        setHeight("315px");
        setWidth("100%");
        setPageLength(0);
        addStyleName("borderless");
        addStyleName("tickets");
        setSortEnabled(false);
        setFooterVisible(true);
        setColumnAlignment(columnNames[0], Table.Align.LEFT);
        for (String columnName : columnNames) {
            addContainerProperty(columnName, String.class, null);
            setColumnExpandRatio(columnName, 2);
        }
    }

    public void fillTable(TableDataHolder dataHolder) throws UnsupportedOperationException {
        List<Object[]> rows = dataHolder.getRows();
        removeAllItems();
        for (int i = 0; i < rows.size(); i++) {
            addItem(rows.get(i), i + 1);
        }
        Object[] footer = dataHolder.getFooter();
        for (int i = 0; i < columnNames.length; i++) {
            setColumnFooter(columnNames[i], (String) footer[i]);
        }
    }

}
