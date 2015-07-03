package com.pb.dashboard.itdashbord;

import com.pb.dashboard.itdashbord.errors.ErrorsController;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;

public class ErrorsView extends VerticalLayout implements View {

    private VerticalLayout content;

    public ErrorsView() {
        init();
    }

    private void init() {
        setSizeFull();
        addStyleName("timings-view");
        initComponent();
    }

    private void initComponent() {
        VerticalLayout row = new VerticalLayout();
        row.setSizeFull();
        row.setMargin(new MarginInfo(true, true, false, true));
        row.setSpacing(false);
        addComponent(row);
        setExpandRatio(row, 2);

        initContent();
        row.addComponent(createPanel());
    }

    private CssLayout createPanel() {
        CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();
        initContent();
        panel.addComponent(content);
        return panel;
    }

    private void initContent() {
        content = new VerticalLayout();
        content.setSpacing(true);
        content.addComponent(new ErrorsController().getView());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}