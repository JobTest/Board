package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.search.SearchListener;
import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;

import java.util.List;

/**
 * Created by vlad
 * Date: 10.09.14
 */
public interface FirstControllerI {

    public void addObserver(Observer<FirstModelI> observer);

    public void setSearchListener(SearchListener listener);

    public void setList(List list);

    public void setListener(ClickListener listener);

    public FirstView getView();

    public FirstModelI getModel();

}