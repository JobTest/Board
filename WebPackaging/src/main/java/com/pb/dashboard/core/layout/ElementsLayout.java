package com.pb.dashboard.core.layout;

import com.pb.dashboard.core.component.namemapping.ComponentNameI;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Link;

public abstract class ElementsLayout extends GridLayout {

    protected Link addLink(String namePict, String linkS, int column, int row, ComponentNameI componentMapping) {
        Resource linkRes = new ExternalResource("#!" + linkS);
        Resource iconRes = new ThemeResource("buttons-img/" + namePict);
        Link link = new Link("", linkRes);
        link.setIcon(iconRes);
        link.setId(componentMapping.getName());
        addComponent(link, column, row);
        setComponentAlignment(link, Alignment.MIDDLE_CENTER);
        return link;
    }

    protected Link addLink(String namePict, String linkS, int column, int row) {
        Resource linkRes = new ExternalResource("#!" + linkS);
        Resource iconRes = new ThemeResource("buttons-img/" + namePict);
        Link link = new Link("", linkRes);
        link.setIcon(iconRes);
        addComponent(link, column, row);
        setComponentAlignment(link, Alignment.MIDDLE_CENTER);
        return link;
    }
}
