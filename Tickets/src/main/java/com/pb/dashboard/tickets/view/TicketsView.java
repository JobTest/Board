package com.pb.dashboard.tickets.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.addon.borderlayout.BorderLayout;

public class TicketsView extends Panel implements View {

    private PanelManager panelManager = new PanelManager();

    @Override
    public void enter(ViewChangeEvent viewChangeEvent) {
    }

    public TicketsView() {
        setSizeFull();
        addStyleName("tickets-view");
        setContent(getDataView());
        panelManager.init();
    }

    private BorderLayout getDataView() {
        BorderLayout components = new BorderLayout();
        components.setSpacing(true);
        components.addComponent(buildLeftPane(), BorderLayout.Constraint.WEST);
        components.addComponent(buildContent(), BorderLayout.Constraint.CENTER);
        return components;
    }

    private VerticalLayout buildLeftPane() {
        VerticalLayout leftPane = new VerticalLayout();
        leftPane.addStyleName("left-pane");
        leftPane.setWidth("230px");
        leftPane.setSpacing(true);
        Component filterPanel = panelManager.getFilterPanel();
        leftPane.addComponent(createPanel(filterPanel, "Фильтр"));
        leftPane.addComponent(panelManager.getStatsPanel());
        return leftPane;
    }

    private Component buildContent() {
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setSizeFull();
        content.addComponent(panelManager.getTopChartPanel());
        content.addComponent(panelManager.getBottomChartPanel());
        return content;
    }


    private CssLayout createPanel(Component content, String title) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        content.setCaption(title);
        return panel;
    }

}
