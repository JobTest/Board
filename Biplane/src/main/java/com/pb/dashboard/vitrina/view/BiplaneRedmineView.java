package com.pb.dashboard.vitrina.view;

import com.pb.dashboard.vitrina.redmine.Redmine;
import com.pb.dashboard.vitrina.redmine.project.ProjectController;
import com.pb.dashboard.vitrina.redmine.project.ProjectView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

public class BiplaneRedmineView extends VerticalLayout implements View {
    private static final long serialVersionUID = -1744455941100836080L;

    public BiplaneRedmineView() {
        setSizeFull();
        addStyleName("past-css");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);

        Redmine redmineCharts = new Redmine();
        ArrayList<ProjectController> projects = redmineCharts.getProjects();

        addComponent(row);

        setExpandRatio(row, 1);
        CssLayout panels = createPanels(projects);
        row.addComponent(panels);
        row.setComponentAlignment(panels, Alignment.MIDDLE_CENTER);
    }

    private CssLayout createPanels(ArrayList<ProjectController> projects) {
        CssLayout panel = new CssLayout();
        for (int i = 0; i < projects.size(); i++) {
            ProjectView cellPanel = projects.get(i).getView();
            panel.addComponent(cellPanel);
            cellPanel.addStyleName("redmine-margin");
        }
        panel.setHeight("100%");
        panel.setWidth("1150px");
        panel.addStyleName("layout-panel");
        panel.addStyleName("redmine-scroll");
        return panel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}