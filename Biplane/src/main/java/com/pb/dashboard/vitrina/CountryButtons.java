package com.pb.dashboard.vitrina;

import com.pb.dashboard.vitrina.core.config.Language;
import com.pb.dashboard.vitrina.core.country.CountrySelector;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;

public class CountryButtons extends HorizontalLayout {

    private Language lang = Language.UA;

    private NativeButton configureUA;
    private NativeButton configureRU;
    private NativeButton configureGEO;

    public CountryButtons(final CountrySelector view) {

        setSpacing(true);
        addStyleName("configure");
        setWidth("100px");

        configureUA = countryButtonCreator(view, Language.UA, "ua-active");
        configureRU = countryButtonCreator(view, Language.RU, "ru-inactive");
        configureGEO = countryButtonCreator(view, Language.GEO, "ge-inactive");

        addComponent(configureUA);
        addComponent(configureRU);
        addComponent(configureGEO);
    }

    private NativeButton countryButtonCreator(final CountrySelector view, final Language newLang, String active) {
        NativeButton configure = new NativeButton();
        configure.addStyleName("country");
        configure.addStyleName(active);
        configure.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (lang != newLang) {
                    lang = newLang;
                    view.updateCountry(lang);
                    view.updateData();
                    styleButtons();
                    Notification.show("Страна изменена на: " + lang.getName());
                }
            }
        });
        return configure;
    }

    private void styleButtons() {
        if (lang == Language.GEO) {
            configureGEO.removeStyleName("ge-inactive");
            configureGEO.addStyleName("ge-active");
            configureRU.removeStyleName("ru-active");
            configureRU.addStyleName("ru-inactive");
            configureUA.removeStyleName("ua-active");
            configureUA.addStyleName("ua-inactive");
        } else if (lang == Language.RU) {
            configureRU.removeStyleName("ru-inactive");
            configureRU.addStyleName("ru-active");
            configureUA.removeStyleName("ua-active");
            configureUA.addStyleName("ua-inactive");
            configureGEO.removeStyleName("ge-active");
            configureGEO.addStyleName("ge-inactive");
        } else {
            configureUA.removeStyleName("ua-inactive");
            configureUA.addStyleName("ua-active");
            configureRU.removeStyleName("ru-active");
            configureRU.addStyleName("ru-inactive");
            configureGEO.removeStyleName("ge-active");
            configureGEO.addStyleName("ge-inactive");
        }
    }
}
