package com.pb.dashboard.monitoring.panel.manager;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by vlad
 * Date: 29.12.14_14:03
 */
public class DPanelManager {

    protected Component getPanel(Component... components) {
        VerticalLayout holder = new VerticalLayout();
        HorizontalLayout panel = new HorizontalLayout();
        panel.setSizeFull();
        panel.setSpacing(true);
        for (Component c : components) {
            panel.addComponent(c);
        }
        holder.addComponent(panel);

        return holder;
    }

    protected VerticalLayout getSubPanel(Component... components) {
        VerticalLayout subPanel = new VerticalLayout();
        subPanel.setSpacing(true);
        for (Component c : components) {
            subPanel.addComponent(c);
        }
        return subPanel;
    }
}
