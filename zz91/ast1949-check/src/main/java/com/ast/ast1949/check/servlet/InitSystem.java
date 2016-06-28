/**
 * Project name: zz91-mail
 * File name: InitSystem.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.ast.ast1949.check.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Service;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2013-12-25
 */
@Service
public class InitSystem extends HttpServlet {
	private static final long serialVersionUID = 1315545405117443146L;
	

	public void startup() throws ServletException {
		//缓存模板
		//缓存账号
	}

	public void destroy() {

	}
}
