/**
 * Project name: zz91-mail
 * File name: InitSystem.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.sms.config;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zz91.sms.service.GatewayService;
import com.zz91.sms.service.SmsLogService;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
@Service
public class InitSystem {
	
	@Resource
	private GatewayService gatewayService;
	@Resource
	private SmsLogService smsLogService;
	
	final static Logger LOG=Logger.getLogger(InitSystem.class);
	
	public void startup() throws ServletException {
//		//初始化 memcached  TODO 不使用缓存，把网关放在内存里
//		MemcachedUtils.getInstance().init("web.properties");
		
		//初始化发送网管帐号
		gatewayService.initGateway();
		
		if(! smsLogService.shutdownRecovery(SmsLogService.SEND_PROCESS, SmsLogService.SEND_READY)){
			LOG.error("sms was not recovery success");
			throw new ServletException("sms was not recovery success.");
		}
		
	}

	public void destroy() {
//		MemcachedUtils.getInstance().shutdownClient();
	}
	
}
