package com.pb.dashboard.vitrina.redmine;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ClickLabel extends VerticalLayout {
    private Label label;

    public ClickLabel(String value) {
        label = new Label(value, ContentMode.HTML);
        addComponent(label);
        label.setSizeFull();
    }

    public void setLabel(String name) {
        label.setValue(name);
    }

    public void setStyleName(String styleName) {
        label.setStyleName(styleName);
    }
}