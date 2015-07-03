package com.pb.dashboard.itdashbord.errors;

import com.pb.dashboard.itdashbord.errors.error.title.TitleController;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.VerticalLayout;

public class ErrorsController {

    private VerticalLayout view;

    private TitleController titleController;

    private ErrorsFiltersListener filter = new ErrorsFiltersListener();

    public ErrorsController() {
        init();
    }

    private void init() {
        titleController = new TitleController(filter);

        view = new VerticalLayout();
        view.setSpacing(true);
        view.setMargin(new MarginInfo(true, false, true, true));

        view.addComponent(titleController);
        view.setExpandRatio(titleController, 1f);
    }

    public VerticalLayout getView() {
        return view;
    }
}
