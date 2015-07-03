package com.pb.dashboard.monitoring.errors.all.window.complex;

import com.pb.dashboard.monitoring.errors.all.table.SessionSessionsTable;
import com.pb.dashboard.monitoring.errors.all.window.complex.tabs.TabsView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by vlad
 * Date: 22.09.14
 */
public class ComplexView extends VerticalLayout {

    private Panel tablePanel;
    private SessionSessionsTable table;

    private TabsView tabSheet;

    public ComplexView() {
        setSizeFull();
        initTablePanel();
    }

    private void initTablePanel() {
        tablePanel = new Panel();
        tablePanel.setSizeFull();
        tablePanel.setVisible(false);
        addComponent(tablePanel);
        setExpandRatio(tablePanel, 1f);
    }

    public void addTabSheet(TabsView tabSheet) {
        this.tabSheet = tabSheet;
        addComponent(tabSheet);
        setExpandRatio(tabSheet, 1.5f);
    }

    public void setTable(SessionSessionsTable table) {
        this.table = table;
        tablePanel.setContent(table);
        tablePanel.setVisible(true);
    }

    public TabsView getTabs() {
        return tabSheet;
    }

    public SessionSessionsTable getTable() {
        return table;
    }
}