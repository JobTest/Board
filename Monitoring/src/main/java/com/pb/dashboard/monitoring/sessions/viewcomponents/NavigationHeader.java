package com.pb.dashboard.monitoring.sessions.viewcomponents;

import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.monitoring.components.filter.FilterModel;
import com.pb.dashboard.monitoring.panel.manager.PopUpManager;
import com.pb.dashboard.monitoring.sessions.SessionsModel;
import com.pb.dashboard.monitoring.sessions.SessionsModelI;
import com.pb.dashboard.monitoring.timings.TimingsModel;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.vaadin.hene.popupbutton.PopupButton;

import java.util.ArrayList;
import java.util.List;

public class NavigationHeader extends HorizontalLayout {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final long serialVersionUID = -462910416865246525L;
    private SessionsModelI model;
    private PopUpManager popUpManager = new PopUpManager();
    private FilterModel filterModel;

    public NavigationHeader(SessionsModelI model, FilterModel filterModel) {
        this.model = model;
        this.filterModel = filterModel;
        buildNavigation();
    }

    private void buildNavigation() {
        setWidth("100%");
        setStyleName("timings-navigation-header");
        HorizontalLayout breadcrumbs = new HorizontalLayout();
        breadcrumbs.setSpacing(true);
        // Navigation breadcrumbs
        List<Component> crumbs = getBreadcrumbs();
        for (int i = 0; i < crumbs.size(); i++) {
            crumbs.get(i).addStyleName("charts-label");
            breadcrumbs.addComponent(crumbs.get(i));
            if (i != crumbs.size() - 1) {
                Label sep = new Label(">");
                sep.addStyleName("charts-label");
                breadcrumbs.addComponent(sep);
            }
        }
        addComponent(breadcrumbs);
        setComponentAlignment(breadcrumbs, Alignment.TOP_CENTER);
    }

    private List<Component> getBreadcrumbs() {
        List<Component> crumbs = new ArrayList<>();
        Label country = new Label(model.getCountry().getName());
        Label complex = new Label(model.getComplex().getName());

        ArrayList<DInterfaceI> dInterfaceIs = new ArrayList<>(model.getInterfaces().values());

        PopupButton interfacesLink = new PopupButton(model.getBpInterface().getName());
        interfacesLink.setContent(popUpManager.getInterfacesPopUp(model.getCountry(),
                model.getComplex(), dInterfaceIs, filterModel));
        crumbs.add(country);
        crumbs.add(complex);
        crumbs.add(interfacesLink);

        if (model instanceof TimingsModel) {
            TimingsModel chartsModel = (TimingsModel) model;
            NavigatorModelAdapter navigator = chartsModel.getNavigator();
            if (navigator.getMetric() != null && navigator.getMetrics() != null) {
                PopupButton metricsLink = new PopupButton(navigator.getMetric().getDescription());
                metricsLink.setContent(popUpManager.getMetricsPopUp(navigator.getCountry(), navigator.getComplex(),
                        navigator.getBpInterface(), navigator.getMetrics()));
                crumbs.add(metricsLink);
            }
        } else if (model instanceof SessionsModel) {
            SessionsModel sessionsModel = (SessionsModel) model;
            String dateFrom = sessionsModel.getDateFrom().toString(DATE_PATTERN);
            String dateTo = sessionsModel.getDateTo().toString(DATE_PATTERN);
            crumbs.add(new Label("Период: " + dateFrom + " - " + dateTo));
        }
        return crumbs;
    }
}
