package com.pb.dashboard.server.dao.service.impl;

import com.pb.dashboard.server.dao.entity.description.DescriptionCompany;
import com.pb.dashboard.server.dao.service.DescriptionDaoI;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by vlad
 * Date: 05.03.15_13:49
 */
@Service
public class DescriptionDao implements DescriptionDaoI {

    @PersistenceContext(unitName = "descriptionUnit")
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public DescriptionCompany getDescriptionCompany(Integer companyId) {
        try {
            DescriptionCompany company = (DescriptionCompany) em.createNativeQuery("exec dbo.dashboard_GetCompanyName ?",
                    DescriptionCompany.class)
                    .setParameter(1, companyId).getSingleResult();
            company.setId(companyId);
            return company;
        } catch (NoResultException e) {
            return null;
        }
    }
}
