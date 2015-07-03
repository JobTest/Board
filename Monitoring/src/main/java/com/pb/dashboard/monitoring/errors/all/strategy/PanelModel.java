package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.monitoring.errors.all.filter.FilterModelI;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelAdapter;

public class PanelModel implements PanelModelI {

    private PageNumber number = PageNumber.FIRST;
    private PageType type = PageType.ERROR;

    private NavigatorModelAdapter navigatorModel;
    private FilterModelI filterModel;

    private String codeIdFirstPage;
    private boolean openSession;

    public PageNumber getNumber() {
        return number;
    }

    public void setNumber(PageNumber number) {
        this.number = number;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public String getCodeIdFirstPage() {
        return codeIdFirstPage;
    }

    public void setCodeIdFirstPage(String codeIdFirstPage) {
        this.codeIdFirstPage = codeIdFirstPage;
    }

    public boolean isOpenSession() {
        return openSession;
    }

    public void setOpenSession(boolean openSession) {
        this.openSession = openSession;
    }

    public NavigatorModelAdapter getNavigatorModel() {
        return navigatorModel;
    }

    public void setNavigatorModel(NavigatorModelAdapter navigatorModel) {
        this.navigatorModel = navigatorModel;
    }

    public FilterModelI getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(FilterModelI filterModel) {
        this.filterModel = filterModel;
    }
}