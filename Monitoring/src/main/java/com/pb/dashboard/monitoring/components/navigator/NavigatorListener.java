package com.pb.dashboard.monitoring.components.navigator;

import java.util.List;

/**
 * Created by vlad
 * Date: 06.01.15_14:13
 */
public interface NavigatorListener {

    void setItem(int navigatorId, ContentItem item);

    void setItems(int navigatorId, List<ContentItem> content);

    void setEnabled(int navigatorId, boolean enabled);

}
