/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-15 下午03:07:44
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

/**
 * 将URL地址全部转化为小写
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ToLowerCaseFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getServletPath().toLowerCase();
		req.getRequestDispatcher(path).forward(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
