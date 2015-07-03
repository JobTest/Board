package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.search.SearchView;
import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.pb.dashboard.monitoring.errors.all.table.view.ErrorTable;
import com.pb.dashboard.monitoring.errors.all.table.view.RecipientTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class FirstView extends VerticalLayout implements Observer<FirstModelI> {

    private FirstControllerI controller;
    private FirstModelI model;

    private Panel searchPanel;
    private SearchView searchView;

    private Panel tablePanel;

    private ClickListener listener;

    public FirstView(FirstControllerI controller, FirstModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        initSearchPanel();
        initTablePanel();
        initListener();
    }

    private void initListener() {
        listener = new ClickListener() {
            @Override
            public void clickCode(String code) {
                model.setCode(code);
            }
        };
    }

    private void initSearchPanel() {
        searchPanel = new Panel();
        searchPanel.setWidth("400px");
        addComponent(searchPanel);
        setComponentAlignment(searchPanel, Alignment.TOP_RIGHT);
    }

    private void initTablePanel() {
        tablePanel = new Panel();
        tablePanel.setContent(createErrorTable(new ArrayList<ErrorModel>()));
        addComponent(tablePanel);
    }

    public void setSearch(SearchView search) {
        this.searchView = search;
        searchPanel.setContent(search);
    }

    @Override
    public void modified(FirstModelI obj) {
        setList(obj.getList());
    }

    public void setList(List list) {
        Table table = createErrorTable(new ArrayList<ErrorModel>());
        if (!list.isEmpty()) {
            if (list.get(0) instanceof ErrorModel) {
                table = createErrorTable(list);
            }
            if (list.get(0) instanceof RecipientModel) {
                table = createRecipientTable(list);
            }
        }
        tablePanel.setContent(table);
    }

    private Table createErrorTable(List<ErrorModel> list) {
        ErrorTable table = new ErrorTable(list);
        table.setListener(listener);
        return table;
    }

    private Table createRecipientTable(List<RecipientModel> list) {
        RecipientTable table = new RecipientTable(list);
        table.setListener(listener);
        return table;
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }
}