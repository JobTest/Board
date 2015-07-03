package com.pb.dashboard.core.layout;

import com.pb.dashboard.core.component.namemapping.NameMappingComponents;
import com.pb.dashboard.core.hierarchy.Dashboard;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;

public class DashboardElementsLayout extends ElementsLayout {

    private static final long serialVersionUID = 5147599238788407550L;
    private final static int COLUMNS = 3;
    private final static int ROWS = 3;

    public DashboardElementsLayout() {
        super();
        setMargin(false);
        setSpacing(false);

        setColumns(COLUMNS);
        setRows(ROWS);

        // set the size for the grid
        setWidth("95%");
        setHeight("700px");

        addLink("biplane.png", Dashboard.Biplane.PATH, 0, 0, NameMappingComponents.TestLink.BIPLANE);
        addLink("templates.png", Dashboard.Templates.PATH, 2, 0, NameMappingComponents.TestLink.TEMPLATES);

        ThemeResource resource = new ThemeResource("dash-img/logo.png");
        Image logo = new Image("", resource);
        logo.addStyleName("logo");
        addComponent(logo, 1, 1);
        setComponentAlignment(logo, Alignment.MIDDLE_CENTER);

        addLink("tickets.png", Dashboard.Tickets.PATH, 2, 2, NameMappingComponents.TestLink.TICKETS);
        addLink("quality.png", Dashboard.Quality.PATH, 0, 2, NameMappingComponents.TestLink.QUALITY);

    }
}