package com.pb.dashboard.external.debtload.view;

import com.pb.dashboard.core.model.Language;
import com.pb.dashboard.core.view.CountryButtons;
import com.pb.dashboard.core.view.CountrySelector;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

public abstract class AbstractView extends VerticalLayout implements View, CountrySelector {

    protected Language lang = Language.UA;
    protected VerticalLayout content;
    protected String caption;

    public AbstractView(String caption) {
        this.caption = caption;
        setSizeFull();
        addStyleName("load-view");
        addComponent(createPanels());
    }

    private CssLayout createPanels() {
        CssLayout panel = new CssLayout();
        panel.setSizeFull();
        Component content = createContent();
        content.setCaption(buildCaption());
        panel.addComponent(content);

        return panel;
    }

    private Component createContent() {
        content = new VerticalLayout();
        content.addComponents(getHeader(), addContent());

        return content;
    }

    // Methods to be overridden in subclasses
    protected abstract Component addContent();
    protected abstract void update();

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setHeight("20px");
        CountryButtons countryButtons = new CountryButtons(this);
        countryButtons.disableButton(CountryButtons.GE);
        header.addComponent(countryButtons);
        header.setComponentAlignment(countryButtons, Alignment.MIDDLE_CENTER);

        return header;
    }

    private String buildCaption() {
        return lang.getName() + " : "  + caption;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void countryUpdated(Language lang) {
        this.lang = lang;
        content.setCaption(buildCaption());
        update();
    }

    public Language getLang() {
        return lang;
    }

}
