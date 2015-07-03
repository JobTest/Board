package com.pb.dashboard.dao.daomanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vlad
 * Date: 14.04.15_14:07
 */
public class DashEntityManager {

    protected EntityManagerFactory emf;

    protected DashEntityManager(String unitName) {
        createEntityManagerFactory(unitName);
    }

    protected void createEntityManagerFactory(String nameUnit) {
        emf = Persistence.createEntityManagerFactory(nameUnit);
    }

    @SuppressWarnings("unchecked")
    protected <Type> List<Type> execNativeQueryList(String queryStr, Class<Type> aClass, Object... params) {
        return execNativeQueryList(queryStr, aClass, aClass, params);
    }

    @SuppressWarnings("unchecked")
    protected <Type, TypeOut> List<TypeOut> execNativeQueryList(String queryStr, Class<Type> entityClass, Class<TypeOut> toClass, Object... params) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(queryStr, entityClass);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    protected <Type> Type execNativeQueryOne(String queryStr, Class<Type> entityClass, Object... params) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createNativeQuery(queryStr, entityClass);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return (Type) query.getSingleResult();
        } finally {
            em.close();
        }
    }
}