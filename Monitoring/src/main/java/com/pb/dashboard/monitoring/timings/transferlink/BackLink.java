package com.pb.dashboard.monitoring.timings.transferlink;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.Reindeer;

public class BackLink extends Button {
    public BackLink() {
        setStyleName(Reindeer.BUTTON_LINK);
        addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(Dashboard.Biplane.Monitoring.PATH);
            }
        });
        setCaption("Главная \"Тайминги\"");
    }
}