package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.search.SearchController;
import com.pb.dashboard.monitoring.errors.all.search.SearchControllerI;
import com.pb.dashboard.monitoring.errors.all.search.SearchListener;
import com.pb.dashboard.monitoring.errors.all.search.SearchModel;
import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vlad
 * Date: 10.09.14
 */
public class FirstController implements FirstControllerI, Serializable {

    private FirstView view;
    private FirstModel model;

    private SearchControllerI searchController;

    public FirstController(FirstModel model) {
        initSearch();
        this.model = model;
        initView();
    }

    public FirstController() {
        initSearch();
        initModel();
        initView();
    }

    private void initSearch() {
        searchController = new SearchController(new SearchModel());
    }

    private void initModel() {
        model = new FirstModel();
        model.setSearchModel((SearchModel) searchController.getModel());
    }

    private void initView() {
        view = new FirstView(this, model);
        view.setSearch(searchController.getView());
    }

    public void addObserver(Observer<FirstModelI> observer) {
        model.addObserver(observer);
    }

    @Override
    public void setSearchListener(SearchListener listener) {
        searchController.setClickListener(listener);
    }

    @Override
    public void setList(List list) {
        if (!list.isEmpty()) {
            boolean errorModel = list.get(0) instanceof ErrorModel;
            PromptType type = errorModel ? PromptType.SESSION : PromptType.COMPANY;
            model.setPrompt(type);
        }
        model.setList(list);
        model.notifyModifiedAll();
    }

    @Override
    public void setListener(ClickListener listener) {
        view.setListener(listener);
    }

    @Override
    public FirstView getView() {
        return view;
    }

    @Override
    public FirstModelI getModel() {
        return model;
    }
}