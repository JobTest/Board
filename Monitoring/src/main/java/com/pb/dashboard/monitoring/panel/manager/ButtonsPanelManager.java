package com.pb.dashboard.monitoring.panel.manager;

import com.pb.dashboard.core.config.ConfigManager;
import com.pb.dashboard.core.config.ConfigParams;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.monitoring.panel.MonitoringType;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

public class ButtonsPanelManager {

    private PopUpManager popUpManager = new PopUpManager();

    public Component getPanel(Country country, Complex complex) {
        VerticalLayout panel = new VerticalLayout();
        panel.setMargin(true);
        panel.setSpacing(true);
        panel.addStyleName("buttons-panel");
        Label titleLabel = new Label(complex.getName());
        titleLabel.addStyleName("buttons-title");
        panel.addComponent(titleLabel);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);

        Component timings = createPopupView(MonitoringType.TIMINGS, country, complex, "sl");
        Component crash = createPopupView(MonitoringType.STATISTIC, country, complex, "hw");
        Component errors = createPopupView(MonitoringType.ERRORS, country, complex, "st");

        buttons.addComponents(timings, crash, errors);
        panel.addComponent(buttons);

        return panel;
    }

    private Component createPopupView(MonitoringType type, Country country, Complex complex, String style) {
        AbstractComponent component;
        if (type == MonitoringType.STATISTIC) {

            final String urlClient = ConfigManager.getInstance().getParam(ConfigParams.WEB_CLIENT_URL);
            final String path = "monitoring/statistic";
            Button button = new Button();
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Page.getCurrent().setLocation(urlClient + path);
                }
            });
            component = button;

        } else {
            component = new PopupView(null, getContent(type, country, complex));

        }
        component.setEnabled(checkEnabledButton(type, country, complex));
        component.setDescription(type.getName());
        component.addStyleName(style);

        return component;
    }

    private Component getContent(MonitoringType type, Country country, Complex complex) {
        if (!checkEnabledButton(type, country, complex)) {
            return null;
        }
        if (type == MonitoringType.ERRORS) {
            return popUpManager.getMetricsErrors(country, complex);
        }
        if (type == MonitoringType.TIMINGS) {
            return popUpManager.getInterfacesPopUp(country, complex);
        }
        return null;
    }

    private boolean checkEnabledButton(MonitoringType type, Country country, Complex complex) {
        if ((type == MonitoringType.STATISTIC && complex != Complex.DEBT) || country != Country.UKRAINE) {
            return false;
        }
        if (type == MonitoringType.STATISTIC) {
            return true;
        }
        if (type == MonitoringType.ERRORS) {
            switch (complex) {
                /*settling*/
                case BIPLANE_API2X:
                case DEBT:
                case TEMPLATES:
                    return true;
            }
        }
        if (type == MonitoringType.TIMINGS && complex != Complex.WIDGETS) {
            return true;
        }
        return false;
    }
}