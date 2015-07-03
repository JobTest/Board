package com.pb.dashboard.monitoring.errors.all.search;

import com.pb.dashboard.core.util.StringUtil;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class SearchController implements SearchControllerI {

    private SearchModelI model;
    private SearchView view;

    private SearchListener listener;

    public SearchController(SearchModelI model) {
        this.model = model;
        view = new SearchView(this, model);
    }

    @Override
    public void setSearchText(String text) {
        if (!StringUtil.isEmptyOrNull(text)) {
            text = text.trim();
            ((SearchModel) model).setSearchText(text);
            if (listener != null) {
                listener.clickSearch(text);
            }
        }
    }

    @Override
    public void setClickListener(SearchListener listener) {
        this.listener = listener;
    }

    @Override
    public SearchModelI getModel() {
        return model;
    }

    @Override
    public SearchView getView() {
        return view;
    }
}