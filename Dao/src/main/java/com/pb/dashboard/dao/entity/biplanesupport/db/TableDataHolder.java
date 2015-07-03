package com.pb.dashboard.dao.entity.biplanesupport.db;

import java.util.ArrayList;
import java.util.List;

public class TableDataHolder {

    private String[] columnNames;
    private List<Object[]> rows;

    public TableDataHolder() {
        columnNames = new String[0];
        rows = new ArrayList<Object[]>();
    }

    public TableDataHolder(String[] columnNames, List<Object[]> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public List<Object[]> getRows() {
        return rows;
    }
}
