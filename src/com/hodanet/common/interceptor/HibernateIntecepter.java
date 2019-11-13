package com.hodanet.common.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * @author lance.lengcs
 * @version 2012-7-18 5:32:09
 * 
 * <pre>
 *    Hibernate.
 * </pre>
 */
public class HibernateIntecepter extends EmptyInterceptor {

    /** ޸ʱֶ. */
    private final String      MODIFIED_TIME    = "modifiedTime";
    /** ʱֶ. */
    private final String      CREATE_TIME      = "createTime";
    /** ־. */
    private final Logger      logger           = Logger.getLogger(HibernateIntecepter.class);

    /**
     * Ĭ.
     */
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[],
     * java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (logger.isDebugEnabled()) {
            logger.debug("isDebugEnabled");
        }
        for (int i = 0; i < propertyNames.length; i++) {
            if (CREATE_TIME.equalsIgnoreCase(propertyNames[i])) {
                state[i] = new Date();
            } else if (MODIFIED_TIME.equalsIgnoreCase(propertyNames[i])) {
                state[i] = new Date();
            }
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[],
     * java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
     */
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
                                String[] propertyNames, Type[] types) {
        if (logger.isDebugEnabled()) {
            logger.debug("isDebugEnabled");
        }
        for (int i = 0; i < propertyNames.length; i++) {
            if (MODIFIED_TIME.equalsIgnoreCase(propertyNames[i])) {
                currentState[i] = new Date();
            }
        }
        return true;
    }

}
