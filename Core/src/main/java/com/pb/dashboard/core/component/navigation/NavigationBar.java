package com.pb.dashboard.core.component.navigation;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NavigationBar extends HorizontalLayout {

    private HorizontalLayout menu;
    private Link backButton;

    public NavigationBar() {
        init();
    }

    private void init() {
        addStyleName("navigation");
        setWidth("100%");
        setHeight("45px");

        initLeftPanel();
        initMenu();
        initRightPanel();
    }

    private void initLeftPanel() {
        HorizontalLayout left = new HorizontalLayout();

        String urlBack = getURLBack();
        Resource linkResource = new ExternalResource(urlBack);
        backButton = new Link(null, linkResource);
        backButton.setStyleName("back");
        backButton.setSizeFull();
        setType(NavigationBarType.BACK);

        left.addComponent(backButton);
        left.setComponentAlignment(backButton, Alignment.MIDDLE_LEFT);

        addComponent(left);
        setComponentAlignment(left, Alignment.MIDDLE_CENTER);
    }

    private String getURLBack() {
        URI location = Page.getCurrent().getLocation();
        String mainPath = location.getScheme() + ':' + location.getSchemeSpecificPart();
        String uri = location.toString();
        if (uri.contains("/")) {
            if (uri.lastIndexOf('/') < mainPath.length()) {
                if (!mainPath.endsWith("/")) {
                    mainPath += '/';
                }
                uri = mainPath + "#!";
            } else {
                uri = uri.substring(0, uri.lastIndexOf('/'));
                if (!uri.substring(uri.lastIndexOf('#')).equals("#!biplane")) {
                    uri = uri.substring(0, uri.lastIndexOf('/'));
                }

            }
        }
        return uri;
    }

    private void initMenu() {
        menu = new HorizontalLayout();
        menu.setSizeFull();
        menu.addStyleName("menu-horizontal");
        addComponent(menu);
        setComponentAlignment(menu, Alignment.MIDDLE_CENTER);
        setExpandRatio(menu, 1.0f);
    }

    private void initRightPanel() {
        HorizontalLayout right = new HorizontalLayout();

        Label currentDate = new Label(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        currentDate.addStyleName("date");
        right.addComponent(currentDate);
        right.setComponentAlignment(currentDate, Alignment.MIDDLE_RIGHT);

        addComponent(right);
        setComponentAlignment(right, Alignment.MIDDLE_CENTER);
    }

    public HorizontalLayout getMenu() {
        return menu;
    }

    public void setType(NavigationBarType type) {
        Resource icon = new ThemeResource(type.getPicture());
        backButton.setIcon(icon);
    }

    public void setBack(String back) {
        backButton.setResource(new ExternalResource("#!" + back));
    }
}