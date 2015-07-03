package com.pb.dashboard.external.charts.view;

import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateView extends HorizontalLayout implements View{
    private ChartsFilter filter;
    private SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
    private String dateFrom;
    private String dateTo;

    public DateView(ChartsFilter filter, String dateFrom, String dateTo) {
        addStyleName("date-pickers");
        setSpacing(true);

        setMargin(new MarginInfo(false,true,false,true));
        this.filter = filter;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        init();
    }

    private void init() {
        if (filter.isDaily()){
            addDailyComponents();}
        if (filter.isHourly()){
            addHourlyComponents();
        }
    }

    private void addHourlyComponents() {
        Date now = new Date();
        PopupDateField from = null;
        try {
            from = createPopUpDateField(sdf.parse(dateFrom), now);
            filter.setFrom(sdf.parse(dateFrom));
            filter.setTo(now);
            final PopupDateField finalFrom = from;
            from.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    filter.setFrom(finalFrom.getValue());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }

        addComponent(from);
    }

    private void addDailyComponents() {
        Date now = new Date();
        PopupDateField from = null;
        PopupDateField to = null;
        try {
            to = createPopUpDateField(sdf.parse(dateTo), now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert to != null;
        final PopupDateField finalTo = to;
        to.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setTo(finalTo.getValue());
            }
        });
        try {
            from = createPopUpDateField(sdf.parse(dateFrom), now);
            filter.setFrom(sdf.parse(dateFrom));
            filter.setTo(sdf.parse(dateTo));

            final PopupDateField finalFrom = from;
            from.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    filter.setFrom(finalFrom.getValue());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


        addComponent(from);
        addComponent(to);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private PopupDateField createPopUpDateField(Date valueDate, Date rangeEnd) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setLocale(new Locale("RU"));
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(valueDate);
        field.setRangeEnd(rangeEnd);
        field.setImmediate(true);
        field.setTextFieldEnabled(false);
        return field;
    }
}
