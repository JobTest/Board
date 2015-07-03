package com.pb.dashboard.core.view;

import com.pb.dashboard.core.model.Language;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;

public class CountryButtons extends HorizontalLayout {

    private Language lang = Language.UA;

    private NativeButton buttonUA;
    private NativeButton buttonRU;
    private NativeButton buttonGE;

    public static final String UA = "ua";
    public static final String RU = "ru";
    public static final String GE = "ge";

    private final String styleActive = "-active";
    private final String styleInactive = "-inactive";

    public CountryButtons(final CountrySelector view) {

        setSpacing(true);
        addStyleName("configure");
        setWidth("100px");

        buttonUA = createCountryButton(view, Language.UA, UA + styleActive);
        buttonRU = createCountryButton(view, Language.RU, RU + styleInactive);
        buttonGE = createCountryButton(view, Language.GEO, GE + styleInactive);

        addComponent(buttonUA);
        addComponent(buttonRU);
        addComponent(buttonGE);
    }

    public void disableButton(String buttonId) {
        if (buttonId.equals(UA)) {
            removeComponent(buttonUA);
        } else if (buttonId.equals(RU)) {
            removeComponent(buttonRU);
        } else if (buttonId.equals(GE)) {
            removeComponent(buttonGE);
        } else {
            throw new IllegalArgumentException("Illegal buttonId for disableButton()");
        }
    }

    private NativeButton createCountryButton(final CountrySelector view, final Language newLang, String style) {
        NativeButton configure = new NativeButton();
        configure.addStyleName("country");
        configure.addStyleName(style);
        configure.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (lang != null && lang != newLang) {
                    lang = newLang;
                    view.countryUpdated(lang);
                    styleButtons();
                    Notification.show("Страна изменена на: " + lang.getName());
                }
            }
        });
        return configure;
    }

    private void styleButtons() {
        if (lang == Language.GEO) {
            buttonGE.removeStyleName(GE + styleInactive);
            buttonGE.addStyleName(GE + styleActive);
            buttonRU.removeStyleName(RU + styleActive);
            buttonRU.addStyleName(RU + styleInactive);
            buttonUA.removeStyleName(UA + styleActive);
            buttonUA.addStyleName(UA + styleInactive);
        } else if (lang == Language.RU) {
            buttonRU.removeStyleName(RU + styleInactive);
            buttonRU.addStyleName(RU + styleActive);
            buttonUA.removeStyleName(UA + styleActive);
            buttonUA.addStyleName(UA + styleInactive);
            buttonGE.removeStyleName(RU + styleActive);
            buttonGE.addStyleName(RU + styleInactive);
        } else {
            buttonUA.removeStyleName(UA + styleInactive);
            buttonUA.addStyleName(UA + styleActive);
            buttonRU.removeStyleName(RU + styleActive);
            buttonRU.addStyleName(RU + styleInactive);
            buttonGE.removeStyleName(GE + styleActive);
            buttonGE.addStyleName(GE + styleInactive);
        }
    }
}
