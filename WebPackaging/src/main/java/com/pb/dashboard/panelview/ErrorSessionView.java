package com.pb.dashboard.panelview;

import com.pb.dashboard.DashboardUI;
import com.pb.dashboard.components.BackBar;
import com.pb.dashboard.itdashbord.errors.errorSession.ErrorsSessionByIdView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.Locale;

public class ErrorSessionView extends Panel implements View {
    private CssLayout root = new CssLayout();
    private CssLayout content = new CssLayout();
    public static final String TEMPLATES_NAME = "!templates";
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Page.getCurrent().setTitle("Errors");
        setLocale(Locale.US);
        setContent(root);
        setSizeFull();

        root.addStyleName("root");
        root.setSizeFull();
        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);

        buildMainView();
    }

    private void buildMainView() {
        root.addComponent(new VerticalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                String current = DashboardUI.getCurrent().getPage().getLocation().toString();
                String[] split = current.split(TEMPLATES_NAME);
                addComponent(new BackBar(split[0]+TEMPLATES_NAME));
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(new ErrorsSessionByIdView());
    }
}
