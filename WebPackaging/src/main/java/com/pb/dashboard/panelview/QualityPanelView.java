package com.pb.dashboard.panelview;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.quality.view.RejectedPaymentsView;
import com.pb.dashboard.quality.view.TimingsView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.Locale;

public class QualityPanelView extends Panel implements View {

    RejectedPaymentsView rejectedPaymentsView;
    CssLayout root = new CssLayout();
    CssLayout content = new CssLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.QUALITY.getName());
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
                addComponent(new NavigationBar(getMenuButtons()));
                addComponent(content);
                initContent();
                setExpandRatio(content, 1);
            }
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(rejectedPaymentsView);
    }

    private Button[] getMenuButtons() {
        Button[] buttons = new Button[2];
        //buttons[] = createViewLink("Статистика", new PaymentsQualityView());
        rejectedPaymentsView = new RejectedPaymentsView();
        buttons[0] = createViewLink("Забракованные", rejectedPaymentsView);
        buttons[1] = createViewLink("Тайминги", new TimingsView());
        return buttons;
    }

    public Button createViewLink(String buttonTitle, final Component component) {
        Button paymentButton = new Button(buttonTitle);
        paymentButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                content.removeAllComponents();
                content.addComponent(component);
            }
        });
        return paymentButton;
    }
}

