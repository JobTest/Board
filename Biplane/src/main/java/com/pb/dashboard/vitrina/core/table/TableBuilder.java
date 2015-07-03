package com.pb.dashboard.vitrina.core.table;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TableBuilder implements Serializable {

    private static final String TABLE_WIDTH = "100%";
    private static final int RATIO_POOR = 2;
    private DecimalFormat pbformat;

    public TableBuilder() {
        init();
    }

    public Table buildTableproperty(boolean hideColumnTiitle, String[] columnsName, String caption) {
        Table table = new Table() {
            @Override
            protected String formatPropertyValue(Object rowId, Object colId,
                                                 Property<?> property) {
                if (!colId.equals("Период")) {
                    if (property != null && property.getValue() != null && property.getValue() != "" && property.getValue() != " ") {
                        String valueA = (String) property.getValue();
                        if (!valueA.isEmpty()) {
                            try {
                                Double value = Double.valueOf((String) property.getValue());
                                return pbformat.format(value);
                            } catch (NumberFormatException e) {
                                return (String) property.getValue();
                            }
                        } else {
                            return "";
                        }
                    }
                }
                return super.formatPropertyValue(rowId, colId, property);
            }
        };
        table.setCaption(caption);
        table.setWidth(TABLE_WIDTH);
        table.setPageLength(0);
        table.addStyleName("plain");
        table.addStyleName("borderless");
        table.setSortEnabled(false);
        table.setFooterVisible(true);

        table.setColumnAlignment(columnsName[0], Table.Align.LEFT);
        for (String aColumnsName : columnsName) {
            table.addContainerProperty(aColumnsName, String.class, null);
            table.setColumnExpandRatio(aColumnsName, RATIO_POOR);
        }
        if (hideColumnTiitle) {
            table.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        }
        return table;
    }

    private void init() {
        pbformat = setDecimalFormat();
    }

    public DecimalFormat setDecimalFormat() {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');
        return new DecimalFormat("###,###,###.##", symbols);
    }
}