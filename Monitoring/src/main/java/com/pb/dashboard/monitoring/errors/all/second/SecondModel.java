package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.components.observers.Observers;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;

import java.util.Collections;
import java.util.List;

/**
 * Created by vlad
 * Date: 12.09.14
 */
public class SecondModel implements SecondModelI {

    private Observers<Observer, SecondModelI> observers = new Observers<Observer, SecondModelI>();
    private String code;
    private PageType pageType;
    private boolean descriptionVisible;
    private ErrorDetailI errorDetail;
    private List list;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public boolean isDescriptionVisible() {
        return descriptionVisible;
    }

    public void setDescriptionVisible(boolean descriptionVisible) {
        this.descriptionVisible = descriptionVisible;
    }

    @Override
    public ErrorDetailI getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetailI errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public List getList() {
        return list == null ? Collections.emptyList() : list;
    }

    @Override
    public void notifyModifiedAll() {
        observers.notifyModified(this);
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public void addObserver(Observer<SecondModelI> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<SecondModelI> observer) {
        observers.remove(observer);
    }
}