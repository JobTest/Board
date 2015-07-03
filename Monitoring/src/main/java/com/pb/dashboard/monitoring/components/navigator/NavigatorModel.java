package com.pb.dashboard.monitoring.components.navigator;

import java.util.*;

/**
 * Created by vlad
 * Date: 21.11.14_14:47
 */
public class NavigatorModel implements NavigatorModelI {

    private Set<NavigatorObserver> observers;
    private NavigatorListener listener;

    private Map<Integer, NavigatorContent> contentMap;
    private int[] pointNewLine;

    public NavigatorModel() {
        pointNewLine = new int[]{};
        contentMap = new HashMap<Integer, NavigatorContent>();
        observers = new LinkedHashSet<NavigatorObserver>();
    }

    @Override
    public int getCount() {
        return contentMap.size();
    }

    @Override
    public void setPointsNewLine(int... points) {
        this.pointNewLine = points;
    }

    @Override
    public void setContents(int id, List<ContentItem> items) {
        NavigatorContent content = contentMap.get(id);
        if (content == null) {
            content = new NavigatorContent();
            contentMap.put(id, content);
        }
        content.setItems(items);
        if (!items.isEmpty()) {
            setContent(id, items.get(0));
        }
        if (listener != null) {
            listener.setItems(id, items);
        }
    }

    @Override
    public void setContent(int id, ContentItem item) {
        NavigatorContent content = contentMap.get(id);
        if (content == null) {
            content = new NavigatorContent();
            contentMap.put(id, content);
        }
        content.setItem(item);
        for (NavigatorObserver observer : observers) {
            observer.setItem(id, item);
        }
        if (listener != null) {
            listener.setItem(id, item);
        }
    }

    public void notifyAllObservers() {
        for (Map.Entry<Integer, NavigatorContent> entry : contentMap.entrySet()) {
            Integer key = entry.getKey();
            ContentItem item = entry.getValue().getItem();
            for (NavigatorObserver observer : observers) {
                observer.setItem(key, item);
            }
        }
    }

    @Override
    public void setEnabled(int id, boolean enabled) {
        NavigatorContent content = contentMap.get(id);
        if (content == null) {
            content = new NavigatorContent();
            contentMap.put(id, content);
        }
        content.setEnabled(enabled);
        if (listener != null) {
            listener.setEnabled(id, enabled);
        }
    }

    public Map<Integer, NavigatorContent> getContentMap() {
        return contentMap;
    }

    @Override
    public boolean isEnabled(int id) {
        return contentMap.get(id).isEnabled();
    }

    @Override
    public NavigatorContent getContent(int id) {
        return contentMap.get(id);
    }

    @Override
    public ContentItem getItem(int id) {
        NavigatorContent content = contentMap.get(id);
        return content.getItem();
    }

    @Override
    public void addObserver(NavigatorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(NavigatorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public int[] getPointNewLine() {
        return pointNewLine;
    }

    @Override
    public void setListener(NavigatorListener listener) {
        this.listener = listener;
    }
}