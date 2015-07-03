package com.pb.dashboard.itdashbord.errors.errorSession.dater;

import com.pb.dashboard.itdashbord.errors.errorSession.ErrorsSessionFiltersListener;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorsSessionDaterView extends HorizontalLayout {
    private static final String SHOW = "Показать";
    private PopupDateField from;
    private PopupDateField to;
    private Button buttonApply;
    private ErrorsSessionFiltersListener filter;

    public ErrorsSessionDaterView(Date fromDate, Date toDate, ErrorsSessionFiltersListener filter) {
        this.filter = filter;
        setWidth("400px");
        from = createPopupDateField(fromDate);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        filter.setDateFrom(sdf.format(from.getValue()));
        to = createPopupDateField(toDate);
        filter.setDateTo(sdf.format(to.getValue()));
        init();
    }

    private PopupDateField createPopupDateField(Date date) {
        PopupDateField field = new PopupDateField(null,date);
        field.setWidth("110px");
        field.setDateFormat("yyyy-MM-dd");
        field.setImmediate(true);
        field.setTextFieldEnabled(false);
        return field;
    }

    private void init() {
        from.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                filter.setDateFrom(sdf.format(from.getValue()));
            }
        });
        to.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                filter.setDateTo(sdf.format(to.getValue()));
            }
        });
        addComponent(from);
        addComponent(to);
        buttonApply = new Button(SHOW);
        addComponent(buttonApply);
    }

    public PopupDateField getTo() {
        return to;
    }

    public PopupDateField getFrom() {
        return from;
    }
}
