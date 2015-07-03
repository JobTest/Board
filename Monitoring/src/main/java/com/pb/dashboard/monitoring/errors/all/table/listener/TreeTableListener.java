package com.pb.dashboard.monitoring.errors.all.table.listener;

import com.pb.dashboard.monitoring.errors.all.strategy.PageType;

import java.util.List;

/**
 * Created by vlad
 * Date: 16.09.14
 */
public interface TreeTableListener {

    public List<String> clickItem(String itemId, PageType type);

    public void clickSession(String session);
}
