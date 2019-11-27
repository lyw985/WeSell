package com.hodanet.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hodanet.common.entity.vo.PageData;

/**
 * <pre>
 *    DAO
 * </pre>
 */
public interface BaseDao {

    /**
     * .
     * 
     * @param entity 
     * @return ID
     */
    Serializable save(Object entity);

    /**
     * .
     * 
     * @param entitys 
     * @return ID
     */
    Serializable[] save(Object[] entitys);

    /**
     * .
     * 
     * @param entity 
     */
    void saveOrUpdate(Object entity);

    /**
     * .
     * 
     * @param entitys 
     */
    void saveOrUpdate(Object[] entitys);

    /**
     * 
     * 
     * @param entity 
     */
    void update(Object entity);

    /**
     * 
     * 
     * @param entitys 
     */
    void update(Object[] entitys);

    /**
     * ɾ.
     * 
     * @param entity 
     */
    void delete(Object entity);

    /**
     * ɾ.
     * 
     * @param entitys 
     */
    void delete(Object[] entitys);

    /**
     * ɾ.
     * 
     * @param <T> 
     * @param entityClass 
     * @param id  ID.
     */
    <T> void delete(Class<T> entityClass, Serializable id);

    /**
     * ɾ.
     * 
     * @param <T> 
     * @param entityClass 
     * @param ids ID
     */
    <T> void delete(Class<T> entityClass, Serializable[] ids);

    /**
     * ִUPDATEDELETEINSERT INTO
     * 
     * @param hql HQL( UPDATE | DELETE ) FROM? EntityName (WHERE where_conditions)?
     * @return 
     */
    int executeUpdate(String hql, Object... objects);

    /**
     * ʵͺIDȡ.
     * 
     * @param <T> 
     * @param entityClass 
     * @param id ID
     * @return 
     */
    <T> T get(Class<T> entityClass, Serializable id);

    /**
     * ݶ
     * 
     * @param <T> 
     * @param entityClass 
     * @return 
     */
    <T> List<T> get(Class<T> entityClass);

    /**
     * ݶ
     * 
     * @param <T> 
     * @param entityClass 
     * @param pager Ϣ
     * @return ѯ
     */
    <T> PageData<T> get(Class<T> entityClass, PageData<T> pager);

    /**
     * ݶ
     * 
     * @param <T> 
     * @param entityClass 
     * @param firstResult 
     * @param maxResults 
     * @return 
     */
    <T> List<T> get(Class<T> entityClass, int firstResult, int maxResults);

    /**
     * 
     * @param sql SQL
     * @return 
     */
    int executeSql(String sql);

    /**
     * 
     * @param sql SQL
     * @return Object[]
     */
    List querySql(String sql);

    /**
     * 
     * @param <T> 
     * @param class1 
     * @param sql SQL
     * @return Object[]
     */
    <T> List<T> querySql(String sql, Class<T> class1);

    /**
     * 
     * @param sql SQL
     * @param pageData 
     * @return 
     */
    PageData<Object> querySql(String sql, PageData<Object> pageData);

    /**
     * 
     * @param <T> 
     * @param sql SQL
     * @param class1 
     * @param pageData 
     * @return 
     */
    <T> PageData<T> querySql(String sql, Class<T> class1, PageData<T> pageData);

    /**
     * 
     * @param sql SQL
     * @param totalSql 
     * @param pageData 
     * @return 
     */
    PageData<Object> querySql(String sql, String totalSql, PageData<Object> pageData);

    /**
     * 
     * @param <T> 
     * @param sql SQL
     * @param totalSql 
     * @param class1 
     * @param pageData 
     * @return 
     */
    <T> PageData<T> querySql(String sql, String totalSql, Class<T> class1, PageData<T> pageData);

    /**
     * 
     * @param <T> 
     * @param criteria 
     * @return 
     */
    <T> List<T> get(DetachedCriteria criteria);

    /**
     * 
     * @param <T> 
     * @param criteria 
     * @param firstResult ׼¼
     * @param maxResults Ҫȡļ¼
     * @return 
     */
    <T> List<T> get(DetachedCriteria criteria, int firstResult, int maxResults);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @return 
     */
    <T> List<T> queryHql(String hql);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param param 
     * @return 
     */
    <T> List<T> queryHql(String hql, Object param);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param params 
     * @return 
     */
    <T> List<T> queryHql(String hql, Object... params);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param param 
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql, Object param);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param params 
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql, Object... params);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param pageData 
     * @param params 
     * @return 
     */
    <T> PageData<T> queryHqlPageData(String hql, PageData<T> pageData, Object... params);

    /**
     * 
     * @param <T> 
     * @param hql HQL
     * @param totalHql
     * @param pageData 
     * @param params
     * @return 
     */
    <T> PageData<T> queryHqlPageData(String hql, String totalHql, PageData<T> pageData, Object... params);

    /**
     * 
     * @param hql
     * @param startIndex
     * @param size
     * @param params
     * @return
     */

    <T> List<T> queryHqlLimit(String hql, int startIndex, int size, Object... params);

    /**
     * 
     * @return
     */
    JdbcTemplate getJdbcTemplate();

}
