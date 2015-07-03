package com.pb.dashboard.monitoring.errors.outer.table;

import com.pb.dashboard.monitoring.errors.outer.table.listener.ClickSessionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Reindeer;

import java.util.List;

/**
 * Created by evasive on 03.11.14.
 */
public class OuterSessionTable extends Table {

    public static final String SESSION_ID = "session_id";
    public static final String IN_NAME = "interface_name";
    public static final String START_TIME = "start_time";
    public static final String DURATION = "duration";
    public static final String STATUS = "status";
    public static final String ERROR_CODE = "error_code";
    public static final String COMPANY_ID = "company_id";

    private ClickSessionListener listener;

    public OuterSessionTable() {
        init();
    }

    public OuterSessionTable(List<OuterSessionData> list) {
        init();
        setOuterSession(list);
    }

    private void init() {
        setImmediate(true);
        setPageLength(0);
        setWidth("100%");
        addStyleName("monitoring");

        setSizeFull();
        addContainerProperty(SESSION_ID, Button.class, null);
        addContainerProperty(IN_NAME, String.class, null);
        addContainerProperty(START_TIME, String.class, null);
        addContainerProperty(DURATION, String.class, null);
        addContainerProperty(STATUS, String.class, null);
        addContainerProperty(COMPANY_ID, String.class, null);
        addContainerProperty(ERROR_CODE, String.class, null);

        setColumnAlignments(
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER
        );

    }

    public void setOuterSession(List<OuterSessionData> outerSessionList) {
        removeAllItems();
        setPageLength(outerSessionList.size());
        for (OuterSessionData outerSession : outerSessionList) {
            Button sessionIdButton = createButton(outerSession.getSessionId());
            addItem(new Object[]{sessionIdButton, outerSession.getInName(),
                    outerSession.getStartTime(), outerSession.getDuration(), outerSession.getStatus(), outerSession.getCompanyId(),
                    outerSession.getErrorCode()}, null);
        }
    }

    private Button createButton(final String sessionId) {
        Button button = new Button(sessionId);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (listener != null) {
                    listener.clickSession(sessionId);
                }
            }
        });
        return button;
    }

    public void setListener(ClickSessionListener listener) {
        this.listener = listener;
    }
}
