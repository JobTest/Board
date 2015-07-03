package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionType;
import com.pb.dashboard.monitoring.components.parameter.MonitoringParam;
import com.pb.dashboard.monitoring.sessions.viewcomponents.NavigationHeader;
import com.pb.dashboard.monitoring.sessions.viewcomponents.TablePanel;
import com.pb.dashboard.monitoring.sessions.viewcomponents.charts.ChartsLayout;
import com.pb.dashboard.monitoring.timings.transferlink.BackLink;
import com.pb.dashboard.monitoring.timings.transferlink.SpecLink;
import com.vaadin.data.Property;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class SessionsView extends DAbstractView {

    private static final long serialVersionUID = -1992304297710847230L;
    private SessionsControllerI controller;
    private SessionsModelI model;

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("Биплан - Сессии");
        addStyleName("timings-view");
        init();
    }

    private void init() {
        controller = new SessionsController(this);
        model = controller.getModel();

        navigationBar.setBack(controller.getUrlBack());
        content.removeAllComponents();
        content.addComponent(buildView());
    }

    private VerticalLayout buildView() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.addComponent(getNavigationLayout());
        content.addComponent(new ChartsLayout(model));

        final TabSheet tabSheet = new TabSheet();
//        tabSheet.addStyleName("sessions-tabs");
        final List<CheckBox> boxes = new ArrayList<>();

        for (SessionType sessionType : SessionType.values()) {
            VerticalLayout layout = new VerticalLayout();
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setMargin(true);

            CheckBox checkBox = new CheckBox("Без задолженности");
            checkBox.addValueChangeListener(new Property.ValueChangeListener() {
                private static final long serialVersionUID = 616351321647765163L;

                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    for (CheckBox box : boxes) {
                        box.setValue((Boolean) event.getProperty().getValue());
                    }
                }
            });
            boxes.add(checkBox);
            horizontalLayout.addComponent(checkBox);
            layout.addComponent(horizontalLayout);
            layout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_RIGHT);
            TablePanel tablePanel = new TablePanel(model.getComplex(), model.getSessions(sessionType), model.getSessionsWithoutDebt(sessionType));
            layout.addComponent(tablePanel);
            checkBox.addValueChangeListener(tablePanel);

            tabSheet.addTab(layout, sessionType.getFullName());
        }
        tabSheet.setSelectedTab(getTabToSelect());
        tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            private static final long serialVersionUID = -1404325350846187144L;

            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
                Page.getCurrent().setUriFragment(getLocation(tabSheet), false);
            }
        });
        content.addComponent(tabSheet);

        return content;
    }

    private String getLocation(TabSheet tabSheet) {
        String[] splitUrl = Page.getCurrent().getUriFragment().split("/");
        String splitSymbol = "&";
        String url = "";
        for (int i = 0; i < splitUrl.length - 1; i++) {
            url += splitUrl[i] + "/";
        }
        String[] params = splitUrl[splitUrl.length - 1].split(splitSymbol);
        for (int i = 0; i < params.length - 1; i++) {
            url += params[i] + splitSymbol;
        }
        url += MonitoringParam.CRITERION.getName() + "=";
        url += getSelectedTabIndex(tabSheet);
        return url;
    }

    private int getSelectedTabIndex(TabSheet tabSheet) {
        int index = -1;
        if (tabSheet.getComponentCount() > 0) {
            index = tabSheet.getTabPosition(tabSheet.getTab(tabSheet.getSelectedTab()));
        }
        return index;
    }

    private Layout getNavigationLayout() {
        HorizontalLayout navigationLayout = new HorizontalLayout();
        navigationLayout.setSizeFull();
        BackLink backLink = new BackLink();
        SpecLink specLink = new SpecLink(model.getComplex());
        NavigationHeader header = new NavigationHeader(model, controller.getFilterModel());
        navigationLayout.addComponents(backLink, header, specLink);
        navigationLayout.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
        navigationLayout.setExpandRatio(backLink, 1.0f);
        navigationLayout.setExpandRatio(specLink, 1.0f);
        navigationLayout.setExpandRatio(header, 10.0f);
        return navigationLayout;
    }

    private int getTabToSelect() {
        return model.getCategory().getId();
    }
}