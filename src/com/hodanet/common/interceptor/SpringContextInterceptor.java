package com.hodanet.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hodanet.common.spring.SpringContextUtil;
import com.hodanet.system.util.CommonPropUtil;

/**
 * @author lance.lengcs
 * @version 2012-7-18 4:48:45
 * 
 *          <pre>
 *    SpringContext(ʼSpringContextUtil)..
 * </pre>
 */
public class SpringContextInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// ͨóùҳʾ(ajaxJsonMessageʱModelAndViewΪnull)
		if (arg3 != null) {
			arg3.addObject("commonMapper", CommonPropUtil.getCommonMapper());
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {

		// ȡSpringģȻ󱣴浽̱߳
		SpringContextUtil.setRequest(request);

		return true;
	}

}
