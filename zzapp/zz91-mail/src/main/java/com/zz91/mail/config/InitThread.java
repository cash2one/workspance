/**
 * Project name: zz91-mail
 * File name: InitThread.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.config;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zz91.mail.service.MailInfoService;
import com.zz91.mail.thread.ControlThread;
import com.zz91.mail.thread.MailDistributeThread;
import com.zz91.mail.thread.MailScanThread;

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
	private MailScanThread mailScanThread;
	@Resource
	private MailDistributeThread mailDistributeThread;
	@Resource
	private MailInfoService mailInfoService;

	public void startup() throws ServletException {
//		 ControlThread.runSwitch = true;
		
//		if(!mailInfoService.shutdownRecovery(MailInfoService.SEND_SENDING, MailInfoService.SEND_WAITING)){
//			LOG.error("Email was not recovery success");
//			throw new ServletException("Email was not recovery success.");
//		}
//		controlThread.start();
//		mailScanThread.start();
//		mailDistributeThread.start();
	}

	public void destroy() {
		ControlThread.shutdown();
		MailScanThread.runSwitch=false;
		MailDistributeThread.runSwitch=false;
	}
	
}
