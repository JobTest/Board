package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.core.hierarchy.Dashboard;
import com.pb.dashboard.monitoring.components.filter.FilterModel;
import com.pb.dashboard.monitoring.sessions.model.UrlModel;
import com.pb.dashboard.monitoring.sessions.model.UrlModelBuilder;

import java.util.Map;

/**
 * Created by vlad
 * Date: 23.12.14_10:47
 */
public class SessionsController implements SessionsControllerI {

    private SessionsModelI model;
    private FilterModel filterModel;
    private SessionsView view;

    private UrlModel urlModel;

    public SessionsController(SessionsView view) {
        this.view = view;
        init();
        initUrlModel();
    }

    private void init() {
        Map<String, String> params = view.getParameters();
        model = SessionsModelBuilder.build(params);
        filterModel = new FilterModel();
    }

    private void initUrlModel() {
        urlModel = new UrlModelBuilder().buildModel(view.getParameters());
    }

    @Override
    public String getUrlBack() {
        String path = Dashboard.Biplane.Monitoring.Timings.PATH;
        String url = UrlModelBuilder.buildUrl(urlModel);
        return path + '/' + url;
    }

    @Override
    public SessionsModelI getModel() {
        return model;
    }

    @Override
    public FilterModel getFilterModel() {
        return filterModel;
    }

}
