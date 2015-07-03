package com.pb.dashboard.monitoring.sessions;

import com.pb.dashboard.monitoring.components.filter.FilterModel;

/**
 * Created by vlad
 * Date: 23.12.14_10:37
 */
public interface SessionsControllerI {

    public String getUrlBack();

    public SessionsModelI getModel();

    public FilterModel getFilterModel();
}
