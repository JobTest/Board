package com.pb.dashboard.itdashbord.errors.error.content;

import com.pb.dashboard.itdashbord.errors.error.content.table.ErrorTable;
import com.vaadin.ui.VerticalLayout;

public class ContentView extends VerticalLayout {

    private ErrorTable table;

    public ContentView() {
        init();
    }

    private void init() {
        setSizeFull();
        table = new ErrorTable();

        addComponent(table);
    }

    public ErrorTable getTable() {
        return table;
    }
}
