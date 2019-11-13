package com.hodanet.common.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author lance.lengcs
 * @version 2012-7-23 11:41:49
 * 
 * <pre>
 *    DAO.
 * </pre>
 */
public abstract class AbstractDao implements BaseDao {

    /** ־. */
    private final Logger logger = Logger.getLogger(AbstractDao.class);

    /**
     * Ϣ־.
     * 
     * @param message .
     */
    public void logInfo(String message) {
        logger.info(message);
    }

    /**
     * ־.
     * 
     * @param message .
     */
    public void logDebug(String message) {
        logger.debug(message);
    }

    /**
     * ־.
     * 
     * @param message .
     * @param t .
     */
    public void logError(String message, Throwable t) {
        logger.error(message, t);
    }

    @Override
    public Serializable[] save(Object[] entitys) {
        Serializable[] ids = new Serializable[entitys.length];
        for (int i = 0; i < entitys.length; i++) {
            ids[i] = save(entitys[i]);
        }
        return ids;
    }

    @Override
    public void saveOrUpdate(Object[] entitys) {
        for (Object object : entitys) {
            update(object);
        }
    }

    @Override
    public void update(Object[] entitys) {
        for (Object object : entitys) {
            update(object);
        }
    }

    @Override
    public void delete(Object[] entitys) {
        for (Object object : entitys) {
            delete(object);
        }
    }

    @Override
    public <T> void delete(Class<T> entityClass, Serializable[] ids) {
        for (Serializable id : ids) {
            delete(entityClass, id);
        }
    }

    @Override
    public <T> T queryHqlUniqueResult(String hql) {
        List<T> list = queryHql(hql);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <T> T queryHqlUniqueResult(String hql, Object param) {
        List<T> list = queryHql(hql, param);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <T> T queryHqlUniqueResult(String hql, Object... params) {
        List<T> list = queryHql(hql, params);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
