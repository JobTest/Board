package com.pb.dashboard.external.charts.components;

import com.pb.dashboard.external.charts.controller.ChartsFilter;
import com.vaadin.ui.CheckBox;

public class DailyCheckBox extends CheckBox {
    private String CAPTION = "день";

    public DailyCheckBox(ChartsFilter filter) {
        setValue(filter.isDaily());
        setImmediate(true);
        setCaption(CAPTION);
        
    }
}
