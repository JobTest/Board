package com.pb.dashboard.itdashbord.table;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TableBuilder implements Serializable {
    private static final long serialVersionUID = 4953914532889385225L;
    private static final String TABLE_WIDTH = "100%";
    private static final String TABLE_Hight = "95%";
    private static final int RATIO_POOR = 2;
    private DecimalFormat pbformat;

    public TableBuilder() {
        init();
    }

    @SuppressWarnings("deprecation")
    public Table buildTableproperty(boolean hideColumnTiitle, String[] columnsName, String caption) {
        Table table = new Table() {
            @Override
            protected String formatPropertyValue(Object rowId, Object colId, Property<?> property) {
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
                return super.formatPropertyValue(rowId, colId, property);
            }
        };

        table.setCaption(caption);
        table.setWidth(TABLE_WIDTH);
        table.setHeight(TABLE_Hight);
        table.setPageLength(0);
        table.addStyleName("plain");
        table.addStyleName("borderless");
        table.setSortEnabled(false);
        table.setFooterVisible(true);


        table.setColumnAlignment(columnsName[0], Table.Align.LEFT);
        for (int i = 0; i < columnsName.length; i++) {
            table.addContainerProperty(columnsName[i], String.class, null);
            table.setColumnExpandRatio(columnsName[i], RATIO_POOR);
        }
        if (hideColumnTiitle) {
            table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
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