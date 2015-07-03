package com.pb.dashboard.external.charts.components;

import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.vaadin.ui.CheckBox;

public class HourlyCheckBox extends CheckBox {
    String CAPTION = "час";

    public HourlyCheckBox(ChartsFilter filter) {
        setValue(filter.isHourly());
        setImmediate(true);
        setCaption(CAPTION);
    }
}
