package com.pb.dashboard.monitoring.sessions.viewcomponents;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.db.BpSession;
import com.pb.dashboard.dao.entity.biplanesupport.db.TableDataHolder;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.sessions.viewcomponents.table.TableManager;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.util.ArrayList;
import java.util.List;

public class TablePanel extends VerticalLayout implements Property.ValueChangeListener {

    private static final long serialVersionUID = 2415499805703041712L;
    private Complex complex;
    private TableManager tableManager = new TableManager();
    private Table tableInfoSession;
    private Component buttons;
    private List<Button> sessionButtons = new ArrayList<Button>();
    private Component buttonsWithoutDebt;
    private List<Button> sessionsWithoutDebtButtons = new ArrayList<Button>();

    public TablePanel(Complex complex, List<BpSession> sessions) {
        this.complex = complex;
        setMargin(true);
        addStyleName("table-panel");
        setSizeFull();
        Component buttons = createButtonsSessions(sessionButtons, sessions);
        addComponent(buttons);
    }

    public TablePanel(Complex complex, List<BpSession> sessions, List<BpSession> sessionsWithoutDebt) {
        this.complex = complex;
        setMargin(true);
        addStyleName("table-panel");
        setSizeFull();
        buttons = createButtonsSessions(sessionButtons, sessions);
        buttonsWithoutDebt = createButtonsSessions(sessionsWithoutDebtButtons, sessionsWithoutDebt);
        buttonsWithoutDebt.setVisible(false);
        addComponent(buttons);
        addComponent(buttonsWithoutDebt);
    }

    private Component createButtonsSessions(List<Button> sessionButtons, List<BpSession> sessions) {
        CssLayout buttonsLayout = new CssLayout();
        buttonsLayout.addStyleName("session-buttons");
        for (BpSession session : sessions) {
            Button sessionButton = createSessionButton(session);
            sessionButtons.add(sessionButton);
            buttonsLayout.addComponent(sessionButton);
        }
        return buttonsLayout;
    }

    private Button createSessionButton(final BpSession session) {
        String sessionStr = sessionToString(session);
        final Button button = new Button(sessionStr);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addStyleName("session-link");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                TableDataHolder dataHolder = loadTableDataHolder(session);
                Table table = tableManager.buildTable(dataHolder);
                styleActiveButton(button);
                addTable(table);
            }
        });
        return button;
    }

    private String sessionToString(BpSession session) {
        if (session == null) {
            return "null";
        }
        StringBuilder res = new StringBuilder(session.getId());
        if (session.getDuration() != null) {
            res.append(String.format(" (%sms)", session.getDuration()));
        }
        return res.toString();
    }

    private void styleActiveButton(Button button) {
        for (Button currentButton : sessionButtons) {
            if (currentButton == button) {
                currentButton.addStyleName("active");
            } else {
                currentButton.removeStyleName("active");
            }
        }
    }

    private void addTable(Table table) {
        if (components.contains(tableInfoSession)) {
            removeComponent(tableInfoSession);
        }
        this.tableInfoSession = table;
        addComponent(table);
    }

    private TableDataHolder loadTableDataHolder(BpSession session) {
        return ServiceFactory.getMonitoring().getSessionDetails(session.getId(), complex.getId());
    }

    public void showDebtSessions(boolean show) {
        buttons.setVisible(!show);
        buttonsWithoutDebt.setVisible(show);
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        showDebtSessions((Boolean) event.getProperty().getValue());
    }
}
