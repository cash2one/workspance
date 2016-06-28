/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.ast.ast1949.bbs.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.site.MemberRuleService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class InitSystem {

	@Autowired
	private ParamService paramService;
	@Resource
	private MemberRuleService memberRuleService;
	@Resource
	private BbsService bbsService;

	public void initSystem() {
		
		MemcachedUtils.getInstance().init("web.properties");
		
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		
		MemberRuleFacade.getInstance().init(memberRuleService.queryMemberRuleList(), null);
		
		SearchEngineUtils.getInstance().init("web.properties");
		
		SeoUtil.getInstance().init();
		
		TagsUtils.getInstance().init("web.properties");
		
		// 初始化所有bbs类别
		bbsService.initBbsPostCategory();
		
		//初始化系统类别库
//		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
	}

	@PreDestroy
	public void destroySystem() {
	}

	public static void main(String[] args) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		for(int i=0;i<50000;i++){
			map.put("account@zz91.com"+i, i);
		}
		System.out.println("map inited...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(map.size());
	}
}
