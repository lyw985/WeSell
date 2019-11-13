package com.hodanet.common.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author lance.lengcs
 * @version 2012-7-18 5:39:44
 * 
 * <pre>
 *    з.
 * </pre>
 */
public class ServiceAspect {

    /** ־. */
    private final Logger logger = Logger.getLogger(ServiceAspect.class);

    /**
     * Around.
     * 
     * @param joinPoint .
     * @return .
     * @throws Throwable .
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long end = System.currentTimeMillis();
        if (logger.isDebugEnabled()) {
            logger.debug(joinPoint.toShortString() + "[cost:" + (end - begin) + "]");
        }
        return object;
    }
}
