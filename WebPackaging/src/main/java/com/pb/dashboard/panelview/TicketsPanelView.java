package com.pb.dashboard.panelview;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.tickets.view.TicketsView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.Locale;

public class TicketsPanelView extends Panel implements View {

    CssLayout root = new CssLayout();
    CssLayout content = new CssLayout();

    public void enter(ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.TICKETS.getName());
        setLocale(Locale.US);
        setContent(root);
        setSizeFull();

        root.addStyleName("root");
        root.setSizeFull();
        buildMainView();
    }

    private void buildMainView() {
        root.addComponent(new VerticalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                Label title = new Label(ProjectType.TICKETS.getName());
                title.addStyleName("tickets-title");
                addComponent(new NavigationBar(title));
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(new TicketsView());
    }

}
