package com.pb.dashboard.itdashbord.errors.error.title;

import com.vaadin.ui.GridLayout;

public class TitleView extends GridLayout {

    private static final int COLUMNS_COUNT = 2;
    private static final int ROWS_COUNT = 2;

    public TitleView() {
        init();
    }

    private void init() {
        setRows(ROWS_COUNT);
        setColumns(COLUMNS_COUNT);
        setSizeFull();
    }
}
