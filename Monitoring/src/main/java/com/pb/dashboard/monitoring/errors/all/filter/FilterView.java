package com.pb.dashboard.monitoring.errors.all.filter;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

import java.util.Calendar;

public class FilterView extends HorizontalLayout implements Observer<FilterModelI> {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int SHOW_TIME_MSEC = 2000;

    private static final Logger log = Logger.getLogger(FilterView.class);
    private static final TopItem TOP_DEFAULT = TopItem.T10;
    private static final String TOP = "Топ";
    private static final String APPLY = "Применить";
    private static final String LOAD = "Загружено";

    private ComboBox topBox;
    private PopupDateField popupDataField;
    private Button applyButton;
    private Label loadLabel;

    private FilterControllerI controller;
    private FilterModelI model;

    public FilterView(FilterControllerI controller, FilterModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        addStyleName("horizontal-filter");
        setHeight("50px");
        setSpacing(true);

        initTopLabel();
        initComboBox();
        initDatePicker();
        initSubmitButton();
        initLoadLabel();
    }

    private void initTopLabel() {
        Label topLabel = new Label(TOP);
        addComponent(topLabel);
    }

    private void initComboBox() {
        topBox = new ComboBox();
        topBox.setWidth("50px");
        topBox.setTextInputAllowed(false);
        topBox.setNullSelectionAllowed(false);
        for (TopItem top : TopItem.values()) {
            topBox.addItem(top);
        }
        topBox.setValue(TOP_DEFAULT);
        addComponent(topBox);
    }

    private void initDatePicker() {
        popupDataField = createPopUpDateField();
        addComponent(popupDataField);
    }

    private PopupDateField createPopUpDateField() {
        Calendar now = Calendar.getInstance();
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setDateFormat(DATE_FORMAT);
        field.setValue(now.getTime());
        field.setRangeEnd(now.getTime());
        field.setImmediate(true);
        field.setTextFieldEnabled(false);
        return field;
    }

    private void initSubmitButton() {
        applyButton = new Button(APPLY);
        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.apply(getTopItem(), getDate());
                startLoadPresent();
            }
        });
        applyButton.addStyleName("filter-submit");
        addComponent(applyButton);
    }

    private void startLoadPresent() {
        loadLabel.setValue(LOAD);
        UI.getCurrent().setPollInterval(1000);
        new InitializerThread().start();
    }

    private class InitializerThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(SHOW_TIME_MSEC);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            UI.getCurrent().access(new Runnable() {
                @Override
                public void run() {
                    loadLabel.setValue("");
                }
            });
        }
    }

    private void initLoadLabel() {
        loadLabel = new Label();
        loadLabel.setWidth("50px");
        addComponent(loadLabel);
    }

    @Override
    public void modified(FilterModelI obj) {
        Calendar date = obj.getDate();
        popupDataField.setValue(date.getTime());
        topBox.setValue(obj.getTopItem());
    }

    @Override
    public void addComponent(Component c) {
        super.addComponent(c);
        setComponentAlignment(c, Alignment.MIDDLE_CENTER);
    }

    private Calendar getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(popupDataField.getValue());
        return calendar;
    }

    private TopItem getTopItem() {
        return (TopItem) topBox.getValue();
    }
}