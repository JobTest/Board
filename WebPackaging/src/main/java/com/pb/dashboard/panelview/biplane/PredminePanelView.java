package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.vitrina.view.BiplaneRedmineView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.Locale;

public class PredminePanelView extends Panel implements View {

    private CssLayout root = new CssLayout();
    private CssLayout content = new CssLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.BIPLANE.getName());
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
                addComponent(new NavigationBar());
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(new BiplaneRedmineView());
    }
}