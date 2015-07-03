package com.pb.dashboard.external.charts.view;

import com.pb.dashboard.external.charts.components.CountryLink;
import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.pb.dashboard.external.charts.layouts.ChartsLayout;
import com.pb.dashboard.external.charts.layouts.ChooserLayout;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class PageView extends VerticalLayout {
    private String dateFrom;
    private ChartsFilter filter;
    private ChooserLayout chooser;
    private ChartsLayout charts;
    private String dateTo;
    private int bank;

    public PageView(int bank, String dateFrom, String dateTo, String filterValue) {
        this.bank=bank;
        filter = new ChartsFilter(bank,filterValue);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        init();
    }

    private void init() {
        initHeader();
        initChart();
    }

    private void initChart() {
        setSpacing(false);
        charts = new ChartsLayout(filter);
        HorizontalLayout top = new HorizontalLayout();
        top.setSizeFull();
        top.setMargin(new MarginInfo(false,false,true,false));
        top.addComponent(charts.getChart(true));
        addComponent(top);
        HorizontalLayout bot = new HorizontalLayout();
        bot.setSizeFull();
        bot.setMargin(new MarginInfo(false, false, true, false));
        bot.addComponent(charts.getChart(false));
        addComponent(bot);

    }

    private void initHeader() {
        initTitle();
        initChooser();
    }

    private void initTitle() {
        HorizontalLayout title = new HorizontalLayout();
        title.setWidth("100%");
        Label country = new Label();
        if (bank == 0) {
            country.setValue("UKRAINE");
        }
        if (bank == 1) {
            country.setValue("RUSSIA");
        }
        title.addComponent(country);
        title.setComponentAlignment(country, Alignment.TOP_LEFT);

        Link changeCountry = new CountryLink(Page.getCurrent().getUriFragment());
        title.addComponent(changeCountry);
        title.setComponentAlignment(changeCountry, Alignment.TOP_RIGHT);
        title.setSizeFull();
        addComponent(title);
    }

    private void initChooser() {
        chooser = new ChooserLayout(dateFrom, dateTo, filter);
        HorizontalLayout chooseComponent = new HorizontalLayout();
        chooseComponent.addComponent(chooser);
        chooseComponent.setComponentAlignment(chooser, Alignment.MIDDLE_CENTER);
        addComponent(chooseComponent);
        setComponentAlignment(chooseComponent, Alignment.MIDDLE_CENTER);
    }
}