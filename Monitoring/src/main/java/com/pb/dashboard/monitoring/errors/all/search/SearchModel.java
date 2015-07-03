package com.pb.dashboard.monitoring.errors.all.search;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class SearchModel implements SearchModelI {

    private Observers<Observer, SearchModelI> observers = new Observers<Observer, SearchModelI>();
    private String prompt = "";
    private String searchText = "";

    public SearchModel() {
    }

    public SearchModel(String prompt, String searchText) {
        this.prompt = prompt;
        this.searchText = searchText;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
        observers.notifyModified(this);
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        observers.notifyModified(this);
    }

    @Override
    public void addObserver(Observer<SearchModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<SearchModelI> observer) {
        observers.remove(observer);
    }
}
