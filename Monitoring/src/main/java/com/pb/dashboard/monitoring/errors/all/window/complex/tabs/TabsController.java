package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.model.Complex;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import org.vaadin.aceeditor.AceMode;

/**
 * Created by vlad
 * Date: 30.09.14
 */
public class TabsController implements TabsControllerI {

    public static final String UNDEFINED_TYPE = "Неизвестный тип данных";
    private TabsModel model;
    private TabsView view;


    private TabSheet.SelectedTabChangeListener listener;
    private ComponentBuilder builder;

    public TabsController(Complex complex) {
        model = new TabsModel();
        model.setComplex(complex);

        builder = new ComponentBuilder(model);
        init();
    }

    private void init() {
        initListener();
        initView();
    }

    private void initListener() {
        listener = new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                loadSelectedTab();
            }
        };
    }

    private void loadSelectedTab() {
        Component component = getComponent(view.getSelectedCaption());
        if (component != view.getSelectedTab()) {
            view.setTabComponent(view.getSelectedTab(), component);
        }
    }

    private void initView() {
        view = new TabsView();
        initTabs();
        view.addSelectedTabChangeListener(listener);
    }

    private void initTabs() {
        for (TabType complexTab : TabType.values()) {
            if (complexTab.isVisible(model.getComplex())) {
                view.addTab(complexTab.getName());
            }
        }
    }

    private Component getComponent(String caption) {
        Component component = model.getComponent(caption);
        if (component == null) {
            TabType tab = TabType.nameToComplexTab(caption);
            if (tab == null) {
                ComponentBuilder.createAceEditor(UNDEFINED_TYPE, AceMode.text);
            }
            component = builder.loadComponent(tab);
            model.setComponent(tab, component);
        }
        return component;
    }

    @Override
    public void setSessionId(String sessionId) {
        model.setSessionId(sessionId);
        view.setEnabled(true);
        loadSelectedTab();
    }

    @Override
    public TabsView getView() {
        return view;
    }
}