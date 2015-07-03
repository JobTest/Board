package com.pb.dashboard.server.dao.service.impl;

import com.pb.dashboard.server.dao.log.LogUtils;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by vlad
 * Date: 14.04.15_14:07
 */
public abstract class DaoAbs {

    public abstract EntityManagerFactory getEmf();

    @SuppressWarnings("unchecked")
    protected <Type> List<Type> execNativeQueryList(String queryStr, Class<Type> aClass, Logger logger, Object... params) {
        return execNativeQueryList(queryStr, aClass, aClass, logger, params);
    }

    @SuppressWarnings("unchecked")
    protected <Type, TypeOut> List<TypeOut> execNativeQueryList(String queryStr, Class<Type> entityClass, Class<TypeOut> toClass, Logger logger, Object... params) {
        logger.debug(LogUtils.formatQueryLog(queryStr, params));
        EntityManager em = getEmf().createEntityManager();
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
    protected <Type> Type execNativeQueryOne(String queryStr, Class<Type> entityClass, Logger logger, Object... params) {
        logger.debug(LogUtils.formatQueryLog(queryStr, params));
        EntityManager em = getEmf().createEntityManager();
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