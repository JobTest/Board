package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;

import java.util.List;

/**
 * Created by vlad
 * Date: 13.01.15_16:27
 */
public interface SecondModelI {

    public String getCode();

    public boolean isDescriptionVisible();

    public PageType getPageType();

    public ErrorDetailI getErrorDetail();

    public List getList();

    public void notifyModifiedAll();

    public void addObserver(Observer<SecondModelI> observer);

    public void removeObserver(Observer<SecondModelI> observer);

}
