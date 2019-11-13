package com.hodanet.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lance.lengcs
 * @version 2012-7-23 1:46:50
 * 
 * <pre>
 *    Dao.
 * </pre>
 */
public abstract class AbstractDaoService {

    @Autowired
    private BaseDao dao;

    public BaseDao getDao() {
        return dao;
    }

}
