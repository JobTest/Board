package com.pb.dashboard.vitrina.selection;

import com.pb.dashboard.vitrina.core.types.BiplaneChannel;
import com.pb.dashboard.vitrina.payment.data.StatEnum;
import com.pb.dashboard.vitrina.view.SelectionView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SelectionViewFilter extends VerticalLayout {

    private SelectionView mainView;
    private InlineDateField dateSelect;
    private InlineDateField compareDate;
    private ComboBox channelSelect;
    private SelectionManager manager;

    private boolean comparingDates = false;
    private boolean compSwitch = true;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SelectionViewFilter(final SelectionView mainView, SelectionManager newManager) {

        this.mainView = mainView;
        this.manager = newManager;
        setSpacing(true);
        setWidth("230px");
        addStyleName("selection-filter");

        VerticalLayout channelBlock = new VerticalLayout();
        channelSelect = new ComboBox();
        for (BiplaneChannel type : BiplaneChannel.values()) {
            if (type == BiplaneChannel.UNDEF) continue;
            channelSelect.addItem(type);
            channelSelect.setItemCaption(type, type.getName());
        }
        channelSelect.setNullSelectionAllowed(false);
        channelSelect.setValue(BiplaneChannel.ALL);
        channelSelect.setImmediate(true);
        channelSelect.setWidth("230px");
        channelSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                updateByType();
            }
        });

        Label channelLabel = new Label("Канал");
        channelLabel.addStyleName("filter-label");
        channelBlock.addComponent(channelLabel);
        channelBlock.addComponent(channelSelect);

        Date today = new Date();

        VerticalLayout dateSelectBlock = new VerticalLayout();
        dateSelect = new InlineDateField();
        dateSelect.setStyleName("custom");
        dateSelect.addStyleName("date");
        dateSelect.setValue(today);
        dateSelect.setRangeEnd(today);
        dateSelect.setImmediate(true);
        dateSelect.setLocale(new Locale("RU"));
        dateSelect.setTimeZone(TimeZone.getTimeZone("Kiev/Ukraine"));
        dateSelect.setResolution(Resolution.DAY);
        dateSelect.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                updateByMainDate();
            }
        });

        Label dateSelectLabel = new Label("Основная дата");
        dateSelectLabel.addStyleName("filter-label");
        dateSelectBlock.addComponent(dateSelectLabel);
        dateSelectBlock.addComponent(dateSelect);

        VerticalLayout compareDateBlock = new VerticalLayout();
        compareDate = new InlineDateField();
        compareDate.addStyleName("custom");
        compareDate.addStyleName("compare-date");
        compareDate.setDescription("Дата для сравнения");
        compareDate.setValue(today);
        compareDate.setRangeEnd(today);
        compareDate.setImmediate(true);
        compareDate.setLocale(new Locale("RU"));
        compareDate.setTimeZone(TimeZone.getTimeZone("Kiev/Ukraine"));
        compareDate.setResolution(Resolution.DAY);
        compareDate.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                updateByCompareDate();
            }
        });

        Label compareDateLabel = new Label("Сравнить");
        compareDateLabel.addStyleName("filter-label");
        compareDateBlock.addComponent(compareDateLabel);
        compareDateBlock.addComponent(compareDate);

        addComponent(channelBlock);
        addComponent(dateSelectBlock);
        addComponent(compareDateBlock);
    }

    private void updateByType() {
        BiplaneChannel channel = (BiplaneChannel) channelSelect.getValue();
        StatEnum type = manager.convertEnum(channel);
        if (comparingDates) {
            manager.compareByType(type);
            mainView.setComparisonChartSeries(manager.getComparisonDates(), manager.getRegChartComparisonData(),
                    manager.getMainDateTotals(), manager.getCompDateTotals());
        } else {
            manager.updateByType(type);
            mainView.setRegularChartSeries(manager.getRegularChartData(), manager.getMainDateTotals(), manager.getDate());
        }
        mainView.fillTable(manager.getTotal(), manager.getPayments());
    }

    private void updateByCompareDate() {
        if (compSwitch) {
            Date date = adjustDate(compareDate.getValue());
            if (sdf.format(manager.getDate()).equals(sdf.format(date))) showMainDate();
            else {
                manager.collectComparisonData(date);
                mainView.fillTable(manager.getTotal(), manager.getPayments());
                mainView.setComparisonChartSeries(manager.getComparisonDates(), manager.getRegChartComparisonData(),
                        manager.getMainDateTotals(), manager.getCompDateTotals());
                comparingDates = true;
            }
        }
    }

    public void updateByMainDate() {
        BiplaneChannel channel = (BiplaneChannel) channelSelect.getValue();
        StatEnum type = manager.convertEnum(channel);
        Date date = dateSelect.getValue();
        manager.collectData(adjustDate(date));
        compSwitch = false;
        compareDate.setValue(date);
        compSwitch = true;
        manager.updateByType(type);
        mainView.fillTable(manager.getTotal(), manager.getPayments());
        mainView.setRegularChartSeries(manager.getRegularChartData(), manager.getMainDateTotals(), manager.getDate());
        comparingDates = false;
    }

    public void showMainDate() {
        mainView.fillTable(manager.getTotal(), manager.getPayments());
        mainView.setRegularChartSeries(manager.getRegularChartData(), manager.getMainDateTotals(), manager.getDate());
        comparingDates = false;
    }

    public Date adjustDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -3);
        if (sdf.format(cal.getTime()).equals(sdf.format(date))) return date;
        else return cal.getTime();
    }

}
