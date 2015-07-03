package com.pb.dashboard.quality.view.table;

import com.vaadin.ui.Table;

import java.util.List;

public class RPTableBuilder {

    private final String[] staticNames = {"Период", "Всего платежей", "Кол-во уникальных клиентов", "Забракованных, %"};
    private String[] columnNames = new String[11];

    public Table buildTable(RPTableDataHolder holder) {
        Table table = new Table();
        table.setWidth("100%");
        table.setPageLength(0);
        table.addStyleName("pq");
        table.setSortEnabled(false);
        table.setFooterVisible(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsible(staticNames[0], false);
        table.setColumnCollapsible(staticNames[1], false);
        table.setColumnCollapsible(staticNames[3], false);

        String[] dynamicNames = holder.getDynamicNames();
        for (int i = 0; i < columnNames.length; i++) {
            if (i < 4) columnNames[i] = staticNames[i];
            else columnNames[i] = dynamicNames[i - 4] + ", %";
        }

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
                int row = (Integer) itemId;
                if (row % 2 == 1) return "grey";
                return null;
            }
        });
    }

}
