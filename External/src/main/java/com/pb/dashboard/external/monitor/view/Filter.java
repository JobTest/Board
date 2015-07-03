package com.pb.dashboard.external.monitor.view;

import com.pb.dashboard.external.monitor.entype.FilterRange;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Filter extends HorizontalLayout {

    private PopupDateField from;
    private PopupDateField to;
    private OptionGroup radioButtons;
    private HorizontalLayout datePickers;
    private Filterable filterable;
    private FilterRange filterRange = FilterRange.R10MIN;
    private Date[] dates;

    public Filter(Filterable filterable) {
        this.filterable = filterable;
        addStyleName("horizontal-filter");
        setSpacing(true);
        addComponents(getRangeButtons(), getDatePickers(), getSubmitButton());
    }

    private Button getSubmitButton() {
        Button submit = new Button("Применить");
        submit.addStyleName("filter-submit");
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                updateData();
            }
        });

        return submit;
    }

    private Component getRangeButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addStyleName("range-buttons");
        buttons.setSpacing(true);
        radioButtons = new OptionGroup();
        radioButtons.addStyleName("horizontal");
        radioButtons.setMultiSelect(false);
        radioButtons.setImmediate(true);
        for (FilterRange range : FilterRange.values()) {
            radioButtons.addItem(range);
            radioButtons.setItemCaption(range, range.getName());
        }
        radioButtons.setValue(FilterRange.R10MIN);
        radioButtons.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                updateFilterElements((FilterRange) radioButtons.getValue());
            }
        });
        buttons.addComponent(radioButtons);

        return buttons;
    }

    private Component getDatePickers() {
        datePickers = new HorizontalLayout();
        datePickers.addStyleName("date-pickers");
        datePickers.setSpacing(true);
        Date now = new Date();
        Date firstDayOfMonth = getFirstDayOfMonth();
        from = createPopUpDateField(firstDayOfMonth, now);
        from.setRangeStart(firstDayOfMonth);
        to = createPopUpDateField(now, now);
        datePickers.addComponent(to);
        to.setEnabled(false);

        return datePickers;
    }

    private void updateFilterElements(FilterRange range) {
        if (range == FilterRange.R10MIN || range == FilterRange.MONTH) {
            datePickers.removeComponent(from);
            to.setEnabled(false);
        } else if (range == FilterRange.HOUR) {
            datePickers.removeComponent(from);
            to.setEnabled(true);
            to.setTextFieldEnabled(false);
        } else if (range == FilterRange.DAY) {
            datePickers.removeAllComponents();
            datePickers.addComponents(from, to);
            to.setEnabled(true);
            to.setTextFieldEnabled(false);
            from.setTextFieldEnabled(false);
        }
    }

    public void updateData() {
        filterRange = (FilterRange) radioButtons.getValue();
        dates = null;
        if (filterRange == FilterRange.R10MIN || filterRange == FilterRange.MONTH) {
            dates = new Date[] { new Date() };
        } else if (filterRange == FilterRange.HOUR) {
            dates = new Date[] { to.getValue() };
        } else if (filterRange == FilterRange.DAY) {
            dates = new Date[] { from.getValue(), to.getValue() };
            if (!isValidPeriod(dates)) return;
        }
        filterable.filterUpdated(filterRange, dates);
    }

    private boolean isValidPeriod(Date[] dates) {
        if (dates[0].after(dates[1])) {
            Notification.show("Неверно выбранный период", Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        return true;
    }

    private Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private PopupDateField createPopUpDateField(Date valueDate, Date rangeEnd) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setLocale(new Locale("RU"));
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(valueDate);
        field.setRangeEnd(rangeEnd);
        field.setImmediate(true);
        field.setInvalidAllowed(false);
        return field;
    }

    public FilterRange getFilterRange() {
        return filterRange;
    }

    public Date[] getDates() {
        return dates;
    }
}
