/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-12 by Ryan.
 */
package com.ast.ast1949.esite.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 这个过滤器用于当用户没有指明具体页面时，转向到目录下默认的页面（index.htm）
 *
 * @author Ryan
 *
 */
public class ToHomePageFilter implements Filter {

	private static Logger LOG = LoggerFactory.getLogger(ToHomePageFilter.class);
	private String homePageUri = "";

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		// 1, 判断当前用户访问的地址是否是根地址(没有指明要访问的网页)
		String servletPath = request.getServletPath();
		if("/upload".equals(servletPath)){
			chain.doFilter(req, res);
			return ;
		}
		if (!servletPath.contains(".")) {
			LOG.debug("the user accessed:" + servletPath
					+ ",now forward request to the real page:" + homePageUri);
			if (!servletPath.endsWith("/")&&!homePageUri.startsWith("/")) {
				homePageUri = "/" + homePageUri;
			}
			if(servletPath.endsWith("/")&&homePageUri.startsWith("/")){
				servletPath=servletPath.substring(0,servletPath.length()-1);
			}
			request.getRequestDispatcher(servletPath + homePageUri).forward(req, res);
		}
		// 2 如果用户不是访问根路径,不做处理
		else {
			chain.doFilter(req, res);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig cfg) throws ServletException {
		homePageUri = cfg.getInitParameter("uri");
	}

}
