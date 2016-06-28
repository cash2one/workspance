/**
 * Project name: zz91-mail
 * File name: InitSystem.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.config;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Service;

import com.zz91.mail.service.AccountService;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
@Service
public class InitSystem extends HttpServlet {
	private static final long serialVersionUID = 1315545405117443146L;
	
	@Resource
	private AccountService accountService;

	public void startup() throws ServletException {
		//缓存模板
		//缓存账号
		accountService.initCache(null);
	}

	public void destroy() {

	}
}
