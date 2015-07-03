package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.external.monitor.chart.ChartBuilder;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.pb.dashboard.external.monitor.logic.ErrorsDataManager;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.Date;

public class ErrorsView extends VerticalLayout {

    private static final long serialVersionUID = -2189826011034545811L;
    private ErrorsViewPanelManager panelManager;

    public ErrorsView(ErrorsDataManager dataManager, ChartBuilder chartManager) {
        panelManager = new ErrorsViewPanelManager(dataManager, chartManager);
        addStyleName("support-view");
        buildContent();
    }

    private void buildContent() {
        HorizontalLayout dataView = new HorizontalLayout();
        dataView.setSizeFull();
        dataView.setSpacing(true);
        dataView.setMargin(true);
        dataView.addComponents(panelManager.getPaymentsChartPanel(), panelManager.getTemplChartPanel());
        panelManager.fillCharts(
                FilterRange.R10MIN,
                new DInterfaceI[]{ErrorsViewPanelManager.ALL_INTERFACES, ErrorsViewPanelManager.ALL_INTERFACES},
                new Date[]{new Date()});
        addComponents(panelManager.getFilterPanel(), dataView);
    }
}