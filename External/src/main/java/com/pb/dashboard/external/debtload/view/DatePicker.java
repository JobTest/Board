package com.pb.dashboard.external.debtload.view;

import com.pb.dashboard.external.debtload.view.load.LoadViewPanelManger;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

import java.util.Date;
import java.util.Locale;

public class DatePicker extends HorizontalLayout {

    private PopupDateField dateSelect;
    private LoadViewPanelManger panelManger;

    private Date date;

    public DatePicker(LoadViewPanelManger panelManger) {
        this.panelManger = panelManger;
        setSpacing(true);

        date = new Date();

        dateSelect = createPopUpDateField(date, date);
        dateSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                update();
            }
        });

        Label loadDateLabel = new Label("Дата загрузки: ");
        loadDateLabel.addStyleName("load-date");
        addComponents(loadDateLabel, dateSelect);
    }

    private PopupDateField createPopUpDateField(Date valueDate, Date rangeEnd) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setLocale(new Locale("RU"));
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(valueDate);
        field.setRangeEnd(rangeEnd);
        field.setImmediate(true);
        return field;
    }

    private void update() {
        date = dateSelect.getValue();
        panelManger.update();
    }

    public Date getDate() {
        return date;
    }

}
