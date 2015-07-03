package com.pb.dashboard.itdashbord.errors.error.title.dater;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;

public class DaterController {

    private DaterView view;

    public DaterController(ErrorsFiltersListener filter) {
        view = new DaterView(filter);

    }

    public DaterView getView() {
        return view;
    }
}
