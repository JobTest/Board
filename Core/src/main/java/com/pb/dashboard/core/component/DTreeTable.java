package com.pb.dashboard.core.component;

import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;

import java.util.Collection;
import java.util.List;

/**
 * Created by vlad
 * Date: 12.09.14
 */
public class DTreeTable extends TreeTable {

    private static final String TITLE_ID = "titleId";

    protected String[] columns = new String[]{};
    protected float[] ratio = new float[]{};
    protected Align[] alignment = new Align[]{};

    protected DTreeTable() {
        setSizeFull();
        setStyleName("tree");
        setSizeFull();
        setImmediate(true);
        setFooterVisible(true);

        addContainerProperty("", Component.class, null);

        addCollapseListener(new Tree.CollapseListener() {
            @Override
            public void nodeCollapse(Tree.CollapseEvent event) {
                if (event.getItemId() != null) {
                    nodeClose(event.getItemId());
                }
            }
        });
    }

    private void nodeClose(Object itemId) {
        Collection<?> children = getChildren(itemId);
        if (children != null) {
            int size = getChildren(itemId).size();
            int pageLength = getPageLength();
            setPageLength(pageLength - size);
        }
    }

    protected void setItems(List<Object[]> list) {
        removeAllItems();
        initTitle();
        for (final Object[] item : list) {
            HorizontalLayout layout = creationLayout(item, false);
            addItem(new Object[]{layout}, item[0]);
            layout.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
                @Override
                public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                    if (areChildrenAllowed(item[0])) {
                        boolean collapsed = isCollapsed(item[0]);
                        setCollapsed(item[0], !collapsed);
                    }
                }
            });
        }
        setPageLength(list.size() + 1);
    }

    private void initTitle() {
        HorizontalLayout layout = creationLayout(columns, true);
        addItem(new Object[]{layout}, TITLE_ID);
        setChildrenAllowed(TITLE_ID, false);
        setPageLength(1);
    }

    private HorizontalLayout creationLayout(Object[] columns, boolean title) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        for (int i = 0; i < columns.length; i++) {
            addLabel(layout, columns[i], title, i);
        }
        return layout;
    }

    private void addLabel(HorizontalLayout layout, Object value, boolean title, int number) {
        String text = value != null ? String.valueOf(value) : "";
        DClickLabel label = new DClickLabel(text);
        label.setSizeFull();
        layout.addComponent(label);
        if (ratio.length > number) {
            layout.setExpandRatio(label, ratio[number]);
        }
        if (title) {
            label.setStyleName("h4");
            label.addStyleName("center");
        } else {
            if (alignment.length > number) {
                if (alignment[number] == Align.CENTER) {
                    label.addStyleName("center");
                }
                if (alignment[number] == Align.RIGHT) {
                    label.addStyleName("right");
                }
            }
        }
    }
}
