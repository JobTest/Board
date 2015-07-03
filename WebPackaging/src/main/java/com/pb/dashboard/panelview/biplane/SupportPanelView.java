package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.core.config.ConfigManager;
import com.pb.dashboard.core.config.ConfigParams;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.external.monitor.chart.ChartBuilder;
import com.pb.dashboard.external.monitor.logic.ErrorsDataManager;
import com.pb.dashboard.external.monitor.logic.QueriesDataManager;
import com.pb.dashboard.external.monitor.sessions.SessionsView;
import com.pb.dashboard.external.monitor.view.ErrorsView;
import com.pb.dashboard.external.monitor.view.NagiosView;
import com.pb.dashboard.external.monitor.view.QueriesView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

public class SupportPanelView extends DAbstractView {

    private static final String SUPPORT = "Support";
    private static final String ERRORS = Dashboard.Biplane.Support.Errors.NAME;
    private static final String QUERIES = Dashboard.Biplane.Support.Queries.NAME;
    private static final String NAGIOS = Dashboard.Biplane.Support.Nagios.NAME;
    private static final String SESIONS = Dashboard.Biplane.Support.Sessions.NAME;
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

    private CssLayout root;
    private Component currentComponent;
    private ErrorsDataManager errorsDataManager;
    private QueriesDataManager queriesDataManager;
    private ChartBuilder chartManager;

    public ErrorsDataManager getErrorsDataManager() {
        if (errorsDataManager == null) {
            errorsDataManager = new ErrorsDataManager();
        }
        return errorsDataManager;
    }

    public QueriesDataManager getQueriesDataManager() {
        if (queriesDataManager == null) {
            queriesDataManager = new QueriesDataManager();
        }
        return queriesDataManager;
    }

    public ChartBuilder getChartManager() {
        if (chartManager == null) {
            chartManager = new ChartBuilder(getErrorsDataManager());
        }
        return chartManager;
    }

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        init(event);
    }

    private void init(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(SUPPORT);
        root = new CssLayout();
        content.removeAllComponents();
        content.addComponent(root);
        initMenuButtons();
        initCurrentComponent(event);
    }

    private void initCurrentComponent(ViewChangeListener.ViewChangeEvent event) {
        String[] params = event.getParameters().split("/", 2);
        String name = params[FIRST_ELEMENT];
        root.removeAllComponents();
        navigationBar.setBack(Dashboard.Biplane.PATH);
        if (ERRORS.equals(name)) {
            String path = ConfigManager.getInstance().getParam(ConfigParams.WEB_CLIENT_URL) + "support/errors";
            Page.getCurrent().setLocation(path);
            return;
        } else if (QUERIES.equals(name)) {
            currentComponent = new QueriesView(getQueriesDataManager(), getChartManager());
        } else if (NAGIOS.equals(name)) {
            currentComponent = new NagiosView();
        } else if (SESIONS.equals(name)) {
            navigationBar.setBack(Dashboard.Biplane.Support.Errors.PATH);
            currentComponent = new SessionsView(getParameters());
        }
        if (currentComponent != null) {
            root.addComponent(currentComponent);
        } else {
            String path = Dashboard.Biplane.Support.Errors.PATH;
            UI.getCurrent().getNavigator().navigateTo(path);
        }
    }

    private void initMenuButtons() {
        navigationBar.setVisible(false);
//        HorizontalLayout buttons = new HorizontalLayout();
//        Button errors = createViewLink("Ошибки", Dashboard.Biplane.Support.Errors.PATH);
//        Button queries = createViewLink("Запросы", Dashboard.Biplane.Support.Queries.PATH);
//        Button nagios = createViewLink("Nagios", Dashboard.Biplane.Support.Nagios.PATH);
//        Button timings = createViewLink("Тайминги", Dashboard.Biplane.Monitoring.PATH);
//        Button pay = createViewLink("Прием Платежей", Dashboard.Biplane.Payments.Pay.PATH);
//        Button debtLoad = createViewLink("Протокол загрузки", Dashboard.Biplane.DebtLoad.Queue.PATH);
//        buttons.addComponent(errors);
//        buttons.addComponent(queries);
//        buttons.addComponent(nagios);
//        buttons.addComponent(timings);
//        buttons.addComponent(pay);
//        buttons.addComponent(debtLoad);
//
//        menu.addComponent(buttons);
//        menu.setComponentAlignment(buttons, Alignment.TOP_CENTER);
    }
}