package com.hodanet.common.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hodanet.common.dao.AbstractDao;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-7-23 11:42:46
 * 
 * <pre>
 *    ݷhibernateӿڷװ.
 * </pre>
 */
@Repository
public class HibernateDaoImpl extends AbstractDao {

    /**
     * SpringṩHibernateװ.
     */
    @Autowired
    private HibernateTemplate template = null;
    @Autowired
    private JdbcTemplate      jdbcTemplate;

    /**
     * ȡHibernateTemplate.
     * 
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return template;
    }

    /**
     * ȡǰSession.
     * 
     * @return ǰSession
     */
    public Session getSession() {
        return template.getSessionFactory().getCurrentSession();
    }

    @Override
    public Serializable save(Object entity) {
        return getHibernateTemplate().save(entity);
    }

    @Override
    public void saveOrUpdate(Object entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void update(Object entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id) {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public <T> void delete(Class<T> entityClass, Serializable id) {
        T t = get(entityClass, id);
        if (t != null) {
            getHibernateTemplate().delete(t);
        }
    }

    @Override
    public int executeUpdate(String hql, Object... objects) {
        return getHibernateTemplate().bulkUpdate(hql, objects);
    }

    @Override
    public <T> List<T> get(Class<T> entityClass) {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    public <T> PageData<T> get(Class<T> entityClass, PageData<T> pager) {
        long total = queryEntityCount(entityClass);
        pager.setTotal(total);

        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        @SuppressWarnings("unchecked")
        List<T> list = getHibernateTemplate().findByCriteria(criteria, pager.getBeginIndex(), pager.getPageSize());
        pager.setData(list);
        return pager;
    }

    @Override
    public <T> List<T> get(Class<T> entityClass, int firstResult, int maxResults) {
        if (firstResult < 0) {
            throw new IllegalArgumentException("ʼС0");
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
        @SuppressWarnings("unchecked")
        List<T> list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);

        return list;
    }

    @Override
    public <T> List<T> get(DetachedCriteria criteria) {
        return get(criteria, 0, -1);
    }

    @Override
    public <T> List<T> get(DetachedCriteria criteria, int firstResult, int maxResults) {
        if (firstResult < 0) {
            throw new IllegalArgumentException("ʼС0");
        }

        @SuppressWarnings("unchecked")
        List<T> list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
        return list;
    }

    /**
     * ѯ.
     * 
     * @param entityName 
     * @return ¼
     */
    private long queryEntityCount(String entityName) {
        return (Long) getSession().createQuery("select count(*) from " + entityName).uniqueResult();
    }

    /**
     * ѯ.
     * 
     * @param <T> Ŀ
     * @param entityClass .
     * @return Ŀͼ¼
     */
    private <T> long queryEntityCount(Class<T> entityClass) {
        return queryEntityCount(entityClass.getClass().getName());
    }

    @Override
    public <T> List<T> queryHql(String hql) {
        return queryHql(hql, (Object[]) null);
    }

    @Override
    public <T> List<T> queryHql(String hql, Object param) {
        return queryHql(hql, new Object[] { param });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> queryHql(String hql, Object... params) {
        if (StringUtil.isBlank(hql)) {
            throw new IllegalArgumentException("HQLΪ");
        }
        return getHibernateTemplate().find(hql, params);
    }

    /**
     * ݲѯҳ
     * 
     * @param hql
     * @param startIndex
     * @param size
     * @param params
     * @return
     */
    @Override
    public <T> List<T> queryHqlLimit(String hql, int startIndex, int size, Object... params) {
        if (StringUtil.isBlank(hql)) {
            throw new IllegalArgumentException("HQLΪ");
        }

        Query query = getSession().createQuery(hql);

        if (params != null) {
            int idx = 0;
            for (Object param : params) {
                query.setParameter(idx, param);
                idx++;
            }
        }

        if (startIndex >= 0 && size >= 0) {
            query.setFirstResult(startIndex);
            query.setMaxResults(size);
        }

        query.setReadOnly(true);

        @SuppressWarnings("unchecked")
        List<T> list = query.list();
        if (list == null) {
            list = new ArrayList<T>();
        }
        return list;
    }

    @Override
    public <T> PageData<T> queryHqlPageData(String hql, PageData<T> pageData, Object... params) {
        if (StringUtil.isBlank(hql)) {
            throw new IllegalArgumentException("HQLΪ");
        }

        // ȡܼ¼
        String tmp = hql.toUpperCase();
        int b = tmp.indexOf("FROM"), e = tmp.lastIndexOf(" ORDER ");
        if (e > 0) {
            tmp = hql.substring(b, e);
        } else {
            tmp = hql.substring(b);
        }

        Query countQuery = getSession().createQuery("select count(*) " + tmp);
        if (params != null) {
            int idx = 0;
            for (Object param : params) {
                countQuery.setParameter(idx, param);
                idx++;
            }
        }

        long total = (Long) countQuery.uniqueResult();
        pageData.setTotal(total);

        Query dataQuery = getSession().createQuery(hql);
        if (params != null) {
            int idx = 0;
            for (Object param : params) {
                dataQuery.setParameter(idx, param);
                idx++;
            }
        }
        @SuppressWarnings("unchecked")
        List<T> data = dataQuery.setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);

        return pageData;
    }

    @Override
    public <T> PageData<T> queryHqlPageData(String hql, String totalHql, PageData<T> pageData, Object... params) {
        if (StringUtil.isBlank(hql) || StringUtil.isBlank(totalHql)) {
            throw new IllegalArgumentException("HQLΪ");
        }

        Query countQuery = getSession().createQuery(totalHql);
        if (params != null) {
            int idx = 0;
            for (Object param : params) {
                countQuery.setParameter(idx, param);
                idx++;
            }
        }
        // ȡܼ¼
        long total = (Long) countQuery.uniqueResult();
        pageData.setTotal(total);

        // ѯ
        Query dataQuery = getSession().createQuery(hql);
        if (params != null) {
            int idx = 0;
            for (Object param : params) {
                dataQuery.setParameter(idx, param);
                idx++;
            }
        }
        @SuppressWarnings("unchecked")
        List<T> data = dataQuery.setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);
        return pageData;
    }

    @Override
    public int executeSql(String sql) {
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List querySql(String sql) {
        if (StringUtil.isBlank(sql)) {
            throw new IllegalArgumentException("SQLΪ");
        }
        return getSession().createSQLQuery(sql).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> querySql(String sql, Class<T> class1) {
        if (StringUtil.isBlank(sql)) {
            throw new IllegalArgumentException("SQLΪ");
        }
        return getSession().createSQLQuery(sql).addEntity(class1).list();
    }

    @Override
    public PageData<Object> querySql(String sql, PageData<Object> pageData) {
        if (StringUtil.isBlank(sql)) {
            throw new IllegalArgumentException("SQLΪ");
        }

        // ȡܼ¼
        String tmp = sql.toUpperCase();
        int e = tmp.lastIndexOf(" ORDER ");
        if (e > 0) {
            tmp = sql.substring(0, e);
        }
        Integer total = (Integer) getSession().createSQLQuery("select count(*) from (" + tmp + ") tmp").uniqueResult();
        pageData.setTotal(total);

        // ѯ
        @SuppressWarnings("unchecked")
        List<Object> data = getSession().createSQLQuery(sql).setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);

        return pageData;
    }

    @Override
    public <T> PageData<T> querySql(String sql, Class<T> class1, PageData<T> pageData) {
        if (StringUtil.isBlank(sql)) {
            throw new IllegalArgumentException("SQLΪ");
        }

        // ȡܼ¼
        String tmp = sql.toUpperCase();
        int e = tmp.lastIndexOf(" ORDER ");
        if (e > 0) {
            tmp = sql.substring(0, e);
        }
        BigDecimal total = (BigDecimal) getSession().createSQLQuery("select count(*) from (" + tmp + ") tmp").uniqueResult();
        pageData.setTotal(total.intValue());

        // ѯ
        @SuppressWarnings("unchecked")
        List<T> data = getSession().createSQLQuery(sql).addEntity(class1).setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);

        return pageData;
    }

    @Override
    public PageData<Object> querySql(String sql, String totalSql, PageData<Object> pageData) {
        if (StringUtil.isBlank(sql) || StringUtil.isBlank(totalSql)) {
            throw new IllegalArgumentException("SQL䲻Ϊ");
        }
        @SuppressWarnings("unchecked")
        List<Integer> total = getSession().createSQLQuery(totalSql).list();
        if (!total.isEmpty()) {
            pageData.setTotal(total.get(0));
        } else {
            throw new RuntimeException("޷ȡܼ¼!");
        }

        @SuppressWarnings("unchecked")
        List<Object> data = getSession().createSQLQuery(sql).setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);

        return pageData;
    }

    @Override
    public <T> PageData<T> querySql(String sql, String totalSql, Class<T> class1, PageData<T> pageData) {
        if (StringUtil.isBlank(sql) || StringUtil.isBlank(totalSql)) {
            throw new IllegalArgumentException("SQL䲻Ϊ");
        }
        @SuppressWarnings("unchecked")
        List<Integer> total = getSession().createSQLQuery(totalSql).list();
        if (!total.isEmpty()) {
            pageData.setTotal(total.get(0));
        } else {
            throw new RuntimeException("޷ȡܼ¼!");
        }

        @SuppressWarnings("unchecked")
        List<T> data = getSession().createSQLQuery(sql).addEntity(class1).setFirstResult(pageData.getBeginIndex()).setMaxResults(pageData.getPageSize()).list();
        pageData.setData(data);

        return pageData;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
