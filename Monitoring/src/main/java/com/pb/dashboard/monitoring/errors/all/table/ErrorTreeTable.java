package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.core.component.DTreeTable;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.Reindeer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 12.09.14
 */
public class ErrorTreeTable extends DTreeTable {

    private static final String CODE = "Код";
    private static final String COUNT = "Количество";
    private static final String DECODING = "Расшифровка";

    private TreeTableListener listener;

    public ErrorTreeTable() {
        init();
        addExpandListener(new Tree.ExpandListener() {
            @Override
            public void nodeExpand(Tree.ExpandEvent event) {
                expandErrorItem(event.getItemId());
            }
        });
    }

    private void expandErrorItem(Object itemId) {
        if (listener != null && itemId != null && !isCollapsed(itemId)) {
            if (!hasChildren(itemId)) {
                List<String> sessions = listener.clickItem((String) itemId, PageType.ERROR);
                setSessions(itemId, sessions);
            } else {
                int size = getChildren(itemId).size();
                int pageLength = getPageLength();
                setPageLength(pageLength + size);
            }
        }
    }

    private void init() {
        addStyleName("monitoring");

        columns = new String[]{
                CODE,
                COUNT,
                DECODING
        };

        ratio = new float[]{
                1.5f,
                1.2f,
                10.0f
        };

        alignment = new Align[]{
                Align.CENTER,
                Align.CENTER,
                Align.LEFT
        };
    }

    public void setErrors(List<ErrorModel> errors) {
        List<Object[]> list = new ArrayList<>();
        for (ErrorModel error : errors) {
            Object[] item = new Object[]{
                    error.getCode(),
                    error.getCount(),
                    error.getDecoding()
            };
            list.add(item);
        }
        setItems(list);
    }

    public void setSessions(Object parent, List<String> sessions) {
        for (final String sessionId : sessions) {
            Button button = createButton(sessionId);
            addItem(new Object[]{button}, sessionId);
            setChildrenAllowed(sessionId, false);
            setParent(sessionId, parent);
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