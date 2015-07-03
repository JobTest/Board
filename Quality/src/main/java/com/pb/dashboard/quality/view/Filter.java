package com.pb.dashboard.quality.view;

import com.pb.dashboard.quality.types.Channel;
import com.pb.dashboard.quality.types.Range;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Filter extends HorizontalLayout {

    private static final long serialVersionUID = 4605615293147223888L;
    private Filterable filterable;
    private PopupDateField from;
    private PopupDateField to;
    private ComboBox channelSelect;
    private OptionGroup radioButtons;

    public Filter(Filterable filterable) {
        this.filterable = filterable;
        addStyleName("horizontal-filter");
        setSpacing(true);
        addComponents(getLeftBlock(), getCenterBlock(), getRightBlock());
    }

    private HorizontalLayout getLeftBlock() {
        HorizontalLayout leftBlock = new HorizontalLayout();
        leftBlock.setSpacing(true);
        leftBlock.addStyleName("left-block");
        Label label = new Label("Канал:");
        label.addStyleName("channel");
        channelSelect = new ComboBox();
        for (Channel channel : Channel.values()) {
            if (channel == Channel.UNDEF) continue;
            channelSelect.addItem(channel);
            channelSelect.setItemCaption(channel, channel.getName());
        }
        channelSelect.setNullSelectionAllowed(false);
        channelSelect.setValue(Channel.OFFICE);
        channelSelect.setImmediate(true);
        channelSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent valueChangeEvent) {
                update();
            }
        });
        addComponents(label, channelSelect);
        return leftBlock;
    }

    private HorizontalLayout getCenterBlock() {
        HorizontalLayout centerBlock = new HorizontalLayout();
        centerBlock.addStyleName("center-block");
        centerBlock.setSpacing(true);
        radioButtons = new OptionGroup();
        radioButtons.addStyleName("horizontal");
        radioButtons.setMultiSelect(false);
        radioButtons.setImmediate(true);
        for (Range range : Range.values()) {
            radioButtons.addItem(range);
            radioButtons.setItemCaption(range, range.getName());
        }
        radioButtons.setValue(Range.DAY);
        radioButtons.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent valueChangeEvent) {
                update();
            }
        });
        centerBlock.addComponent(radioButtons);
        return centerBlock;
    }

    private HorizontalLayout getRightBlock() {
        HorizontalLayout rightBlock = new HorizontalLayout();
        rightBlock.addStyleName("right-block");
        rightBlock.setSpacing(true);
        Label label = new Label("Период:");
        label.addStyleName("period");
        Date[] dates = initDates();
        from = createPopUpDateField(dates[0], dates[1]);
        to = createPopUpDateField(dates[1], dates[1]);
        rightBlock.addComponents(label, from, to);

        return rightBlock;
    }

    private PopupDateField createPopUpDateField(Date valueDate, Date rangeEnd) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setLocale(new Locale("RU"));
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(valueDate);
        field.setRangeEnd(rangeEnd);
        field.setImmediate(true);
        field.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent valueChangeEvent) {
                update();
            }
        });
        return field;
    }

    private void update() {
        if (verifyPeriod(from.getValue(), to.getValue())) {
            Range range = (Range) radioButtons.getValue();
            Date[] dates = {from.getValue(), to.getValue()};
            if (range == Range.WEEK) dates = adjustWeeklyDates(dates);
            if (range == Range.MONTH) dates = adjustMonthlyDates(dates);
            filterable.filterDataUpdated((Channel) channelSelect.getValue(), range, dates);
        }
    }

    private Date[] adjustWeeklyDates(Date[] dates) {
        Calendar to = Calendar.getInstance();
        to.setTime(dates[1]);
        to.add(Calendar.DAY_OF_WEEK, -6);
        if (dates[0].after(to.getTime()) || dates[0].equals(to.getTime())) {
            return new Date[]{to.getTime(), dates[1]};
        }
        to.setTime(dates[1]);
        to.add(Calendar.DAY_OF_WEEK, 1);
        while (to.getTime().after(dates[0])) {
            to.add(Calendar.DAY_OF_WEEK, -7);
        }
        //to.add(Calendar.DAY_OF_WEEK, 7);

        return new Date[]{to.getTime(), dates[1]};
    }

    private Date[] adjustMonthlyDates(Date[] dates) {
        Calendar from = Calendar.getInstance();
        from.setTime(dates[0]);
        from.set(Calendar.DAY_OF_MONTH, from.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar to = Calendar.getInstance();
        to.setTime(dates[1]);
        to.set(Calendar.DAY_OF_MONTH, to.getActualMaximum(Calendar.DAY_OF_MONTH));

        return new Date[]{from.getTime(), to.getTime()};
    }

    private boolean verifyPeriod(Date from, Date to) {
        if (from.after(to)) {
            Notification.show("Неверно выбранный период", Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        return true;
    }

    private Date[] initDates() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date dateTo = cal.getTime();
        cal.add(Calendar.MONTH, -1);
        Date dateFrom = cal.getTime();
        return new Date[]{dateFrom, dateTo};
    }

}
