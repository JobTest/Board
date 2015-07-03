package com.pb.dashboard.monitoring.errors.panel;

import com.vaadin.ui.Component;

import java.util.Map;

/**
 * Created by vlad
 * Date: 19.12.14_12:39
 */
public class ErrorsModel {

    private Map<String, String> params;
    private boolean outer;
    private Component component;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean isOuter() {
        return outer;
    }

    public void setOuter(boolean outer) {
        this.outer = outer;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
