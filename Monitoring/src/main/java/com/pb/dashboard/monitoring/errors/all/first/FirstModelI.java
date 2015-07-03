package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.search.SearchModelI;

import java.util.List;

/**
 * Created by vlad
 * Date: 13.01.15_13:26
 */
public interface FirstModelI {

    public PromptType getPrompt();

    public void setPrompt(PromptType prompt);

    public SearchModelI getSearchModel();

    public List getList();

    public void setList(List list);

    public String getCode();

    public void setCode(String code);

    public void addObserver(Observer<FirstModelI> observer);

    public void removeObserver(Observer<FirstModelI> observer);
}
