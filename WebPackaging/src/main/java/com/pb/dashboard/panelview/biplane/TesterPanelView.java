package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.tester.middleware.MiddleWareView;
import com.pb.dashboard.tester.middleware2.MiddleWare2View;
import com.pb.dashboard.tester.testing.TesterView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.Locale;

public class TesterPanelView extends Panel implements View {

    private static final long serialVersionUID = -5117656833188938528L;
    private CssLayout root = new CssLayout();
    private CssLayout content = new CssLayout();
    private TesterView testerView = new TesterView();
    private Component currentContent;
    private MiddleWareView middleWareView = new MiddleWareView();
    private MiddleWare2View middleWare2View = new MiddleWare2View();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.TESTER.getName());
        checkAdress();
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

    private void checkAdress() {
        String[] parsedUrl = Page.getCurrent().getUriFragment().split("/");
        if ("alltests".equals(parsedUrl[parsedUrl.length - 1])) {
            currentContent = testerView;
        }
        if ("middleware".equals(parsedUrl[parsedUrl.length - 1])) {
            currentContent = middleWareView;
        }
        if ("middleware2".equals(parsedUrl[parsedUrl.length - 1])) {
            currentContent = middleWare2View;
        }
    }

    private void buildMainView() {
        root.addComponent(new VerticalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new NavigationBar(getMenuButtons()));
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }

            private static final long serialVersionUID = -6856518175552565796L;
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(currentContent);
    }

    private Button[] getMenuButtons() {
        Button[] buttons = new Button[3];
        buttons[0] = createViewLink("Тесты");
        buttons[1] = createViewLink("MiddleWare");
        buttons[2] = createViewLink("MiddleWare2");
        return buttons;
    }

    public Button createViewLink(final String buttonTitle) {
        Button paymentButton = new Button(buttonTitle);
        paymentButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Navigator navigator = UI.getCurrent().getNavigator();
                if (buttonTitle.equals("Тесты")) {
                    navigator.navigateTo(Dashboard.Biplane.Tests.AllTests.PATH);
                }
                if (buttonTitle.equals("MiddleWare")) {
                    navigator.navigateTo(Dashboard.Biplane.Tests.Middleware.PATH);
                }
                if (buttonTitle.equals("MiddleWare2")) {
                    navigator.navigateTo(Dashboard.Biplane.Tests.Middleware2.PATH);
                }
            }
        });
        return paymentButton;
    }
}
