package com.pb.dashboard.quality.view.table;

import java.util.List;

public class RPTableDataHolder extends TableDataHolder {

    private String[] dynamicNames;

    public RPTableDataHolder(String[] dynamicNames, String[] total, List<Object[]> rows) {
        super(total, rows);
        this.dynamicNames = dynamicNames;
    }

    public String[] getDynamicNames() {
        return dynamicNames;
    }
}
