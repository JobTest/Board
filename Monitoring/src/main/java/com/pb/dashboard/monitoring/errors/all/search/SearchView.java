package com.pb.dashboard.monitoring.errors.all.search;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class SearchView extends HorizontalLayout implements Observer<SearchModelI> {

    public static final String SEARCH_BY_ID_SESSION = "Поиск по %s...";
    private static final String FIND = "Найти";

    private SearchControllerI controller;
    private SearchModelI model;

    private TextField field;
    private Button find;

    public SearchView(SearchControllerI controller, SearchModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        setSizeFull();
        initTextField();
        initButton();
        modified(model);
    }

    private void initTextField() {
        field = new TextField();
        field.setInputPrompt(SEARCH_BY_ID_SESSION);
        field.setWidth("300px");
        addComponent(field);
        setComponentAlignment(field, Alignment.TOP_LEFT);
    }

    private void initButton() {
        find = new Button(FIND);
        find.setId("search_button");
        find.setHeight("90%");
        find.setWidth("90px");
        find.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                controller.setSearchText(field.getValue());
            }
        });
        addComponent(find);
        setComponentAlignment(find, Alignment.TOP_RIGHT);
    }

    @Override
    public void modified(SearchModelI obj) {
        setInputPrompt(obj.getPrompt());
        field.setValue(obj.getText());
    }

    private void setInputPrompt(String prompt) {
        field.setInputPrompt(String.format(SEARCH_BY_ID_SESSION, prompt));
    }

    public TextField getField() {
        return field;
    }

    public Button getFind() {
        return find;
    }
}