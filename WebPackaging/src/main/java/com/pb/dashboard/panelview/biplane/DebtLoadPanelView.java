package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.external.charts.ChartsView;
import com.pb.dashboard.external.debtload.view.load.LoadView;
import com.pb.dashboard.external.debtload.view.queue.QueueView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ResourceReference;
import com.vaadin.ui.*;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DebtLoadPanelView extends DAbstractView {

    private static final long serialVersionUID = -5117656833188938528L;
    private static final String QUEUE = Dashboard.Biplane.DebtLoad.Queue.NAME;
    private static final String LOAD = Dashboard.Biplane.DebtLoad.Load.NAME;
    private static final String CHARTS = Dashboard.Biplane.DebtLoad.Charts.NAME;

    private CssLayout root;
    private QueueView queueView;
    private LoadView loadView;
    private ChartsView chartsView;

    public QueueView getQueueView() {
        if (queueView == null) {
            queueView = new QueueView();
        }
        return queueView;
    }

    public LoadView getLoadView() {
        if (loadView == null) {
            loadView = new LoadView();
        }
        return loadView;
    }

    public ChartsView getChartsView() {
        if (chartsView == null) {
            chartsView = new ChartsView();
        }
        return chartsView;
    }

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        init(event);
    }

    private void init(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.DEBT_LOAD.getName());
        navigationBar.setBack(Dashboard.Biplane.PATH);
        initRoot();
        initMenuButtons();
        initCurrentComponent(event.getParameters());
    }

    private void initRoot() {
        root = new CssLayout();
        root.addStyleName("root");
        root.setSizeFull();
        content.removeAllComponents();
        content.addComponent(root);
    }

    private void initCurrentComponent(String urlParams) {
        String[] params = urlParams.split("/", 2);
        String name = params[0];
        if (QUEUE.equals(name)) {
            root.addComponent(getQueueView());
        }
        if (LOAD.equals(name)) {
            root.addComponent(getLoadView());
        }
        if (CHARTS.equals(name)) {
            LocalDate date = LocalDate.now(DateTimeZone.UTC);
            String urlFragment = "!"+Dashboard.Biplane.DebtLoad.Charts.PATH + "/0/hourly/" + date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth() + "/";
            Page.getCurrent().setUriFragment(urlFragment, false);
            root.addComponent(getChartsView());
        }
        if (root.getComponentCount() == 0) {
            String path = Dashboard.Biplane.DebtLoad.Queue.PATH;
            UI.getCurrent().getNavigator().navigateTo(path);
        }
    }

    private void initMenuButtons() {
        HorizontalLayout menu = navigationBar.getMenu();
        if (menu.getComponentCount() != 0) {
            return;
        }
        Button queue = createViewLink("Очередь", Dashboard.Biplane.DebtLoad.Queue.PATH);
        Button load = createViewLink("Загруженные", Dashboard.Biplane.DebtLoad.Load.PATH);
        Button charts = createViewLink("Графики", Dashboard.Biplane.DebtLoad.Charts.PATH);
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(queue);
        layout.addComponent(load);
        layout.addComponent(charts);
        menu.addComponent(layout);
        menu.setComponentAlignment(layout, Alignment.TOP_CENTER);
    }

    private Button createLink(String caption) {
        final Button paymentButton = new Button(caption);
        paymentButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Resource res = new ExternalResource(getURL());
                final ResourceReference rr = ResourceReference.create(res, paymentButton, "");
                Page.getCurrent().open(rr.getURL(), null);
            }

            private String getURL() {
                String[] splitedURL = Page.getCurrent().getUriFragment().split("/");
                String URL = "";
                if (!splitedURL[splitedURL.length - 1].equals("charts")) {
                    for (int i = 0; i < splitedURL.length - 1; i++) {
                        URL += splitedURL[i] + "/";
                    }
                    URL = "#" + URL + "charts/0/";
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                URL += "hourly/";
                URL += sdf.format(new Date());
                URL += "/";
                return URL;
            }
        });
        return paymentButton;
    }
}