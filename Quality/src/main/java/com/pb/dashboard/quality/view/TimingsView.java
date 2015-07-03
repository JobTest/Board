package com.pb.dashboard.quality.view;

import com.pb.dashboard.quality.chart.ChartBuilder;
import com.pb.dashboard.quality.logic.TimingsDisplayDataManager;
import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.Range;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.Date;

public class TimingsView extends VerticalLayout implements View, Filterable {

    private static final long serialVersionUID = 1644334769406612811L;
    private VerticalLayout content;
    private VerticalLayout generalView;
    private VerticalLayout detailedView;
    private VerticalLayout tabSheetPanel;
    private TabSheet tabsheet;
    private ChartBuilder chartBuilder = new ChartBuilder();

    private TimingsDisplayDataManager dataManager = new TimingsDisplayDataManager(chartBuilder);

    public TimingsView() {
        setSizeFull();
        addStyleName("timeline-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);
        setExpandRatio(row, 3);
        row.addComponent(createPanels());
        dataManager.initReport();
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        content = createContent();
        panel.addComponent(content);
        content.setCaption("Тайминги платежей: Украина");
        return panel;
    }

    private VerticalLayout createContent() {
        VerticalLayout dataView = new VerticalLayout();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        dataView.setMargin(true);
        dataView.addComponent(getHeader());
        generalView = buildGeneralView();
        dataView.addComponent(generalView);
        return dataView;
    }

    private VerticalLayout buildGeneralView() {
        VerticalLayout generalChartView = new VerticalLayout();
        generalChartView.addComponent(chartBuilder.getTimingsChart());
        Button more = new Button("Подробнее");
        more.addStyleName("green");
        more.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (detailedView == null) detailedView = buildDetailedView();
                switchViews(generalView, detailedView);
            }
        });
        generalChartView.addComponent(more);
        generalChartView.setComponentAlignment(more, Alignment.MIDDLE_CENTER);
        return generalChartView;
    }

    private VerticalLayout buildDetailedView() {
        tabSheetPanel = new VerticalLayout();
        tabSheetPanel.setSpacing(true);
        Button back = new Button("Общий график");
        back.addStyleName("green");
        back.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                switchViews(detailedView, generalView);
            }
        });
        Filter filter = new Filter(this);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setWidth("100%");
        layout.addComponents(filter, back);
        layout.setComponentAlignment(back, Alignment.TOP_RIGHT);
        layout.setExpandRatio(filter, 11);
        layout.setExpandRatio(back, 1);
        tabsheet = dataManager.initTabsheet();
        tabSheetPanel.addComponents(layout, tabsheet);
        return tabSheetPanel;
    }

    private void switchViews(Component old, Component current) {
        content.removeComponent(old);
        content.addComponent(current);
    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("20px");
        Button configureUA = new Button();
        configureUA.addStyleName("borderless");
        configureUA.addStyleName("small");
        configureUA.addStyleName("icon-only");
        configureUA.addStyleName("configure");
        configureUA.setIcon(new ThemeResource("icons/ua.png"));
        header.addComponent(configureUA);
        header.setComponentAlignment(configureUA, Alignment.MIDDLE_CENTER);
        return header;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public void filterDataUpdated(Channel channel, Range range, Date[] dates) {
        tabSheetPanel.removeComponent(tabsheet);
        tabsheet = dataManager.displayDetailedData(channel, range, dates);
        tabSheetPanel.addComponent(tabsheet);
    }
}
