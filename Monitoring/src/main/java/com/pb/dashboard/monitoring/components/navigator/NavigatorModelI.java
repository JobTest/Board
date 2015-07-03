package com.pb.dashboard.monitoring.components.navigator;

import java.util.List;
import java.util.Map;

/**
 * Created by vlad
 * Date: 21.11.14_10:16
 */
public interface NavigatorModelI {

    public int getCount();

    public void setPointsNewLine(int... lineNew);

    public NavigatorContent getContent(int id);

    public ContentItem getItem(int id);

    public Map<Integer, NavigatorContent> getContentMap();

    public boolean isEnabled(int id);

    void setContents(int id, List<ContentItem> items);

    void setContent(int id, ContentItem content);

    void setEnabled(int id, boolean enabled);

    void addObserver(NavigatorObserver observer);

    void removeObserver(NavigatorObserver observer);

    int[] getPointNewLine();

    void setListener(NavigatorListener listener);

    public void notifyAllObservers();
}