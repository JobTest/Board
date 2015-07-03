package com.pb.dashboard.quality.view.table;

import com.vaadin.ui.Table;

import java.util.List;

public class TimingsTableBuilder {

    private final String[] columnNames = {"Период", "Всего платежей", "Кол-во уникальных клиентов",
            "Не уложились в регламент, %", "Среднее время проведения", "Среднее время ОДБ"};

    public Table buildTable(TimingsTableDataHolder holder) {
        Table table = new Table();
        table.setWidth("100%");
        table.setPageLength(0);
        table.addStyleName("pq");
        table.setSortEnabled(false);
        table.setFooterVisible(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsible(columnNames[0], false);
        table.setColumnCollapsible(columnNames[1], false);
        table.setColumnCollapsible(columnNames[3], false);

        table.setColumnAlignment(columnNames[0], Table.Align.LEFT);
        for (String name : columnNames) {
            table.addContainerProperty(name, String.class, null);
            table.setColumnExpandRatio(name, 2);
        }
        fillTable(table, holder.getTotal(), holder.getRows());

        return table;
    }

    public void fillTable(Table table, String[] total, List<Object[]> rows) {
        for (int i = 0; i < rows.size(); i++) {
            table.addItem(rows.get(i), i + 1);
        }
        for (int i = 0; i < total.length; i++) {
            table.setColumnFooter(columnNames[i], total[i]);
        }

        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table table, Object itemId, Object propertyId) {
                if (propertyId == null) return null;
                int row = ((Integer) itemId).intValue();
                if (row % 2 == 1) return "grey";
                return null;
            }
        });
    }

}
