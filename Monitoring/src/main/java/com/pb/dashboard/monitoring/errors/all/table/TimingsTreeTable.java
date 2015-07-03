package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.core.component.DClickLabel;
import com.pb.dashboard.core.util.StringUtil;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

import java.util.Collection;

public class TimingsTreeTable extends TreeTable {

    public static final String T0 = "T0";
    public static final String T2 = "T2";
    private static final String PREF_COMPONENT = "component_";
    private static final long serialVersionUID = -7901261343778369144L;

    public TimingsTreeTable() {
        init();
        initItems();
        initExpandListener();
    }

    private void init() {
        setStyleName("monitoring");
        addStyleName("tree");
        setWidth("100%");
        refreshRowCache();
        refreshRenderedCells();
        setFooterVisible(true);
        addContainerProperty(StringUtil.EMPTY, Component.class, "");
    }

    private void initItems() {
        DClickLabel labelT0 = new DClickLabel(T0);
        addItem(new Object[]{labelT0}, T0);
        labelT0.addLayoutClickListener(createListener(T0));

        DClickLabel labelT2 = new DClickLabel(T2);
        addItem(new Object[]{labelT2}, T2);
        labelT2.addLayoutClickListener(createListener(T2));

        setPageLength(2);
    }

    private LayoutEvents.LayoutClickListener createListener(final String itemId) {
        return new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                if (itemId != null && areChildrenAllowed(itemId)) {
                    setCollapsed(itemId, !isCollapsed(itemId));
                }
            }
        };
    }

    private void initExpandListener() {
        addExpandListener(new Tree.ExpandListener() {
            @Override
            public void nodeExpand(Tree.ExpandEvent event) {
                setPageLength(getPageLength() + 1);
                nodeTimingLoad(event.getItemId());
            }
        });

        addCollapseListener(new Tree.CollapseListener() {
            @Override
            public void nodeCollapse(Tree.CollapseEvent event) {
                setPageLength(getPageLength() - 1);
            }
        });
    }

    private void nodeTimingLoad(Object itemIdParent) {
        if (StringUtil.isEmptyOrNull(itemIdParent)) {
            return;
        }
        Collection<?> children = getChildren(itemIdParent);
        if (children == null || children.isEmpty()) {
            Component component = itemIdParent.equals(TimingsTreeTable.T0) ? loadComponentT0() : loadComponentT2();
            String itemId = PREF_COMPONENT + itemIdParent;
            addItem(new Object[]{component}, itemId);
            setChildrenAllowed(itemId, false);
            setParent(itemId, itemIdParent);
        }
    }

    public Component loadComponentT0() {
        return new VerticalLayout();
    }

    public Component loadComponentT2() {
        return new VerticalLayout();
    }
}