package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterface;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.external.monitor.chart.ChartBuilder;
import com.pb.dashboard.external.monitor.chart.MonitorChart;
import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.pb.dashboard.external.monitor.logic.ErrorsDataManager;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ErrorsViewPanelManager implements Filterable, Serializable {

    private static final long serialVersionUID = 5131345037672658655L;
    private ErrorsDataManager dataManager;
    private ChartBuilder chartManager;
    private List<MonitorChart> charts = new ArrayList<MonitorChart>();
    private ComboBox paymentsInterfaceMenu;
    private ComboBox templInterfaceMenu;
    private Filter filter;

    public static final DInterfaceI ALL_INTERFACES = new DInterface(-1, "Все интерфейсы", "Все интерфейсы");

    public ErrorsViewPanelManager(ErrorsDataManager dataManager, ChartBuilder chartManager) {
        this.dataManager = dataManager;
        this.chartManager = chartManager;
    }

    // Left Vertical Layout for Errors View
    public Component getPaymentsChartPanel() {
        Map<Integer, DInterfaceI> interfaces = ServiceFactory.getMonitoring().getInterfaces(Complex.BIPLANE_API2X.getId(), Country.UKRAINE.getId());
        paymentsInterfaceMenu = getChartComboBox(new ArrayList<>(interfaces.values()));
        paymentsInterfaceMenu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent event) {
                filter.updateData();
            }
        });
        return createChartPanel(Complex.BIPLANE_API2X.getName(), paymentsInterfaceMenu, getErrSubPanel(Complex.BIPLANE_API2X, (DInterfaceI) paymentsInterfaceMenu.getValue()));
    }

    // Right Vertical Layout for Errors View
    public Component getTemplChartPanel() {
        Map<Integer, DInterfaceI> interfaces = ServiceFactory.getMonitoring().getInterfaces(Complex.TEMPLATES.getId(), Country.UKRAINE.getId());
        templInterfaceMenu = getChartComboBox(new ArrayList<>(interfaces.values()));
        templInterfaceMenu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent event) {
                filter.updateData();
            }
        });
        return createChartPanel(Complex.TEMPLATES.getName(), templInterfaceMenu, getErrSubPanel(Complex.TEMPLATES, (DInterfaceI) templInterfaceMenu.getValue()));
    }

    private ComboBox getChartComboBox(List<DInterfaceI> interfaces) {
        final ComboBox interfMenu = new ComboBox();
        interfMenu.addItem(ALL_INTERFACES);
        interfMenu.setItemCaption(ALL_INTERFACES, ALL_INTERFACES.getName());
        for (DInterfaceI interf : interfaces) {
            interfMenu.addItem(interf);
            interfMenu.setItemCaption(interf, interf.getName());
        }
        interfMenu.setNullSelectionAllowed(false);
        interfMenu.setValue(ALL_INTERFACES);
        interfMenu.setImmediate(true);
        interfMenu.setTextInputAllowed(false);

        return interfMenu;
    }

    public Component getFilterPanel() {
        HorizontalLayout filterPanel = new HorizontalLayout();
        filterPanel.setWidth("100%");
        filterPanel.addStyleName("filter-panel");
        filter = new Filter(this);
        filterPanel.addComponent(filter);
        filterPanel.setComponentAlignment(filter, Alignment.TOP_CENTER);

        return filterPanel;
    }

    /* Errors View Sub Panel */

    private Component getErrSubPanel(Complex complex, DInterfaceI bpInterface) {
        boolean isForAll = false;
        if (bpInterface.getName() != null && "Все интерфейсы".equals(bpInterface.getName())) {
            isForAll = true;
        }
        String sysErrors = "Системные ошибки";  // 500
        String businessErrors = "Бизнес ошибки"; // 400
        String yCategoriesTitle = "К-во ошибок";
        MonitorChart chart400 = chartManager.getChart(businessErrors, yCategoriesTitle, false);
        chartManager.setListeners(chart400);
        chart400.setComplex(complex);
        chart400.setForAllInterfaces(isForAll);
        chart400.setSystemErrorsChart(false);
        chart400.setFilterRange(FilterRange.R10MIN);
        charts.add(chart400);
        MonitorChart chart500 = chartManager.getChart(sysErrors, yCategoriesTitle, false);
        chartManager.setListeners(chart500);
        chart500.setComplex(complex);
        chart500.setForAllInterfaces(isForAll);
        chart500.setSystemErrorsChart(true);
        chart500.setFilterRange(FilterRange.R10MIN);
        charts.add(chart500);
        if (complex == Complex.BIPLANE_API2X) {
            charts.get(0).setComboBox(paymentsInterfaceMenu);
            charts.get(1).setComboBox(paymentsInterfaceMenu);
        }
        if (complex == Complex.TEMPLATES) {
            charts.get(2).setComboBox(templInterfaceMenu);
            charts.get(3).setComboBox(templInterfaceMenu);
        }
        return getSubPanel(chart500, chart400);
    }

    public void fillCharts(FilterRange range, DInterfaceI[] interfaces, Date[] dates) {
        fillPaymentsCharts(range, interfaces[0], dates);
        fillTemplChart(range, interfaces[1], dates);
    }

    private void fillPaymentsCharts(FilterRange range, DInterfaceI interfaces, Date[] dates) {
        chartManager.setChartSeries(charts.get(0), dataManager.getPaymentsBusinessErrors(range, interfaces, dates, "pay"));
        chartManager.setChartSeries(charts.get(1), dataManager.getPaymentsSystemErrors(range, interfaces, dates, "pay"));
    }

    private void fillTemplChart(FilterRange range, DInterfaceI interfaces, Date[] dates) {
        chartManager.setChartSeries(charts.get(2), dataManager.getTemplBusinessErrors(range, interfaces, dates, "tem"));
        chartManager.setChartSeries(charts.get(3), dataManager.getTemplSystemErrors(range, interfaces, dates, "tem"));
    }

    private CssLayout createChartPanel(String title, ComboBox menu, Component component) {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        HorizontalLayout menuLayout = new HorizontalLayout();
        menuLayout.addStyleName("configure");
        menuLayout.addComponent(menu);
        component.setCaption(title);
        panel.addComponents(menuLayout, component);

        return panel;
    }

    private Component getSubPanel(Component... components) {
        VerticalLayout panel = new VerticalLayout();
        panel.addStyleName("inner-panel");
        for (Component c : components)
            panel.addComponent(c);
        return panel;
    }

    @Override
    public void filterUpdated(FilterRange range, Date[] dates) {
        DInterfaceI[] interfaces = new DInterfaceI[2];
        interfaces[0] = (DInterfaceI) paymentsInterfaceMenu.getValue();
        interfaces[1] = (DInterfaceI) templInterfaceMenu.getValue();
        dataManager.loadErrorsViewData(range, interfaces, dates);
        for (MonitorChart chart : charts) {
            chart.setFilterRange(filter.getFilterRange());
            chart.setDates(filter.getDates());
        }
        charChange(interfaces);
        fillCharts(range, interfaces, dates);
    }

    private void charChange(DInterfaceI[] interfaces) {
        if (interfaces[0].getName() != null && "Все интерфейсы".equals(interfaces[0].getName())) {
            charts.get(0).setForAllInterfaces(true);
            charts.get(1).setForAllInterfaces(true);
        } else {
            charts.get(0).setForAllInterfaces(false);
            charts.get(1).setForAllInterfaces(false);
        }
        if (interfaces[1].getName() != null && "Все интерфейсы".equals(interfaces[1].getName())) {
            charts.get(2).setForAllInterfaces(true);
            charts.get(3).setForAllInterfaces(true);
        } else {
            charts.get(2).setForAllInterfaces(false);
            charts.get(3).setForAllInterfaces(false);
        }

    }
}
