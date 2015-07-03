package com.pb.dashboard.monitoring.errors.all.search;

import com.pb.dashboard.monitoring.components.observers.Observer;

/**
 * Created by vlad
 * Date: 13.01.15_15:33
 */
public interface SearchModelI {

    public String getPrompt();

    public String getText();

    public void addObserver(Observer<SearchModelI> observer);

    public void removeObserver(Observer<SearchModelI> observer);

}
