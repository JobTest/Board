package com.pb.dashboard.monitoring.errors.all.table.view;

import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Reindeer;

import java.util.List;

public class ErrorTable extends Table {

    private static final String CODE = "Код";
    private static final String COUNT = "Количество";
    private static final String DECODING = "Расшифровка";

    private ClickListener listener;

    public ErrorTable() {
        init();
    }

    public ErrorTable(List<ErrorModel> errors) {
        init();
        setErrors(errors);
    }

    private void init() {
        addStyleName("monitoring");
        setWidth("100%");
        setPageLength(0);
        setSortEnabled(false);
        setFooterVisible(true);

        addContainerProperty(CODE, Button.class, null);
        addContainerProperty(COUNT, Integer.class, null);
        addContainerProperty(DECODING, String.class, null);

        setColumnAlignments(
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.LEFT
        );

        setColumnExpandRatio(CODE, 1.5f);
        setColumnExpandRatio(COUNT, 1.0f);
        setColumnExpandRatio(DECODING, 10.0f);
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setErrors(List<ErrorModel> errors) {
        removeAllItems();
        for (ErrorModel error : errors) {
            Button errorCodeButton = creationErrorCodeButton(error.getCode());
            addItem(new Object[]{
                    errorCodeButton,
                    error.getCount(),
                    error.getDecoding()
            }, error.getCode());
        }
        setPageLength(errors.size());
    }

    private Button creationErrorCodeButton(final String text) {
        final Button button = new Button(text);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (listener != null) {
                    listener.clickCode(text);
                }
            }
        });
        return button;
    }
}
