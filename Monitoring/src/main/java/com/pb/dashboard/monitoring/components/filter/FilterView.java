package com.pb.dashboard.monitoring.components.filter;

import com.pb.dashboard.core.view.wrapper.Wrapper;
import com.pb.dashboard.core.view.wrapper.WrapperSide;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.util.Calendar;

public class FilterView extends HorizontalLayout implements Observer<FilterModelI> {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String REGLAMENT_TIME = "Регламентное время";
    private static final String APPLY = "Применить";
    private static final long serialVersionUID = -899392154588042386L;

    private FilterControllerI controller;
    private FilterModelI model;

    private OptionGroup optionGroup;
    private PopupDateField dateField;
    private PopupDateField fromField;
    private PopupDateField toField;
    private CheckBox reglamentBox;
    private Button submitButton;

    public FilterView(FilterControllerI controller, FilterModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        addStyleName("horizontal-filter");
        initRangeButtons();
        initDatePickers();
        initReglamentBox();
        initButtonSubmit();
        setFilterRange(model.getFilterRange());
    }

    private void initRangeButtons() {
        optionGroup = new OptionGroup();
        optionGroup.addStyleName("horizontal");
        optionGroup.setImmediate(true);
        for (FilterRange range : FilterRange.values()) {
            optionGroup.addItem(range);
            optionGroup.setItemCaption(range, range.getName());
        }
        optionGroup.setValue(model.getFilterRange());
        optionGroup.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                setFilterRange((FilterRange) optionGroup.getValue());
            }
        });
        addComponent(optionGroup);
    }

    private void initDatePickers() {
        dateField = createPopupDateField(model.getDate());
        fromField = createPopupDateField(model.getDateFrom());
        toField = createPopupDateField(model.getDateTo());
        addComponent(Wrapper.wrap(WrapperSide.LEFT, dateField, fromField, toField));
    }

    private PopupDateField createPopupDateField(LocalDate valueDate) {
        Calendar now = Calendar.getInstance();
        PopupDateField field = new PopupDateField();
        field.addStyleName("date-pickers");
        field.setTextFieldEnabled(false);

        field.setWidth("110px");
        field.setDateFormat(DATE_PATTERN);
        if (valueDate != null) {
            field.setValue(valueDate.toDate());
        }
        field.setRangeEnd(now.getTime());
        field.setImmediate(true);
        return field;
    }

    private void setFilterRange(FilterRange range) {
        boolean rangeDay = range == FilterRange.DAY;
        toField.setVisible(rangeDay);
        fromField.setVisible(rangeDay);
        dateField.setVisible(!rangeDay);
        reglamentBox.setVisible(!rangeDay);
    }

    private void initReglamentBox() {
        reglamentBox = new CheckBox(REGLAMENT_TIME);
        reglamentBox.setValue(model.isReglament());
        addComponent(Wrapper.wrap(WrapperSide.RIGHT_LEFT, reglamentBox));
    }

    private void initButtonSubmit() {
        submitButton = new Button(APPLY);
        submitButton.addStyleName("filter-submit");
        submitButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 3424690285727600955L;

            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                controller.change(getFilterRange(),
                        getDateTime(dateField),
                        getDateTime(fromField),
                        getDateTime(toField),
                        reglamentBox.getValue()
                );
            }
        });
        addComponent(submitButton);
    }

    private FilterRange getFilterRange() {
        return (FilterRange) optionGroup.getValue();
    }

    private LocalDate getDateTime(PopupDateField dateField) {
        if (dateField.getValue() != null) {
            return LocalDate.fromDateFields(dateField.getValue());
        }
        return new LocalDate(DateTimeZone.UTC);
    }

    @Override
    public void modified(FilterModelI model) {
        setFilterRange(model.getFilterRange());
        dateField.setValue(model.getDate().toDate());
        fromField.setValue(model.getDateFrom().toDate());
        toField.setValue(model.getDateTo().toDate());
        reglamentBox.setValue(model.isReglament());
    }
}