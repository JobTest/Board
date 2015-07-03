package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Reindeer;

/**
 * Created by vlad
 * Date: 29.09.14
 */
public class SessionSessionsTable extends Table {

    public static final String SESSION_ID = "session_id";
    public static final String INFO = "info";

    private ClickListener listener;

    public SessionSessionsTable() {
        init();

    }

    private void init() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        setStyleName("monitoring");
        setSizeFull();
        addContainerProperty(SESSION_ID, Button.class, null);
        addContainerProperty(INFO, Table.class, null);

        setColumnExpandRatio(SESSION_ID, 1f);
        setColumnExpandRatio(INFO, 6f);

        setColumnAlignments(
                Align.CENTER,
                Align.CENTER
        );
    }

    public void addSession(String sessionId, Table table) {
        setPageLength(getPageLength() + 1);
        Button button = createButton(sessionId);
        addItem(new Object[]{button, table}, sessionId);
    }

    private Button createButton(final String sessionId) {
        Button button = new Button(sessionId);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (listener != null) {
                    listener.clickCode(sessionId);
                }
            }
        });
        return button;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }
}