package com.pb.dashboard.monitoring.errors.all.window;

import com.vaadin.ui.Window;

public class SessionWindow extends Window {

    private static final String CAPTION = "Session";
    private static final long serialVersionUID = 4293669995060069439L;

    public SessionWindow() {
        init();
    }

    public void init() {
        setCaption(CAPTION);
        setWidth("95%");
        setHeight("95%");
        center();
        setModal(true);
        setVisible(false);
    }
}
