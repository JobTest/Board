package com.pb.dashboard.monitoring.errors.panel;

import com.pb.dashboard.core.component.DAbstractView;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.core.view.wrapper.Wrapper;
import com.pb.dashboard.core.view.wrapper.WrapperSide;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

/**
 * Created by vlad
 * Date: 19.12.14_12:38
 */
public class ErrorsView extends DAbstractView {

    private static final String ERRORS = "Ошибки";
    private static final String TITLE_OUTER = "Outer session";
    private static final String TITLE_ERRORS = "Errors";
    private static final String URL_ERRORS = "http://qa-portal.nc.pb.ua/biplaneErCodes/";
    private ErrorsController controller;

    @Override
    public void dashEnter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle(ERRORS);
        init(event.getParameters());
    }

    private void init(String params) {
        if (controller == null) {
            controller = new ErrorsController(this, getParameters());
        }
        controller.setParameters(params);
        initNavigator();
        initView();
    }

    private void initView() {
        content.removeAllComponents();
        content.addComponent(controller.getView());
    }

    private void initNavigator() {
        navigationBar.setBack(Dashboard.Biplane.Monitoring.PATH);
        HorizontalLayout menu = navigationBar.getMenu();
        menu.removeAllComponents();
        Component menuContent = createMenuContent();
        menu.addComponent(menuContent);
        menu.setComponentAlignment(menuContent, Alignment.TOP_CENTER);
    }

    private Component createMenuContent() {
        HorizontalLayout layout = new HorizontalLayout();
        Button allButton = createAllButton();
        layout.addComponent(allButton);
        layout.setComponentAlignment(allButton, Alignment.TOP_CENTER);
        if (controller.haveOuter()) {
            Button outerButton = createOuterButton();
            layout.addComponent(outerButton);
            layout.setComponentAlignment(outerButton, Alignment.TOP_CENTER);
        }

        Link link = new Link(ERRORS, new ExternalResource(URL_ERRORS));
        Component wrap = Wrapper.wrap(WrapperSide.LEFT, link);
        layout.addComponent(wrap);
        layout.setComponentAlignment(wrap, Alignment.MIDDLE_RIGHT);
        return layout;
    }

    private Button createOuterButton() {
        return createViewLink(TITLE_OUTER, controller.getOuterPath());
    }

    private Button createAllButton() {
        return createViewLink(TITLE_ERRORS, controller.getAllPath());
    }

    public VerticalLayout getContent() {
        return content;
    }
}