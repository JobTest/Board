package com.pb.dashboard.external.debtload.view.load;

import com.pb.dashboard.external.debtload.view.AbstractView;
import com.vaadin.ui.Component;

public class LoadView extends AbstractView {

    private LoadViewPanelManger panelManager;

    public LoadView() {
        super("Протокол загрузки off-line задолженности");
    }

    @Override
    protected Component addContent() {
        panelManager = new LoadViewPanelManger(this);
        return panelManager.getContent();
    }

    @Override
    protected void update() {
        panelManager.update();
    }

}
