package com.pb.dashboard.monitoring.errors.all.table.view;

import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Reindeer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vlad
 * Date: 11.09.14
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class RecipientTable extends Table {

    private static final String COMPANY_ID = "ID Company";
    private static final String RECIPIENT = "Получатель";
    private static final String FILIAL = "Филиал";
    private static final String COUNT = "Кол-во";

    private ClickListener listener;

    public RecipientTable() {
        init();
    }

    public RecipientTable(List<RecipientModel> list) {
        init();
        setRecipients(list);
    }

    private void init() {
        addStyleName("monitoring");
        setWidth("100%");
        setPageLength(0);
        setSortEnabled(false);
        setFooterVisible(true);

        addContainerProperty(COMPANY_ID, Button.class, null);
        addContainerProperty(RECIPIENT, String.class, null);
        addContainerProperty(FILIAL, String.class, null);
        addContainerProperty(COUNT, Integer.class, null);

        setColumnAlignments(
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER,
                Table.Align.CENTER
        );
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setRecipients(List<RecipientModel> recipients) {
        removeAllItems();
        setPageLength(recipients.size());
        for (RecipientModel recipient : recipients) {
            Button buttonId = creationIdButton(recipient.getId());
            addItem(new Object[]{
                    buttonId,
                    recipient.getName(),
                    recipient.getFilial(),
                    recipient.getCount()
            }, recipient.getId());
        }
    }

    private Button creationIdButton(final String id) {
        final Button button = new Button(id);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (listener != null) {
                    listener.clickCode(id);
                }
            }
        });
        return button;
    }
}
