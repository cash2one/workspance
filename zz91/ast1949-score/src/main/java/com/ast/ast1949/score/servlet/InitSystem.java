/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.ast.ast1949.score.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.site.MemberRuleService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */

public class InitSystem {

	private static Logger LOG = Logger.getLogger(InitSystem.class);
	@Autowired
	private ParamService paramService;
	
	@Resource
	private MemberRuleService memberRuleService;
	
	public void initSystem() {
		
		MemcachedUtils.getInstance().init("web.properties");
		//初始化系统配置
		initBaseConfig();

		//初始化系统配置
//		initParamConfig();
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		
		//初始化规则
		MemberRuleFacade.getInstance().init(memberRuleService.queryMemberRuleList(), null);
	}

	
	public void destroySystem() {
		MemcachedUtils.getInstance().getClient().shutdown();
	}

	public void initBaseConfig() {
		LOG.info(">>>>>>>>Init system properties cache start...");
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("web.properties");
		Properties p = new Properties();

		try {
			p.load(inputStream);
		} catch (IOException e1) {
			LOG.error("error read web.properties", e1);
		}

		String tmp = null;
		for (Object key : p.keySet()) {
			tmp = String.valueOf(key);
			if (tmp != null && tmp.startsWith("baseConfig.")) {
				MemcachedUtils.getInstance().getClient().set(String.valueOf(key), 0, String.valueOf(p.get(key)));
			}
		}

		LOG.info(">>>>>>>>Init system properties cache end...");
	}

}
