package com.pb.dashboard.itdashbord.errors.error.title.filter;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;

public class FilterController {

    private FilterView view;
    private ErrorsFiltersListener filter;

    public FilterController(ErrorsFiltersListener filter) {
        this.filter=filter;
        init();
    }

    private void init() {
        view = new FilterView(filter);
    }

    public FilterView getView() {
        return view;
    }
}
