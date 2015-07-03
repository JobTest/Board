package com.pb.dashboard.server.dao.service;

import com.pb.dashboard.server.dao.entity.dashboard.CountSales;
import com.pb.dashboard.server.dao.entity.dashboard.MassPaysAuthEntity;

import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_10:42
 */
public interface DashboardDaoI {

    public List<CountSales> getCountSaleses(Integer year, Integer month, String bankAttr, Integer ticketId);

    public List<MassPaysAuthEntity> getMassPaysAuth();
}
