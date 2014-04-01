package com.sizmoj.mini.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Repository
public class SystemFilter extends HandlerInterceptorAdapter{
	

	@SuppressWarnings({ "rawtypes", "unchecked" })  
    @Override 
	public boolean preHandle(HttpServletRequest request,  
	            HttpServletResponse response, Object handler) throws Exception {
		 request.setCharacterEncoding("UTF-8");  
	     response.setCharacterEncoding("UTF-8");  
	     response.setContentType("text/html;charset=UTF-8"); 
	     String str = (String) request.getSession().getAttribute("isLogin");
	     String uri = request.getRequestURI();
	     if(StringUtils.isBlank(str)) {
	    	 response.sendRedirect(request.getContextPath() + "/back/login");
	     }
		 return false;
	}
}
