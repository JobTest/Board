package com.pb.dashboard.server.dao.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vlad
 * Date: 23.03.15_13:55
 */

public class DaoFactory implements DaoFactoryI {

    @Autowired
    private DashboardDaoI dashboardService;
    @Autowired
    private DescriptionDaoI descriptionService;
    @Autowired
    private IQDaoI iqService;
    @Autowired
    private VitrinaDaoI vitrinaService;

    @Override
    public DashboardDaoI getDashboard() {
        return dashboardService;
    }

    @Override
    public DescriptionDaoI getDescription() {
        return descriptionService;
    }

    @Override
    public IQDaoI getIq() {
        return iqService;
    }

    @Override
    public VitrinaDaoI getVitrina() {
        return vitrinaService;
    }
}
