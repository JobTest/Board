package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.model.Complex;
import com.vaadin.ui.Component;

/**
 * Created by vlad
 * Date: 14.11.14_11:39
 */
public interface TabsModelI {

    public String getSessionId();

    public Complex getComplex();

    public Component getComponent(String caption);

}
