package com.pb.dashboard.vitrina.view;

import com.pb.dashboard.vitrina.CountryButtons;
import com.pb.dashboard.vitrina.core.config.ConfigManager;
import com.pb.dashboard.vitrina.core.config.Language;
import com.pb.dashboard.vitrina.core.country.CountrySelector;
import com.pb.dashboard.vitrina.core.table.TableBuilder;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.selection.SelectionManager;
import com.pb.dashboard.vitrina.selection.SelectionViewChartManager;
import com.pb.dashboard.vitrina.selection.SelectionViewFilter;
import com.vaadin.addon.charts.Chart;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.addon.borderlayout.BorderLayout;

import java.util.Date;
import java.util.List;

public class SelectionView extends VerticalLayout implements View, CountrySelector {

    private ConfigManager configManager;
    private SelectionManager selectionManager = new SelectionManager();
    private TableBuilder builder = new TableBuilder();

    private SelectionViewChartManager chartManager = new SelectionViewChartManager();
    private CountryButtons countryButtons;
    private Component content;
    private SelectionViewFilter filter;
    private VerticalLayout charts;
    private Table table;

    private Chart regularChart;
    private Chart compChart;

    private final String[] columnNames = {"Период", "Прием", "Наличные", "Безналичные", "Задолженность",
            "Физ.лица", "Юр.лица"};

    public SelectionView() {
        setConfigManager();

        setSizeFull();
        addStyleName("selection-view");
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(true);
        addComponent(row);

        setExpandRatio(row, 3);
        row.addComponent(createPanels());

        loadInitialData();
    }

    private void loadInitialData() {
        selectionManager.collectData(new Date());
        selectionManager.updateByType(StatEnum.ALL);
        fillTable(selectionManager.getTotal(), selectionManager.getPayments());
        setRegularChartSeries(selectionManager.getRegularChartData(), selectionManager.getMainDateTotals(),
                selectionManager.getDate());
    }

    private void setConfigManager() {
        configManager = new ConfigManager();
        selectionManager.setConfigManager(configManager);
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        panel.addStyleName("layout-panel");
        content = createContent();
        panel.addComponent(content);
        content.setCaption("Выборка: Украина");
        return panel;
    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("20px");
        countryButtons = new CountryButtons(this);
        header.addComponent(countryButtons);
        header.setComponentAlignment(countryButtons, Alignment.MIDDLE_CENTER);
        return header;
    }

    private CssLayout createPanel(Component content) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        panel.addComponent(content);
        return panel;
    }

    private VerticalLayout createContent() {
        BorderLayout dataView = new BorderLayout();
        dataView.setStyleName("data-view");
        dataView.setSpacing(true);
        initTable();
        initCharts();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName("tabsheet");
        tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
        tabSheet.setWidth("100%");
        tabSheet.addTab(buildChartsLayout(), "График");
        tabSheet.addTab(createPanel(table), "Таблица");
        filter = new SelectionViewFilter(this, selectionManager);
        dataView.addComponent(getHeader(), BorderLayout.Constraint.NORTH);
        dataView.addComponent(filter, BorderLayout.Constraint.WEST);
        dataView.addComponent(tabSheet, BorderLayout.Constraint.CENTER);
        return dataView;
    }

    private void initTable() {
        table = builder.buildTableproperty(false, columnNames, "");
        table.addStyleName("selection");
        table.setCellStyleGenerator(selectionManager.getCellStyleGenerator());
    }

    public void fillTable(String total, List<Object[]> rows) {
        table.removeAllItems();
        for (int i = 0; i < rows.size(); i++) {
            table.addItem(rows.get(i), i + 1);
        }
        for (int i = 0; i < columnNames.length; i++) {
            if (i == 5) table.setColumnFooter(columnNames[i], "Всего платежей");
            else if (i == 6) table.setColumnFooter(columnNames[i], total);
            else table.setColumnFooter(columnNames[i], "");
        }
    }

    private VerticalLayout buildChartsLayout() {
        charts = new VerticalLayout();
        charts.setSpacing(true);
        charts.addComponent(regularChart);
        charts.addComponent(compChart);
        return charts;
    }

    private void initCharts() {
        regularChart = chartManager.getRegularChart();
        compChart = chartManager.getCompChart();
    }

    public void setLang(Language lang) {
        configManager.setLang(lang);
    }

    public void setCaption(String caption) {
        content.setCaption("Выборка: " + caption);
    }

    public void setComparisonChartSeries(List<Date> dates, List<List<Number>> regChartData, List<Number> compChartMainDate,
                                         List<Number> compChartCompDate) {
        chartManager.setComparisonChartSeries(dates, regChartData, compChartMainDate, compChartCompDate);
    }

    public void setRegularChartSeries(List<List<Number>> list, List<Number> totals, Date date) {
        chartManager.setRegularChartSeries(list.get(0), list.get(1), list.get(2), totals, date);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    @Override
    public void updateCountry(Language lang) {
        configManager.setLang(lang);
        content.setCaption("Выборка: " + lang.getName());
    }

    @Override
    public void updateData() {
        filter.updateByMainDate();
    }
}
