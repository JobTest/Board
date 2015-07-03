package com.pb.dashboard.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

public class BackBar extends HorizontalLayout {

    public BackBar(String url) {
        addStyleName("navigation");
        setWidth("100%");
        setHeight("45px");


        HorizontalLayout left = new HorizontalLayout();
        left.setWidth(null);


        Resource backIcon = new ThemeResource("dash-img/back-button.png");
        Resource linkResource = new ExternalResource(url);
        Link backButton = new Link("", linkResource);
        backButton.setSizeFull();
        backButton.setIcon(backIcon);

        left.addComponent(backButton);
        left.setComponentAlignment(backButton, Alignment.MIDDLE_LEFT);

        addComponent(left);
        setComponentAlignment(left, Alignment.MIDDLE_LEFT);
    }
}
