package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.core.component.DTreeTable;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.Reindeer;

import java.util.ArrayList;
import java.util.List;

public class RecipientTreeTable extends DTreeTable {

    private static final String COMPANY_ID = "ID Company";
    private static final String COMPANY_NAME = "Получатель";
    private static final String FILIAL_NAME = "Филиал";
    private static final String COUNT = "Кол-во";

    private TreeTableListener listener;

    public RecipientTreeTable() {
        init();
        addExpandListener(new Tree.ExpandListener() {
            @Override
            public void nodeExpand(Tree.ExpandEvent event) {
                if (listener != null && event.getItemId() != null && !isCollapsed(event.getItemId())) {
                    if (!hasChildren(event.getItemId())) {
                        List<String> sessions = listener.clickItem(event.getItemId().toString(), PageType.RECIPIENT);
                        setSessions(event.getItemId(), sessions);
                    } else {
                        int size = getChildren(event.getItemId()).size();
                        int pageLength = getPageLength();
                        setPageLength(pageLength + size);
                    }
                }
            }
        });
    }

    private void init() {
        addStyleName("monitoring");

        columns = new String[]{
                COMPANY_ID,
                COMPANY_NAME,
                FILIAL_NAME,
                COUNT
        };

        ratio = new float[]{
                1.0f,
                4.0f,
                2.0f,
                1.0f
        };

        alignment = new Align[]{
                Align.CENTER,
                Align.CENTER,
                Align.CENTER,
                Align.CENTER
        };
    }

    public void setRecipients(List<RecipientModel> recipients) {
        List<Object[]> list = new ArrayList<Object[]>();
        for (RecipientModel recipient : recipients) {
            Object[] item = new Object[]{
                    recipient.getId(),
                    recipient.getName(),
                    recipient.getFilial(),
                    recipient.getCount()
            };
            list.add(item);
        }
        setItems(list);
    }

    public void setSessions(Object parent, List<String> sessions) {
        for (final String session : sessions) {
            Button button = createButton(session);
            Object id = addItem(new Object[]{button}, null);
            setChildrenAllowed(id, false);
            setParent(id, parent);
        }
        setPageLength(getPageLength() + sessions.size());
    }

    private Button createButton(final String sessionId) {
        Button button = new Button(sessionId);
        button.setStyleName(Reindeer.BUTTON_LINK);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (listener != null) {
                    listener.clickSession(sessionId);
                }
            }
        });
        return button;
    }

    public void setListener(TreeTableListener listener) {
        this.listener = listener;
    }
}