package com.hodanet.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lance.lengcs
 * @version 2012-7-18 5:42:45
 * 
 * <pre>
 *    쳣.
 * </pre>
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /** ־. */
    private static final Logger LOGGER     = Logger.getLogger(GlobalExceptionResolver.class);
    private static final String ERROR_PAGE = "error";

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        // 쳣Ϣrequest
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("GlobalException >>:" + ex.getMessage());
        }
        LOGGER.error("GlobalException >>:" + ex.getMessage(), ex);
        // 쳣
        request.setAttribute("ex", ex);

        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("x-requested-with:XMLHttpRequest");
            }
            response.setStatus(500);
        }
        return new ModelAndView(ERROR_PAGE);
    }

}
