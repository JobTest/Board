package com.pb.dashboard.external.monitor.sessions;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.external.monitor.sessions.table.SessionsTablePanel;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.util.List;
import java.util.Map;

public class SessionsView extends VerticalLayout {

    private static final String STATUS_ERROR = "Статус ошибки";
    private static final String COUNT_ERROR = "Кол-во ошибок";
    private static final String CROSSING = "переход";
    private static final String SHOW_SESSIONS = "Показать сессии";
    private SessionsModel model;

    private VerticalLayout content;
    private Component sessionTablePanel;

    public SessionsView(Map<String, String> params) {
        Component pageContent;
        initModel(params);
        pageContent = buildContent();
        addComponent(pageContent);
    }

    private Component buildContent() {
        content = new VerticalLayout();
        content.setSpacing(true);
        Map<Integer, Integer> errors = getErrCntByStatus();
        Table table = new Table();
        table.addContainerProperty(STATUS_ERROR, String.class, null);
        table.addContainerProperty(COUNT_ERROR, String.class, null);
        table.addContainerProperty(CROSSING, Button.class, null);
        table.addStyleName("table-panel");
        GridLayout grid = new GridLayout();
        grid.setColumns(3);
        int i = 0;
        for (final Map.Entry<Integer, Integer> entry : errors.entrySet()) {
            i++;
            table.addItem(new Object[]{entry.getKey(), entry.getValue()});
            final Button button = new Button(SHOW_SESSIONS);
            button.setStyleName(BaseTheme.BUTTON_LINK);
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    if (sessionTablePanel != null) {
                        content.removeComponent(sessionTablePanel);
                    }
                    Complex complex = model.getComplex();
                    sessionTablePanel = new SessionsTablePanel(complex, getSessionsByErrStatus(entry.getKey()));
                    content.addComponent(sessionTablePanel);
                    content.setComponentAlignment(sessionTablePanel, ALIGNMENT_DEFAULT);
                }
            });
            table.addItem(new Object[]{String.valueOf(entry.getKey()), String.valueOf(entry.getValue()), button}, i);

        }
        if (i < 6) {
            int height = 31 * i + 32;
            table.setHeight(height + "px");
        }
        table.setEnabled(true);
        grid.addComponent(table, 0, 0);

        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidth("100%");
        topBar.addComponents(grid);
        topBar.setComponentAlignment(grid, Alignment.TOP_CENTER);
        content.addComponent(topBar);
        return content;
    }

    private void initModel(Map<String, String> params) {
        model = SessionsModelBuilder.build(params);
    }

    public Map<Integer, Integer> getErrCntByStatus() {

        return ServiceFactory.getExternal().getErrorCntByStatus(model.getComplex(), model.isSystem(), model.getYear(),
                model.getMonth(), model.getDay(), model.getHour(), model.getMinute(), model.getInterfaceName());
    }

    public List<BpSession> getSessionsByErrStatus(int sessionStatus) {
        return ServiceFactory.getExternal().getSessionsByErrStatus(model.getComplex(), model.getYear(), model.getMonth(),
                model.getDay(), model.getHour(), model.getMinute(), model.getInterfaceName(), String.valueOf(sessionStatus));
    }
}
