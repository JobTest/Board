package com.pb.dashboard.monitoring.errors.all.search;

/**
 * Created by vlad
 * Date: 17.09.14
 */
public interface SearchControllerI {

    public void setSearchText(String text);

    public void setClickListener(SearchListener listener);

    public SearchModelI getModel();

    public SearchView getView();
}