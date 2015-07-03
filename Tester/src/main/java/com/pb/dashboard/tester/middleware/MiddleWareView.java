package com.pb.dashboard.tester.middleware;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class MiddleWareView extends VerticalLayout implements View {

    private Component content;
    private TableManager tableManager = new TableManager();
    private DataManager dataManager = new DataManager();

    public MiddleWareView() {
        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        row.addComponent(createPanels());
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        content = createContent();
        panel.addComponent(content);
        content.setCaption("Мониторинг");
        return panel;
    }

    private Component createContent() {
        VerticalLayout dataView = new VerticalLayout();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);
        Table easTable = tableManager.buildTable();
        tableManager.fillTable(easTable, dataManager.getEasTableData());
        Table gfTable = tableManager.buildTable();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName("middleware");
        tabSheet.addTab(easTable, "EAS");
        tabSheet.addTab(gfTable, "GlassFish");
        dataView.addComponent(tabSheet);
        return dataView;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
