package com.hodanet.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hodanet.common.util.StringUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.util.CommonPropUtil;

/**
 * @author lance.lengcs
 * @version 2012-8-1 1:11:42
 * 
 *          <pre>
 * ¼ȨFilter
 *          </pre>
 */
public class HodanetAuthenticationFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(true);
		String url = request.getRequestURI();

		if (session == null || session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER) == null) {
			if (StringUtil.isNotBlank(url) && (url.indexOf("home") < 0 && url.indexOf("login") < 0
					&& url.indexOf("Register") < 0 && url.indexOf("register") < 0 && url.indexOf("api") < 0)) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
		}

		arg2.doFilter(arg0, arg1);
		return;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// SpringContextUtil.setServletContext(config.getServletContext());

	}

}
