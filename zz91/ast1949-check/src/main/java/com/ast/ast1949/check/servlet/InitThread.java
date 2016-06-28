/**
 * Project name: zz91-mail
 * File name: InitThread.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.ast.ast1949.check.servlet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ast.ast1949.check.thread.CheckDistributeThread;
import com.ast.ast1949.check.thread.CheckScanThread;
import com.ast.ast1949.check.thread.ControlThread;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
@Component("InitThread")
public class InitThread extends HttpServlet {
	private static final long serialVersionUID = 9188497441746376931L;
	
	final static Logger LOG=Logger.getLogger(InitThread.class);
	
	@Resource
	private ControlThread controlThread;
	@Resource
	private CheckScanThread checkScanThread;
	@Resource
	private CheckDistributeThread checkDistributeThread;
	

	public void startup() throws ServletException {
//		 ControlThread.runSwitch = true;
		
		controlThread.start();
		checkScanThread.start();
		checkDistributeThread.start();
	}

	public void destroy() {

	}
	
}
