package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

import java.util.List;

public class StackTraceTable extends Table {

    protected static final String MESSAGE = "message";

    public StackTraceTable() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        setStyleName("monitoring");
        addStyleName("small");
        addContainerProperty(MESSAGE, TextArea.class, null);

        setColumnExpandRatio(MESSAGE, 5f);

        setColumnAlignments(
                Align.CENTER
        );
    }

    public void setStackTraces(List<SessionStackTraceData> stackTraces) {
        setPageLength(stackTraces.size());
        removeAllItems();
        for (SessionStackTraceData stackTrace : stackTraces) {
            addItem(new Object[]{
                    createTextArea(stackTrace.getMessage()),
            }, null);
        }
    }

    private TextArea createTextArea(String text) {
        TextArea area = new TextArea();
        area.setSizeFull();
        area.setValue(text);
        area.setRows(30);
        area.setReadOnly(true);
        return area;
    }
}