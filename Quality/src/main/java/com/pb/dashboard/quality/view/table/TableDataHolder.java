package com.pb.dashboard.quality.view.table;

import java.util.List;

public abstract class TableDataHolder {

    private String[] total;
    private List<Object[]> rows;

    public TableDataHolder(String[] total, List<Object[]> rows) {
        this.total = total;
        this.rows = rows;
    }

    public String[] getTotal() {
        return total;
    }

    public List<Object[]> getRows() {
        return rows;
    }

}
