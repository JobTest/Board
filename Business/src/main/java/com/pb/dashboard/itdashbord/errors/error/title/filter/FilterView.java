package com.pb.dashboard.itdashbord.errors.error.title.filter;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.pb.dashboard.itdashbord.errors.error.title.filter.Components.ComboBoxComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

public class FilterView extends HorizontalLayout {

    private static final String BRANCH = "Филиал";
    private static final String GROUP = "Группа вида";
    private static final String TYPE_DEBT = "Вид задолженности";

    private ComboBox branchButton;
    private ComboBox groupButton;
    private ComboBox typeDebtButton;

    public FilterView(ErrorsFiltersListener filter) {
        setWidth("500px");

        branchButton = creationComboBox(BRANCH, filter);
        addComponent(branchButton);

        groupButton = creationComboBox(GROUP, filter);
        addComponent(groupButton);

        typeDebtButton = creationComboBox(TYPE_DEBT, filter);
        addComponent(typeDebtButton);
    }

    private ComboBox creationComboBox(String caption, ErrorsFiltersListener filter) {
        ComboBox box = new ComboBoxComponent(caption, filter);
        box.setWidth("150px");

        return box;
    }

    public ComboBox getBranchButton() {
        return branchButton;
    }

    public ComboBox getGroupButton() {
        return groupButton;
    }

    public ComboBox getTypeDebtButton() {
        return typeDebtButton;
    }
}
