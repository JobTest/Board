package com.pb.dashboard.monitoring.sessions.viewcomponents.charts;

import com.pb.dashboard.monitoring.sessions.SessionsModelI;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by petrik on 02.10.14.
 */
public class ChartsLayout extends HorizontalLayout {
    public ChartsLayout(SessionsModelI model) {
        ChartsController controller = new ChartsController(model);
        setWidth("100%");
        setHeight("25%  ");
        setSpacing(false);
        addComponent(controller.getCharts());
    }
}
