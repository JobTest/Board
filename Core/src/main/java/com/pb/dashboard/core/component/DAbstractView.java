package com.pb.dashboard.core.component;

import com.pb.dashboard.core.component.navigation.NavigationBar;
import com.pb.dashboard.core.util.UrlParamBuilder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by vlad
 * Date: 01.10.14
 */
public abstract class DAbstractView extends VerticalLayout implements View {

    private static final Logger LOG = Logger.getLogger(DAbstractView.class);
    private static final String ERROR = "Error";
    private final Map<String, String> params = new LinkedHashMap<String, String>();

    protected VerticalLayout content;
    protected String redirectUrl = "";
    protected NavigationBar navigationBar;

    protected DAbstractView() {
        initNavigation();
        initContent();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            initParams(event);
            dashEnter(event);
        } catch (Exception e) {
            error(e);
        }
    }

    private void initNavigation() {
        navigationBar = new NavigationBar();
        addComponent(navigationBar);
    }

    private void initContent() {
        addStyleName("timings-view");
        setSizeFull();
        setLocale(Locale.US);

        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(false, true, false, true));
        row.setSpacing(false);
        addComponent(row);
        setExpandRatio(row, 2);

        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        row.addComponent(panel);

        content = new VerticalLayout();
        panel.addComponent(content);
    }

    private void error(Exception e) {
        Page.getCurrent().setTitle(ERROR);
        addStyleName("timings-view");
        LOG.error(e.getMessage(), e);
        content.removeAllComponents();
        content.addComponent(new ErrorLayout(e, redirectUrl));
    }

    private void initParams(ViewChangeListener.ViewChangeEvent event) {
        params.clear();
        params.putAll(UrlParamBuilder.parseParams(event.getParameters()));
    }

    public Button createViewLink(String title, final String navigationState) {
        Button button = new Button(title);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(navigationState);
            }
        });
        return button;
    }

    public Map<String, String> getParameters() {
        return params;
    }

    public abstract void dashEnter(ViewChangeListener.ViewChangeEvent event);
}
