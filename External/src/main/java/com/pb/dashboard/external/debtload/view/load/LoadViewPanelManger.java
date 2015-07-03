package com.pb.dashboard.external.debtload.view.load;

import com.pb.dashboard.external.debtload.view.DatePicker;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

public class LoadViewPanelManger {

    private static final Logger LOG = Logger.getLogger(LoadViewPanelManger.class);
    private LoadView loadView; // main view

    private VerticalLayout content;
    private LoadTablePanel manTablePanel = new LoadTablePanel(true);
    private LoadTablePanel regTablePanel = new LoadTablePanel(false);
    private Label mDateLabel;
    private DatePicker datePicker = new DatePicker(this);

    private Component manTablePanelView;
    private Component regTablePanelView;

    public LoadViewPanelManger(LoadView loadView) {
        this.loadView = loadView;
    }

    public Component getContent() {
        content = new VerticalLayout();
        content.setSizeFull();
        content.addComponent(buildContentHeader());

        LOG.info(datePicker.getDate());
        manTablePanel.setDate(datePicker.getDate());
        regTablePanel.setDate(datePicker.getDate());
        manTablePanelView = manTablePanel.getView();
        regTablePanelView = regTablePanel.getView();


        update();

        return content;
    }

    public Component buildContentHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setSpacing(true);
        header.setWidth("100%");
        mDateLabel = new Label();
        mDateLabel.addStyleName("m-date");
        header.addComponents(datePicker, mDateLabel);
        header.setComponentAlignment(datePicker, Alignment.TOP_LEFT);
        header.setComponentAlignment(mDateLabel, Alignment.TOP_RIGHT);
        return header;
    }

    public void update() {
        load();
        // update date label
        mDateLabel.setValue("Время модификации: " + regTablePanel.getmDate());
        // check if manual load table panel should be displayed
        manTablePanel.setDate(datePicker.getDate());
        regTablePanel.setDate(datePicker.getDate());

        content.removeComponent(manTablePanelView);
        content.removeComponent(regTablePanelView);
        if (!manTablePanel.isEmpty()) {
            content.addComponent(manTablePanelView);
            manTablePanel.updateDisplay();
        }
        content.addComponent(regTablePanelView);
        regTablePanel.updateDisplay();
    }

    public void load() {
        manTablePanel.loadData(loadView.getLang(), datePicker.getDate());
        regTablePanel.loadData(loadView.getLang(), datePicker.getDate());
    }

}
