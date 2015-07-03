package com.pb.dashboard.itdashbord.errors.error.title.dater;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DaterView extends HorizontalLayout {

    private static final String SHOW = "Показать";
    private PopupDateField from;
    private PopupDateField to;
    private Button buttonApply;
    private ErrorsFiltersListener filter;

    public PopupDateField getTo() {
        return to;
    }

    public PopupDateField getFrom() {
        return from;
    }

    public DaterView(ErrorsFiltersListener filter) {
        this.filter=filter;
        setWidth("400px");
        init();

    }

    private void init() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy M d");

        Calendar instance = Calendar.getInstance();
        from = createPopupDateField(instance);
        addComponent(from);
        to = createPopupDateField(instance);
        addComponent(to);

        filter.setDateFrom((sdf.format(from.getValue())));
        filter.setDateTo((sdf.format(to.getValue())));

        buttonApply = new Button(SHOW);
        addComponent(buttonApply);
    }

    private PopupDateField createPopupDateField(Calendar calendar) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(calendar.getTime());
        field.setImmediate(true);
        field.setTextFieldEnabled(false);

        return field;
    }


}
