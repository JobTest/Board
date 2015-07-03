package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import com.pb.dashboard.monitoring.errors.all.search.SearchModel;
import com.pb.dashboard.monitoring.errors.all.search.SearchModelI;

import java.util.List;

/**
 * Created by vlad
 * Date: 10.09.14
 */
public class FirstModel implements FirstModelI {

    private Observers<Observer, FirstModelI> observers = new Observers<Observer, FirstModelI>();
    private PromptType prompt = PromptType.SESSION;
    private List list;
    private String code;

    private SearchModel searchModel = new SearchModel();

    @Override
    public SearchModelI getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }

    @Override
    public PromptType getPrompt() {
        return prompt;
    }

    @Override
    public void setPrompt(PromptType prompt) {
        this.prompt = prompt;
        searchModel.setPrompt(prompt.getName());
    }

    @Override
    public List getList() {
        return list;
    }

    @Override
    public void setList(List list) {
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void addObserver(Observer<FirstModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<FirstModelI> observer) {
        observers.remove(observer);
    }

    public void notifyModifiedAll() {
        observers.notifyModified(this);
    }
}
