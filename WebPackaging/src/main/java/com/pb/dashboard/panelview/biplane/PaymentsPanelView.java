package com.pb.dashboard.panelview.biplane;

import com.pb.dashboard.components.NavigationBar;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.model.ProjectType;
import com.pb.dashboard.panelview.PageUnderConstruction;
import com.pb.dashboard.vitrina.view.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.Locale;


public class PaymentsPanelView extends Panel implements View {

    private static final long serialVersionUID = -7585389985781708658L;
    private BiplanePayView biplanePayView = new BiplanePayView();
    private BiplaneWiringView biplaneWiringView = new BiplaneWiringView();
    private SelectionView selectionView = new SelectionView();
    private TimelineView timelineView = new TimelineView();
    private TrendsView trendsView = new TrendsView();
    private PageUnderConstruction pageUnderConstruction = new PageUnderConstruction();
    private Component currentContent;

    CssLayout root = new CssLayout();
    CssLayout content = new CssLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ProjectType.BIPLANE.getName());
        getCurrentAdress();
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

        timelineView.runGetData();
    }

    private void getCurrentAdress() {
        String[] splitedUrl = Page.getCurrent().getUriFragment().split("/");
        if (splitedUrl[splitedUrl.length - 1].equals("pay")) {
            currentContent = biplanePayView;
        }
        if (splitedUrl[splitedUrl.length - 1].equals("operday")) {
            currentContent = biplaneWiringView;
        }
        if (splitedUrl[splitedUrl.length - 1].equals("selection")) {
            currentContent = selectionView;
        }
        if (splitedUrl[splitedUrl.length - 1].equals("timeline")) {
            currentContent = timelineView;
        }
        if (splitedUrl[splitedUrl.length - 1].equals("trends")) {
            currentContent = trendsView;
        }
        if (splitedUrl[splitedUrl.length - 1].equals("page")) {
            currentContent = pageUnderConstruction;
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
        });
    }

    private void initContent() {
        content.setSizeFull();
        content.addStyleName("view-content");
        content.addComponent(currentContent);
    }

    private Button[] getMenuButtons() {
        Button[] buttons = new Button[6];
        buttons[0] = createViewLink("Платежи");
        buttons[1] = createViewLink("Опердень");
        buttons[2] = createViewLink("Выборка");
        buttons[3] = createViewLink("Таймлайн");
        buttons[4] = createViewLink("Тренды");
        buttons[5] = createViewLink("SLA");

        return buttons;
    }

    public Button createViewLink(final String buttonTitle) {
        Button paymentButton = new Button(buttonTitle);

        paymentButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment(getNewLocation(buttonTitle));
            }
        });
        return paymentButton;
    }

    private String getNewLocation(String name) {
        String url = "";
        String[] splitedUrl = Page.getCurrent().getUriFragment().split("/");
        for (int i = 0; i < 2; i++) {
            url += splitedUrl[i] + "/";
        }
        if ("Платежи".equals(name)) {
            url += "pay";
        }
        if ("Опердень".equals(name)) {
            url += "operday";
        }
        if ("Выборка".equals(name)) {
            url += "selection";
        }
        if ("Таймлайн".equals(name)) {
            url += "timeline";
        }
        if ("Тренды".equals(name)) {
            url += "trends";
        }
        if ("SLA".equals(name)) {
            url += "page";
        }
        return url;
    }

}
