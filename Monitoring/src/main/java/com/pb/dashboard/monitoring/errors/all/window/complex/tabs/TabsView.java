package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by vlad
 * Date: 30.09.14
 */
public class TabsView extends TabSheet {

    public TabsView() {
        init();
    }

    private void init() {
        setSizeFull();
        setEnabled(false);
    }

    public void addTab(String caption) {
        addTab(createComponent(), caption);
    }

    private Component createComponent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(new MarginInfo(true));
        layout.setSizeFull();
        return layout;
    }

    public void setTabComponent(Component tab, Component component) {
        VerticalLayout layout = (VerticalLayout) tab;
        layout.removeAllComponents();
        layout.addComponent(component);
    }

    public String getSelectedCaption() {
        return getTab(getSelectedTab()).getCaption();
    }
}
