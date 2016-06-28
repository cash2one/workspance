/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-13 下午05:26:03
 */
package com.ast.ast1949.bbs.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 这个过滤器用于将根目录下的文件直接转向到root目录下.
 *
 * @author Ryan
 *
 */

public class ToRootPageFilter implements Filter {

	private static Logger LOG = Logger.getLogger(ToRootPageFilter.class);
	private String rootName = "";

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
		// 1, 判断当前用户访问的地址是否是根目录下的地址
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String pageName = request.getServletPath();

		if (pageName.lastIndexOf('/') == 0) {
			String realPage = contextPath + "/" + rootName + pageName;
			LOG.debug("the user accessed:" + requestURI + ",now forward request to the real page:"
					+ realPage);
			request.getRequestDispatcher(rootName + pageName).forward(req, res);
		}
		// 2 如果用户不是访问根目录下的地址,不做处理
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
		rootName = cfg.getInitParameter("rootName");
	}

}
