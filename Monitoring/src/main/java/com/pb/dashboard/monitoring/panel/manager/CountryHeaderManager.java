package com.pb.dashboard.monitoring.panel.manager;

import com.pb.dashboard.core.model.Country;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

public class CountryHeaderManager {

    public Component getCountryHeaders() {
        HorizontalLayout headers = new HorizontalLayout();
        headers.setSpacing(true);
        headers.addComponents(getUkrHeader(), getRusHeader(), getGeoHeader());
        return headers;
    }

    private Component getUkrHeader() {
        Component ukr = buildCountryTitle(Country.UKRAINE, new Image(null, new ThemeResource("img/timings/ukr-flag.png")));
        ukr.addStyleName("ukr");
        return ukr;
    }

    private Component getRusHeader() {
        Component rus = buildCountryTitle(Country.RUSSIA, new Image(null, new ThemeResource("img/timings/rus-flag.png")));
        rus.addStyleName("rus");
        return rus;
    }

    private Component getGeoHeader() {
        Component geo = buildCountryTitle(Country.GEORGIA, new Image(null, new ThemeResource("img/timings/geo-flag.png")));
        geo.addStyleName("geo");
        geo.addStyleName("header");
        return geo;
    }

    private Component buildCountryTitle(Country country, Image flag) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("country-header");
        layout.setSpacing(true);
        Label titleLabel = new Label(country.getName());
        titleLabel.addStyleName("country-title");
        flag.addStyleName("flag");
        layout.addComponents(flag, titleLabel);
        return layout;
    }
}