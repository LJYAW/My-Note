package com.sino.base.common.interceptor;

import com.sino.base.common.Global;
import com.sino.base.common.util.SystemUtils;
import com.sino.base.system.entity.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * session拦截器，判断用户的session是否超时
 *
 * @author .
 * @date 2017年6月14日 下午2:58:59
 * @since Sino-Bridge From 2001-2017
 */
public class SessionTimeOutInterceptor implements HandlerInterceptor {

	/**
	 * 请求处理前调用
	 *
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		//判断如果为login.do的请求，直接放行
		if (url.contains("login.do") || url.contains("index.do")) {
			return true;
		} else if (url.contains("index.html") || url.contains("toLogin.do")) {
			return true;
		} else if (url.contains("country") || url.contains("register") || url.contains("getCode") || url
				.contains("isUsedName") || url.contains("success") || url.contains("prodType")) {
			return true;
		}
		//判断用户是否存在，即session是否超时，超时结束请求，弹出信息并跳转到登录页面
		SysUser curUser = SystemUtils.getLoginUser();
		if ((curUser == null || "".equals(curUser.toString())) && !url.contains("web")) {
			PrintWriter out = response.getWriter();
			String builder = "<script type=\"text/javascript\" charset=\"UTF-8\">"
					+ "window.top.location.href=\"" + request.getSession().getServletContext().getContextPath()
					+ Global.TIMEOUT_URL + "\""
					+ "</script>";
			out.print(builder);
			out.close();
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}
