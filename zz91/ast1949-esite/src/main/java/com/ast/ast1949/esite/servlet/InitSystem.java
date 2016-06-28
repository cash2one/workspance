/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.ast.ast1949.esite.servlet;

import javax.annotation.Resource;

import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.company.EsiteService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.site.MemberRuleService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.mongo.MongoDBUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */

public class InitSystem {
	
	final static String WEB_PROP="web.properties";

	@Resource
	private ParamService paramService;
	@Resource
	private MemberRuleService memberRuleService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private EsiteService esiteService;
	
	public void initSystem() {
		
		MemcachedUtils.getInstance().init("web.properties");

		//初始化系统配置
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
	
		//初始化规则
		MemberRuleFacade.getInstance().init(memberRuleService.queryMemberRuleList(), null);
		
		//初始化系统类别库
		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
		
		//初始化Seo
		SeoUtil.getInstance().init();
		
		SearchEngineUtils.getInstance().init(WEB_PROP);
		
		// 初始化所有域名
		esiteService.initDomain();
		
		// 门市部死链Xml
//		XmlFileUtils.init(WEB_PROP);
		
		MongoDBUtils.getInstance().init(WEB_PROP,"");
	}

	
	public void destroySystem() {
		MemcachedUtils.getInstance().getClient().shutdown();
	}
	
}
