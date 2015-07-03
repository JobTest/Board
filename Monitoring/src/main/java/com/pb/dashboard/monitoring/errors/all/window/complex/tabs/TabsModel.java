package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.model.Complex;
import com.vaadin.ui.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vlad
 * Date: 30.09.14
 */
public class TabsModel implements TabsModelI {

    private Complex complex;
    private String sessionId;
    private Map<String, Map<String, Component>> components;

    public TabsModel() {
        components = new LinkedHashMap<>();
    }

    @Override
    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Component getComponent(String caption) {
        if (!components.containsKey(sessionId)) {
            return null;
        }
        Map<String, Component> session = components.get(sessionId);
        return session.get(caption);
    }

    public void setComponent(TabType tab, Component component) {
        if (!components.containsKey(sessionId)) {
            components.put(sessionId, new LinkedHashMap<String, Component>());
        }
        Map<String, Component> session = components.get(sessionId);
        session.put(tab.getName(), component);
    }
}