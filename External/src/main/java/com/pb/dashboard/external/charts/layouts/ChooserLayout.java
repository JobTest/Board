package com.pb.dashboard.external.charts.layouts;

import com.pb.dashboard.external.charts.components.DailyCheckBox;
import com.pb.dashboard.external.charts.components.HourlyCheckBox;
import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.pb.dashboard.external.charts.view.DateView;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ResourceReference;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooserLayout extends HorizontalLayout implements View{
    private String dateFrom;
    private CheckBox hourly;
    private CheckBox daily;
    private ChartsFilter filter;
    private DateView dateView;
    private Button link;
    private String dateTo;

    public ChooserLayout(String dateFrom, String dateTo, ChartsFilter filter) {
        setMargin(new MarginInfo(true,true,true,true));
        this.filter = filter;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        init();
    }

    private void init() {
        initRadioButtons();
        initDate();
        initButton();
    }

    private void initButton() {
        link = new Button("Применить");
        link.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Resource res = new ExternalResource(getURL());
                final ResourceReference rr = ResourceReference.create(res, link, "email");
                Page.getCurrent().open(rr.getURL(), null);
            }
        });
        addComponent(link);
    }

    private void initDate() {
        if (filter.isHourly()) {
            SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
            Date now = new Date();
            dateTo = sdf.format(now);
            dateView = new DateView(filter, dateFrom, dateTo);
        }
        else{
            dateView = new DateView(filter, dateFrom, dateTo);
        }
        addComponent(dateView);
    }

    private void initRadioButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addStyleName("range-buttons");
        buttons.setSpacing(true);
        hourly = new HourlyCheckBox(filter);
        daily = new DailyCheckBox(filter);
        buttons.addComponent(hourly);
        buttons.addComponent(daily);
        setListeners();
        addComponent(buttons);
        setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
    }

    private void setListeners() {
        hourly.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setHourly(hourly.getValue());
                repaint();
            }
        });
        daily.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setDaily(daily.getValue());
                repaint();
            }
        });

    }

    private void repaint() {
        removeAllComponents();
        init();
    }

    private String getURL() {
        String URL = "#";
        String[] subCurrentURL = Page.getCurrent().getUriFragment().split("/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        if (filter.isDaily()){
            int i = 0;
            while(!subCurrentURL[i].equals("charts")){
                URL += subCurrentURL[i]+"/";
                i++;
            }
            URL += subCurrentURL[i]+"/";
            URL += subCurrentURL[i+1]+"/";
            URL += "daily/";
            URL += sdf.format(filter.getFrom());
            URL += "/";
            URL += sdf.format(filter.getTo());
            URL += "/";
        }
        if (filter.isHourly()){
            int i = 0;
            while(!subCurrentURL[i].equals("charts")){
                URL += subCurrentURL[i]+"/";
                i++;
            }
            URL += subCurrentURL[i]+"/";
            URL += subCurrentURL[i+1]+"/";
            URL += "hourly/";
            URL += sdf.format(filter.getFrom());
            URL += "/";

        }

        return URL;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
