package com.pb.dashboard.core.component;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DClickLabel extends VerticalLayout {
    private Label label;

    public DClickLabel(String value) {
        label = new Label(value, ContentMode.HTML);
        addComponent(label);
        label.setSizeFull();
    }

    @Override
    public void addStyleName(String style) {
        label.addStyleName(style);
    }

    public void setLabel(String name) {
        label.setValue(name);
    }

    public void setStyleName(String styleName) {
        label.setStyleName(styleName);
    }
}
