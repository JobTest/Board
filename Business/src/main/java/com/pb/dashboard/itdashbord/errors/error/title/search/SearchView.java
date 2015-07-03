package com.pb.dashboard.itdashbord.errors.error.title.search;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class SearchView extends HorizontalLayout {

    private static final String SEARCH = "Поиск";
    private static final String SEARCH_BY_ID = "Поиск по id...";
    private TextField searchField;
    private Button searchApply;
    private ErrorsFiltersListener filter;

    public SearchView(ErrorsFiltersListener filter) {
        this.filter = filter;
        init();
    }

    private void init() {
        searchField = new TextField();
        searchField.setInputPrompt(SEARCH_BY_ID);
        searchField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setSearchString(searchField.getValue());
            }
        });

        addComponent(searchField);
        setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);

        searchApply = new Button(SEARCH);
        addComponent(searchApply);
        setComponentAlignment(searchApply, Alignment.MIDDLE_CENTER);
    }

    public Button getSearchApply() {
        return searchApply;
    }
}
