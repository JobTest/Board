package com.pb.dashboard.server.dao.service.impl;

import com.pb.dashboard.server.dao.entity.dashboard.CountSales;
import com.pb.dashboard.server.dao.entity.dashboard.MassPaysAuthEntity;
import com.pb.dashboard.server.dao.service.DashboardDaoI;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vlad
 * Date: 05.03.15_10:40
 */
@Service
public class DashboardDao implements DashboardDaoI {

    @PersistenceContext(unitName = "dashboardUnit")
    private EntityManager em;

    @Override
    public List<CountSales> getCountSaleses(Integer year, Integer month, String bankAttr, Integer ticketId) {
        Query query = em.createNativeQuery("{call getCountSalesByChannel(?,?,?,?)}",
                CountSales.class)
                .setParameter(1, year)
                .setParameter(2, month)
                .setParameter(3, bankAttr)
                .setParameter(4, ticketId);
        List<CountSales> result = query.getResultList();
        return result;
    }

    @Override
    public List<MassPaysAuthEntity> getMassPaysAuth() {
        Query query = em.createQuery("select pays from MassPaysAuthEntity as pays ORDER BY date desc");
        return query.getResultList();
    }
}
