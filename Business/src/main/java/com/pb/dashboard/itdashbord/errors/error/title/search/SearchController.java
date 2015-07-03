package com.pb.dashboard.itdashbord.errors.error.title.search;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;

public class SearchController {

    private SearchView view;

    public SearchController(ErrorsFiltersListener filter) {

        view = new SearchView(filter);
    }

    public SearchView getView() {
        return view;
    }
}
