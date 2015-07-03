package com.pb.dashboard.vitrina.payview;

import com.vaadin.ui.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PayViewCellRenderer implements Serializable{

    private List<boolean[]> discrepancy;
    private String[] columnNames;

    public PayViewCellRenderer(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void setDiscrepancy(List<Object[]> monthOldMetrics, List<Object[]> oldMetrics,
                               List<Object[]> newMetrics, Integer hourlyThreshold) {
        reset();
        discrepancy = new ArrayList<boolean[]>();
        for (int i = 0; i < 4; i++) { // table rows
            boolean[] values = new boolean[4]; // table columns
            if (i == 0) {
                if (oldMetrics != null && newMetrics != null) {
                    Object[] oldInts = oldMetrics.get(i);
                    Object[] newInts = newMetrics.get(i);
                    calculateDifference(hourlyThreshold, values, oldInts, newInts);
                }
            } else {
                if (monthOldMetrics != null && newMetrics != null) {
                    Object[] oldInts = monthOldMetrics.get(i - 1);
                    Object[] newInts = newMetrics.get(i);
                    calculateDifference(null, values, oldInts, newInts);
                }
            }
            discrepancy.add(values);
        }
    }

    private void calculateDifference(Integer hourlyThreshold, boolean[] values, Object[] oldInts, Object[] newInts) {
        for (int j = 0; j < values.length; j++) {
            int newInt = Integer.parseInt((String) newInts[j + 1]);
            int oldInt = Integer.parseInt((String) oldInts[j + 1]);
            if (hourlyThreshold == null) {
                if ((newInt == 0 && oldInt == 0)) continue;
                values[j] = (newInt - oldInt <= 0);
            } else {
                if ((newInt == 0 && oldInt == 0) || hourlyThreshold == 0) continue;
                int diff = newInt - oldInt;
                values[j] = (diff < 0) || (j == 0) ? (diff < hourlyThreshold * 2) : (diff < hourlyThreshold);
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
                        boolean[] ints = discrepancy.get(row);
                        for (int col = 0; col < ints.length; col++) {
                            if (currentRow == (row + 1) && currentColumn == (col + 1)) {
                                if (ints[col]) return "red";
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
