package com.pb.dashboard.panelview;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.itdashbord.AuthorizationView;
import com.pb.dashboard.itdashbord.DashboardView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import java.util.Locale;

//import com.pb.dashboard.itdashbord.ErrorsView;

@SuppressWarnings("serial")
public class TemplatesPanelView extends Panel implements View {

    private CssLayout root = new CssLayout();
    private CssLayout content = new CssLayout();
    private DashboardView dashboardView = new DashboardView();
    private AuthorizationView authorizationView = new AuthorizationView();
    //private ErrorsView errorsView = new ErrorsView();
    private Component currentComponent;

    @Override
    public void enter(ViewChangeEvent event) {
        checkLocation();
        Page.getCurrent().setTitle(ProjectType.TEMPLATES.getName());
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

    private void checkLocation() {
        String[] currentUrl = Page.getCurrent().getUriFragment().split("/");
        boolean flag = false;
        if (currentUrl.length == 1) {
            currentComponent = dashboardView;
            Page.getCurrent().setUriFragment(Page.getCurrent().getUriFragment() + "/pay");
            flag = true;
        }
        if (currentUrl[currentUrl.length - 1].equals("pay")) {
            currentComponent = dashboardView;
            flag = true;
        }
        if (currentUrl[currentUrl.length - 1].equals("authorization")) {
            currentComponent = authorizationView;
            flag = true;
        }
//        if (currentUrl[currentUrl.length - 1].equals("errors")){
//            currentComponent = errorsView;
//            flag = true;
//        }
        if (!flag) {
            currentComponent = dashboardView;
            Page.getCurrent().setUriFragment(currentUrl[0] + "/pay");
        }
    }

    private void buildMainView() {
        root.addComponent(new VerticalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                NavigationBar navigator = new NavigationBar(getMenuButtons());

                navigator.setUriBack(Dashboard.PATH + "#!");
                addComponent(navigator);
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(currentComponent);
    }

    private Button[] getMenuButtons() {
        Button[] buttons = new Button[2];
        buttons[0] = createViewLink("Платежи");
        buttons[1] = createViewLink("Авторизация");
        //buttons[2] = createViewLink("Oшибки");
        return buttons;
    }

    public Button createViewLink(String buttonTitle) {
        Button paymentButton = new Button(buttonTitle);
        String url = "";
        String[] currentUrl = Page.getCurrent().getUriFragment().split("/");
        for (int i = 0; i < currentUrl.length - 1; i++) {
            url += currentUrl[i] + "/";
        }
        if ("Платежи".equals(buttonTitle)) {
            url += "pay";
        }
        if ("Авторизация".equals(buttonTitle)) {
            url += "authorization";
        }
        if ("Oшибки".equals(buttonTitle)) {
            url += "errors";
        }
        final String finalUrl = url;
        paymentButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Page.getCurrent().setUriFragment(finalUrl);
            }
        });
        return paymentButton;
    }
}