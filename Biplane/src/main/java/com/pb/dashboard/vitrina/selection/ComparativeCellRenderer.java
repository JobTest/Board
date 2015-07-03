package com.pb.dashboard.vitrina.selection;

import com.vaadin.ui.Table;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComparativeCellRenderer implements Serializable {

    private static final Logger LOG = Logger.getLogger(ComparativeCellRenderer.class);
    private List<Integer[]> discrepancy;

    private String[] columnNames = {"Период", "Прием", "Наличные", "Безналичные", "Задолженность",
            "Физ.лица", "Юр.лица"};

    public void setDiscrepancy(List<Integer[]> mainData, List<Integer[]> dataToCompare) {
        reset();
        if (dataToCompare != null) {
            if (dataToCompare.size() != mainData.size()) {
                LOG.error("error in ComparativeCellRenderer.setDiscrepancy() : incorrect list size");
                return;
            }
            discrepancy = new ArrayList<Integer[]>();
            for (int i = 0; i < mainData.size(); i++) {
                Integer[] main = mainData.get(i);
                Integer[] toCompare = dataToCompare.get(i);
                Integer[] values = new Integer[main.length];
                for (int j = 0; j < main.length; j++) {
                    values[j] = toCompare[j] - main[j];
                }
                discrepancy.add(values);
            }
        }
    }

    public Table.CellStyleGenerator getGenerator() {

        return new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table table, Object itemId, Object propertyId) {
                if (discrepancy != null) {
                    if (propertyId == null) return null;
                    int currentRow = (Integer) itemId;
                    int currentColumn = convertToInt((String) propertyId);

                    if (currentColumn == 0) return null;
                    if (currentRow == 0) return null;

                    for (int row = 0; row < discrepancy.size(); row++) {
                        Integer[] ints = discrepancy.get(row);
                        for (int col = 0; col < ints.length; col++) {
                            if (currentRow == (row + 1) && currentColumn == (col + 1)) {
                                if (ints[col] > 0) return "red";
                                //if (ints[col] < 0) return "green";
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    private int convertToInt(String columnName) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnName.equals(columnNames[i])) return i;
        }
        return 0;
    }

    public void reset() {
        discrepancy = null;
    }

}
