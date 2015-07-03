package com.pb.dashboard.server.dao.service;

/**
 * Created by vlad
 * Date: 08.04.15_12:15
 */
public interface DaoFactoryI {

    public DashboardDaoI getDashboard();

    public DescriptionDaoI getDescription();

    public IQDaoI getIq();

    public VitrinaDaoI getVitrina();

}
