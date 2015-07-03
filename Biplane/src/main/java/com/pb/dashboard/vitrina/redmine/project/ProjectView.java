package com.pb.dashboard.vitrina.redmine.project;

import com.pb.dashboard.vitrina.redmine.ClickLabel;
import com.pb.dashboard.vitrina.redmine.version.VersionView;
import com.vaadin.ui.*;

import java.util.ArrayList;

public class ProjectView extends Panel {
    private HorizontalLayout layoutVersion;
    private VerticalLayout mainLayout;
    private ArrayList<VersionView> versions;
    private String nameProject;
    private Table table;
    private Panel tablePanel;
    private ClickLabel title;
    private int count;

    public ProjectView(String nameProject, int count) {
        this.nameProject = nameProject;
        this.count = count;
        init();
    }

    private void init() {
        initContent();
        initTitle();
        initVersions();
        initTable();
    }

    private void initContent() {
        CssLayout content = new CssLayout();
        content.setSizeFull();

        setWidth("1100px");
        setHeight("450px");

        setContent(content);
        setStyleName("redmine-css");
        initMainLayout();
        content.addComponent(mainLayout);
    }

    private void initMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.setStyleName("redmine-layout");
        mainLayout.setHeight("95%");
        mainLayout.setWidth("90%");
    }

    private void initTitle() {
        title = new ClickLabel("<span style='font-size:36px;'>" + nameProject + " </span><span style='color:red;font-size:30px;'> (" + count + ")</span>");
        title.setWidth("100%");
        title.setStyleName("redmine-title");
        mainLayout.addComponent(title);
        mainLayout.setComponentAlignment(title, Alignment.TOP_CENTER);
        mainLayout.setExpandRatio(title, 1.3f);
    }

    private void initVersions() {
        layoutVersion = new HorizontalLayout();
        layoutVersion.setWidth("110%");
        layoutVersion.setHeight("100%");
        mainLayout.addComponent(layoutVersion);
        mainLayout.setComponentAlignment(layoutVersion, Alignment.TOP_CENTER);
        mainLayout.setExpandRatio(layoutVersion, 10f);
        versions = new ArrayList<VersionView>(3);
    }

    private void initTable() {
        tablePanel = new Panel();
        tablePanel.setWidth("110%");
        tablePanel.setHeight("320px");
        mainLayout.addComponent(tablePanel);
        mainLayout.setComponentAlignment(tablePanel, Alignment.TOP_CENTER);
        mainLayout.setExpandRatio(tablePanel, 1f);
        tablePanel.setVisible(false);

        table = new Table();

        table.addStyleName("plain");
        table.addStyleName("borderless");
        table.addStyleName("redmine-view");

        table.setWidth("95%");
        table.setHeight("95%");

        table.addContainerProperty("ID", String.class, null);
        table.setColumnWidth("ID", 100);
        table.addContainerProperty("Название", String.class, null);
        table.setColumnWidth("Название", 650);
        table.addContainerProperty("Тип", String.class, null);
        table.setColumnWidth("Тип", 100);
        table.addContainerProperty("URL", Link.class, null);
        table.setColumnWidth("URL", 100);
        table.setColumnAlignments(Table.Align.CENTER, Table.Align.CENTER, Table.Align.CENTER, Table.Align.CENTER);

        tablePanel.setContent(table);
        table.setVisible(true);
    }

    public void open(int count) {
        if (count <= 0) {
            mainLayout.setExpandRatio(tablePanel, 1f);
            setHeight("450px");
        } else {
            mainLayout.setExpandRatio(tablePanel, 9f);
            setHeight(700, Unit.PIXELS);
        }
    }

    public Table getTable() {
        return table;
    }

    public void addView(VersionView c) {
        versions.add(c);
        layoutVersion.addComponent(c);
        layoutVersion.setComponentAlignment(c, Alignment.TOP_CENTER);
    }

    public Panel getTablePanel() {
        return tablePanel;
    }

    public ClickLabel getTitle() {
        return title;
    }
}