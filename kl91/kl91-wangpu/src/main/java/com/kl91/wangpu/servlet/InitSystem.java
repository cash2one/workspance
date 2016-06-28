/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.kl91.wangpu.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.search.SorlUtil;
import com.zz91.util.seo.SeoUtil;


public class InitSystem {
	
	private static Logger LOG = Logger.getLogger(InitSystem.class);
	
	final static String WEB_PROP="web.properties";
	
	public void initSystem() {
		
		MemcachedUtils.getInstance().init(WEB_PROP);
		//初始化系统配置
		initBaseConfig();
		
		// 搜索引擎初始化
		SorlUtil.getInstance().init(WEB_PROP);

		//初始化系统配置
//		initParamConfig();
//		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
//		
//		//初始化系统类别库
//		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
//		
//		//初始化类别code
//		CategoryProductsFacade.getInstance().init(productsCategoryService.queryAllCategoryProducts(), null);
//		
//		//初始化规则
//		MemberRuleFacade.getInstance().init(memberRuleService.queryMemberRuleList(), null);
		
		//初始化SEO
		SeoUtil.getInstance().init();
		
//		LogUtil.getInstance().init(WEB_PROP);
//		
//		TagsUtils.getInstance().init(WEB_PROP);
//		
		MailUtil.getInstance().init(WEB_PROP);
//		
//		SmsUtil.getInstance().init(WEB_PROP);
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
