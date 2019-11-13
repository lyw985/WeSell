package com.hodanet.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hodanet.common.entity.vo.PageData;

/**
 * <pre>
 *    DAOӿ.
 * </pre>
 */
public interface BaseDao {

    /**
     * .
     * 
     * @param entity ҪĶ
     * @return ID
     */
    Serializable save(Object entity);

    /**
     * .
     * 
     * @param entitys ҪĶ
     * @return ID
     */
    Serializable[] save(Object[] entitys);

    /**
     * .
     * 
     * @param entity ҪĶ
     */
    void saveOrUpdate(Object entity);

    /**
     * .
     * 
     * @param entitys ҪĶ
     */
    void saveOrUpdate(Object[] entitys);

    /**
     * ¶.
     * 
     * @param entity ҪµĶ
     */
    void update(Object entity);

    /**
     * ¶.
     * 
     * @param entitys ҪµĶ
     */
    void update(Object[] entitys);

    /**
     * ɾ.
     * 
     * @param entity ҪɾĶ.
     */
    void delete(Object entity);

    /**
     * ɾ.
     * 
     * @param entitys ҪɾĶ
     */
    void delete(Object[] entitys);

    /**
     * ɾ.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @param id ҪɾĶ ID.
     */
    <T> void delete(Class<T> entityClass, Serializable id);

    /**
     * ɾ.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @param ids ҪɾĶID
     */
    <T> void delete(Class<T> entityClass, Serializable[] ids);

    /**
     * ִUPDATEDELETEINSERT INTO䣬Ӱļ¼.
     * 
     * @param hql HQL䣬α﷨ṹ:( UPDATE | DELETE ) FROM? EntityName (WHERE where_conditions)?
     * @return Ӱļ¼
     */
    int executeUpdate(String hql, Object... objects);

    /**
     * ʵͺIDȡ.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @param id ĿID
     * @return ҪȡĶ
     */
    <T> T get(Class<T> entityClass, Serializable id);

    /**
     * ݶͻȡж.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @return 󼯺
     */
    <T> List<T> get(Class<T> entityClass);

    /**
     * ݶͻȡҳ.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @param pager ҳϢ
     * @return ҳѯ
     */
    <T> PageData<T> get(Class<T> entityClass, PageData<T> pager);

    /**
     * ݶͻȡҳ.
     * 
     * @param <T> Ŀ
     * @param entityClass Ŀ
     * @param firstResult ׼¼
     * @param maxResults Ҫȡļ¼
     * @return ѯ󼯺
     */
    <T> List<T> get(Class<T> entityClass, int firstResult, int maxResults);

    /**
     * ִSQL(INSERT\UPDATE\DELETE).
     * 
     * @param sql SQL
     * @return Ӱ
     */
    int executeSql(String sql);

    /**
     * ѯָSQL,н.
     * 
     * @param sql SQL
     * @return Object[]
     */
    List querySql(String sql);

    /**
     * ѯָSQL,н.
     * 
     * @param <T> Ŀ
     * @param class1 Ŀ
     * @param sql SQL
     * @return Object[]
     */
    <T> List<T> querySql(String sql, Class<T> class1);

    /**
     * ҳѯָSQL,طҳ.
     * 
     * @param sql SQL
     * @param pageData ҳ
     * @return ҳ
     */
    PageData<Object> querySql(String sql, PageData<Object> pageData);

    /**
     * ҳѯָSQL,طҳ.
     * 
     * @param <T> Ŀ
     * @param sql SQL
     * @param class1 Ŀ
     * @param pageData ҳ
     * @return ҳ
     */
    <T> PageData<T> querySql(String sql, Class<T> class1, PageData<T> pageData);

    /**
     * ҳѯָSQL,طҳ.
     * 
     * @param sql SQL
     * @param totalSql ڲѯҳSQL
     * @param pageData ҳ
     * @return ҳ
     */
    PageData<Object> querySql(String sql, String totalSql, PageData<Object> pageData);

    /**
     * ҳѯָSQL,طҳ.
     * 
     * @param <T> Ŀ
     * @param sql SQL
     * @param totalSql ڲѯҳSQL
     * @param class1 Ŀ
     * @param pageData ҳ
     * @return ҳ
     */
    <T> PageData<T> querySql(String sql, String totalSql, Class<T> class1, PageData<T> pageData);

    /**
     * ݴĹвѯ.
     * 
     * @param <T> Ŀ
     * @param criteria 
     * @return Ŀ󼯺
     */
    <T> List<T> get(DetachedCriteria criteria);

    /**
     * ݴĹзҳѯ.
     * 
     * @param <T> Ŀ
     * @param criteria 
     * @param firstResult ׼¼
     * @param maxResults Ҫȡļ¼
     * @return Ŀ󼯺
     */
    <T> List<T> get(DetachedCriteria criteria, int firstResult, int maxResults);

    /**
     * ѯָHQL,н.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @return Ŀ󼯺
     */
    <T> List<T> queryHql(String hql);

    /**
     * ѯָHQL,н.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param param 
     * @return Ŀ󼯺
     */
    <T> List<T> queryHql(String hql, Object param);

    /**
     * ѯָHQL,н.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param params 
     * @return Ŀ󼯺
     */
    <T> List<T> queryHql(String hql, Object... params);

    /**
     * ѯָHQL,ص.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql);

    /**
     * ѯָHQL,ص.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param param 
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql, Object param);

    /**
     * ѯָHQL,ص.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param params 
     * @return 
     */
    <T> T queryHqlUniqueResult(String hql, Object... params);

    /**
     * ҳѯָHQL,طҳ.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param pageData ҳ
     * @param params 
     * @return ҳ
     */
    <T> PageData<T> queryHqlPageData(String hql, PageData<T> pageData, Object... params);

    /**
     * ҳѯָHQL,طҳ.
     * 
     * @param <T> Ŀ
     * @param hql HQL
     * @param totalHql ڲѯҳHQL
     * @param pageData ҳ
     * @param params
     * @return ҳ
     */
    <T> PageData<T> queryHqlPageData(String hql, String totalHql, PageData<T> pageData, Object... params);

    /**
     * ݲѯҳ
     * 
     * @param hql
     * @param startIndex
     * @param size
     * @param params
     * @return
     */

    <T> List<T> queryHqlLimit(String hql, int startIndex, int size, Object... params);

    /**
     * ȡJdbcTemplate
     * 
     * @return
     */
    JdbcTemplate getJdbcTemplate();

}
