package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.external.monitor.chart.ChartBuilder;
import com.pb.dashboard.external.monitor.logic.QueriesDataManager;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class QueriesView extends VerticalLayout {

    private QueriesViewPanelManager panelManager;

    public QueriesView(QueriesDataManager dataManager, ChartBuilder chartManager) {
        panelManager = new QueriesViewPanelManager(dataManager, chartManager);
        addStyleName("support-view");
        addComponent(buildContent());
    }

    protected Component buildContent() {
        HorizontalLayout dataView = new HorizontalLayout();
        dataView.setSizeFull();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);
        dataView.addComponents(panelManager.getPaymentsQueriesPanel(), panelManager.getDebtQueriesPanel());

        return dataView;
    }
}