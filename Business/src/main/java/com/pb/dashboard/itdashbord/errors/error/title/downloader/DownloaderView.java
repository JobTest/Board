package com.pb.dashboard.itdashbord.errors.error.title.downloader;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class DownloaderView extends HorizontalLayout {

    private Button csvButton;
    private Button xlsButton;

    public Button getXlsButton() {
        return xlsButton;
    }

    public DownloaderView() {
        setWidth("200px");

        csvButton = new Button();

        csvButton.setIcon(new ThemeResource("icons/csv.jpeg"));
        addComponent(csvButton);
        setComponentAlignment(csvButton, Alignment.MIDDLE_CENTER);

        xlsButton = new Button();
        xlsButton.setImmediate(true);
        xlsButton.setIcon(new ThemeResource("icons/xls.jpeg"));

        addComponent(xlsButton);
        setComponentAlignment(xlsButton, Alignment.MIDDLE_CENTER);

    }

}
