package com.sizmoj.mini.common;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

@Service
public class ServletContextPathResolver implements ServletContextAware {

	private ServletContext servletContext;
	public String getPath(String uri) {
		setServletContext( servletContext);
		if (uri == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(servletContext.getRealPath(""));
		if (!uri.startsWith("/")) {
			sb.append(File.separator);
		}
		sb.append(uri.replace('/', File.separatorChar));
		return sb.toString();
		}
		
		
		public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		}
}
