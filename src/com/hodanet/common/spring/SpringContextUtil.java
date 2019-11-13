package com.hodanet.common.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * @author lance.lengcs
 * @version 2012-7-18 4:57:26
 * 
 * <pre>
 *    SpringĲ.
 * </pre>
 */
public class SpringContextUtil {

    /** Spring Web . */
    private static final String                    SPRING_WEB_APPLICATION_CONTEXT = FrameworkServlet.class.getName()
                                                                                    + ".CONTEXT.dispatcherServlet";
    /** ǰRequest. */
    private static ThreadLocal<HttpServletRequest> localRequest                   = new ThreadLocal<HttpServletRequest>();
    /** ServletContext. */
    private static ServletContext                  servletContext                 = null;
    /** Spring Web. */
    private static WebApplicationContext           applicationContext             = null;

    /** . */
    private SpringContextUtil(){
    }

    /**
     * Request.
     * 
     * @param request Request
     */
    public static void setRequest(HttpServletRequest request) {
        localRequest.set(request);
    }

    /**
     * ͻȡSpring Bean.
     * 
     * @param <T> Ŀ
     * @param requiredType Ŀ
     * @return Ŀ
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            applicationContext = getWebApplicationContext();
        }
        if (applicationContext != null) {
            return applicationContext.getBean(requiredType);
        }
        return null;
    }

    /**
     * ͻȡSpring Bean.
     * 
     * @param <T> Ŀ
     * @param requiredType Ŀ
     * @return Ŀ
     */
    public static Object getBean(String name) {
        if (applicationContext == null) {
            applicationContext = getWebApplicationContext();
        }
        if (applicationContext != null) {
            return applicationContext.getBean(name);
        }
        return null;
    }

    /**
     * ȡServletContext.
     * 
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * servletContext.
     * 
     * @param servletContext .
     */
    public static void setServletContext(ServletContext servletContext) {
        SpringContextUtil.servletContext = servletContext;
    }

    /**
     * ȡSpring.
     * 
     * @return .
     */
    private static WebApplicationContext getWebApplicationContext() {
        WebApplicationContext wac = null;
        if (servletContext != null) {
            wac = (WebApplicationContext) servletContext.getAttribute(SPRING_WEB_APPLICATION_CONTEXT);
        }

        return wac;
    }

    /**
     * ȡRequest.
     * 
     * @param param 
     * @return ֵ
     */
    public static String getRequestParameter(String param) {
        if (localRequest.get() == null) {
            throw new IllegalArgumentException("δRequest.");
        }
        return localRequest.get().getParameter(param);
    }

    /**
     * ȡRequest.
     * 
     * @param param 
     * @return ֵ
     */
    public static String[] getRequestParameters(String param) {
        if (localRequest.get() == null) {
            throw new IllegalArgumentException("δRequest.");
        }
        return localRequest.get().getParameterValues(param);
    }

    /**
     * ȡSessionв.
     * 
     * @param key .
     * @return .
     */
    public static Object getSessionAttr(String key) {
        if (localRequest.get() == null) {
            throw new IllegalArgumentException("δRequest.");
        }
        return localRequest.get().getSession().getAttribute(key);
    }
}
