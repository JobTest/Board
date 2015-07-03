package com.pb.dashboard.components;

import com.pb.dashboard.DashboardUI;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NavigationBar extends HorizontalLayout {

    private Link backButton;

    public NavigationBar(Component... components) {
        addStyleName("navigation");
        setWidth("100%");
        setHeight("45px");

        // Back button
        HorizontalLayout left = new HorizontalLayout();
        left.setWidth(null);

        URI location = DashboardUI.getCurrent().getPage().getLocation();
        String mainPath = location.getScheme() + ":" + location.getSchemeSpecificPart();
        String uri = location.toString();
        if (uri.contains("/")) {
            if (uri.lastIndexOf("/") < mainPath.length()) {
                if (!mainPath.endsWith("/")) {
                    mainPath += "/";
                }
                uri = mainPath + "#!";
            } else {
                uri = uri.substring(0, uri.lastIndexOf("/"));
                if (!uri.substring(uri.lastIndexOf("#")).equals("#!biplane")) {
                    uri = uri.substring(0, uri.lastIndexOf("/"));
                }

            }
        }
        Resource backIcon = new ThemeResource("dash-img/back-button.png");
        Resource linkResource = new ExternalResource(uri);
        backButton = new Link("", linkResource);
        backButton.setSizeFull();
        backButton.setIcon(backIcon);

        left.addComponent(backButton);
        left.setComponentAlignment(backButton, Alignment.MIDDLE_LEFT);

        // Main menu
        HorizontalLayout menu = new HorizontalLayout();
        menu.addStyleName("menu-horizontal");
        for (Component component : components) {
            if (component != null)
                menu.addComponent(component);
        }

        // Current Date
        HorizontalLayout right = new HorizontalLayout();
        right.setWidth(null);
        Label currentDate = new Label(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        currentDate.setWidth(null);
        currentDate.addStyleName("date");
        right.addComponent(currentDate);
        right.setComponentAlignment(currentDate, Alignment.MIDDLE_RIGHT);

        addComponent(left);
        setComponentAlignment(left, Alignment.MIDDLE_CENTER);
        addComponent(menu);
        setComponentAlignment(menu, Alignment.TOP_CENTER);
        setExpandRatio(menu, 1.0f);
        addComponent(right);
    }

    public void setUriBack(String linkBack) {
        backButton.setResource(new ExternalResource(linkBack));
    }
}