package com.pb.dashboard.itdashbord.errors.errorSession.filter;

import com.pb.dashboard.itdashbord.errors.errorSession.ErrorsSessionFiltersListener;
import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

public class FilterView extends ComboBox {
    private final ErrorsSessionFiltersListener filter;
    private String NAME = "тип ошибки";
    private String[] ITEMS ={"Ошибки пользовательского ввода", "Системная", "Бизнесовая", "Бизнесовая, валидационная","Бизнесовая, управляющая","Предупреждающая"};

    public FilterView(ErrorsSessionFiltersListener filter) {
        this.filter = filter;
        setCaption(NAME);
        setWidth("250px");
        setImmediate(true);
        for (String ITEM : ITEMS) {
            addItem(ITEM);
            addListenerToBox();
        }
    }

    private void addListenerToBox() {
        addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filter.setType((String)getValue());
            }
        });
    }
}
