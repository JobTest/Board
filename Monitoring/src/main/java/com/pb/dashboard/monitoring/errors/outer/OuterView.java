package com.pb.dashboard.monitoring.errors.outer;

import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionTable;
import com.pb.dashboard.monitoring.errors.outer.table.listener.ClickSessionListener;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by evasive on 03.11.14
 */
public class OuterView extends VerticalLayout {

    private static final Logger LOG = Logger.getLogger(OuterView.class);
    private static final String OUTER_SESSION_ID = "Поиск по outer_session_id";
    private static final String APPLY_BUTTON_CAPTION = "Применить";

    private TextField outerSessionField;
    private PopupDateField popupDateField;
    private Button apllyButton;
    private OuterSessionTable table;

    public OuterView() {
        init();
    }

    private void init() {
        initSearchPanel();
        initTable();
    }

    private void initSearchPanel() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("750px");
        layout.setHeight("30px");
        initTextField();
        initPopupDate();
        initButton();
        layout.addComponents(popupDateField, outerSessionField, apllyButton);
        layout.setExpandRatio(popupDateField, 1.3f);
        layout.setExpandRatio(outerSessionField, 3f);
        layout.setExpandRatio(apllyButton, 1.1f);
        addComponent(layout);
        setComponentAlignment(layout, Alignment.TOP_CENTER);
    }

    private void initTextField() {
        outerSessionField = new TextField();
        outerSessionField.setInputPrompt(OUTER_SESSION_ID);
        outerSessionField.setSizeFull();
    }

    private void initPopupDate() {
        Calendar now = Calendar.getInstance();
        popupDateField = createPopUpDateField(now);
        popupDateField.setSizeFull();
    }

    private void initButton() {
        apllyButton = new Button(APPLY_BUTTON_CAPTION);
        apllyButton.setSizeFull();
    }

    private void initTable() {
        table = new OuterSessionTable();
        table.setSizeFull();
        table.setVisible(false);
        addComponent(table);
    }

    private PopupDateField createPopUpDateField(Calendar now) {
        PopupDateField field = new PopupDateField();
        field.setWidth("110px");
        field.setDateFormat("yyyy-MM-dd");
        field.setValue(now.getTime());
        field.setImmediate(true);
        field.setTextFieldEnabled(false);
        return field;
    }

    public void setClickListener(Button.ClickListener listener) {
        apllyButton.addClickListener(listener);
    }

    public String getSearchValue() {
        return outerSessionField.getValue();
    }

    public Date getDateValue() {
        return popupDateField.getValue();
    }

    public void setSearchValue(String value) {
        this.outerSessionField.setValue(value);
    }

    public void setSessions(List<OuterSessionData> sessions) {
        table.setOuterSession(sessions);
        table.setVisible(true);
    }

    public void setClickSessionListener(ClickSessionListener listener) {
        table.setListener(listener);
    }

    public void setTableVisible(boolean visible) {
        table.setVisible(visible);
    }
}
