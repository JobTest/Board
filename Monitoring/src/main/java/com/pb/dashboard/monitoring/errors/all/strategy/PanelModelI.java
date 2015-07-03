package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.monitoring.errors.all.filter.FilterModelI;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelAdapter;

/**
 * Created by vlad
 * Date: 05.01.15_14:35
 */
public interface PanelModelI {

    public PageNumber getNumber();

    public void setNumber(PageNumber number);

    public PageType getType();

    public void setType(PageType type);

    public String getCodeIdFirstPage();

    public void setCodeIdFirstPage(String codeIdFirstPage);

    public boolean isOpenSession();

    public void setOpenSession(boolean openSession);

    public NavigatorModelAdapter getNavigatorModel();

    public void setNavigatorModel(NavigatorModelAdapter navigatorModel);

    public FilterModelI getFilterModel();

    public void setFilterModel(FilterModelI filterModel);
}
