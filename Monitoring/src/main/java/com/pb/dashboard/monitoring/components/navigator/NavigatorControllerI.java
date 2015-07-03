package com.pb.dashboard.monitoring.components.navigator;

import java.util.List;

/**
 * Created by vlad
 * Date: 21.11.14_10:34
 */
public interface NavigatorControllerI {

    public void setPointsNewLine(int... lineNew);

    public void setItem(int id, ContentItem item);

    public void setItems(int id, List<ContentItem> contents);

    public void setEnabled(int id, boolean enabled);

    public NavigatorView getView();

    NavigatorModelI getModel();
}
