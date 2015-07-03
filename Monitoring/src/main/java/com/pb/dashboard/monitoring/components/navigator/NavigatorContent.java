package com.pb.dashboard.monitoring.components.navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 21.11.14_16:04
 */
public class NavigatorContent {

    private ContentItem item;
    private List<ContentItem> items;
    private boolean enabled = true;

    public NavigatorContent() {
    }

    public ContentItem getItem() {
        return item;
    }

    public void setItem(ContentItem item) {
        this.item = item;
    }

    public List<ContentItem> getItems() {
        return items == null ? new ArrayList<ContentItem>() : items;
    }

    public void setItems(List<ContentItem> items) {
        this.items = items;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
