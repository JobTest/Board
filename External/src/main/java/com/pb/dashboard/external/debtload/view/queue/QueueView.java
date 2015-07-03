package com.pb.dashboard.external.debtload.view.queue;

import com.pb.dashboard.external.debtload.view.AbstractView;
import com.vaadin.ui.Component;

public class QueueView extends AbstractView {

    private QueueViewPanelManager panelManager;

    public QueueView() {
        super("Очередь на загрузку");
    }

    @Override
    protected Component addContent() {
        panelManager = new QueueViewPanelManager(this);
        return panelManager.getContentPanel();
    }

    @Override
    protected void update() {
        panelManager.updateByCountry();
    }
}
