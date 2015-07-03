package com.pb.dashboard.itdashbord.errors.errorSession.searchError;

import com.pb.dashboard.itdashbord.errors.errorSession.ErrorsSessionFiltersListener;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class ErrorSearchView extends HorizontalLayout {

    private static final String SEARCH = "Поиск";
    private static final String SEARCH_BY_CODE = "Поиск по коду...";
    private TextField searchField;
    private Button searchApply;
    private ErrorsSessionFiltersListener filter;

    public ErrorSearchView(ErrorsSessionFiltersListener filter) {
        this.filter = filter;
        init();
    }


    private void init() {


        searchField = new TextField();
        searchField.setInputPrompt(SEARCH_BY_CODE);

        searchField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setSerchString(searchField.getValue());
            }
        });
        searchField.setImmediate(true);

        addComponent(searchField);
        setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);

        searchApply = new Button(SEARCH);
        addComponent(searchApply);
        setComponentAlignment(searchApply, Alignment.MIDDLE_CENTER);


    }

    public Button getSearchButton() {
        return searchApply;
    }
}
