package com.ast1949.shebei.trade.servlet;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.seo.SeoUtil;

/**
 * @author qizj (qizhenj@gmail.com)
 *
 */

public class InitSystem {

//	@Resource
//	private CategoryService categoryService;
	
	public void initSystem() {
		
		MemcachedUtils.getInstance().init("web.properties");
	
//		//初始化系统类别库
//		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
		
		//初始化Seo
		SeoUtil.getInstance().init();
		
	}

	
	public void destroySystem() {
		MemcachedUtils.getInstance().getClient().shutdown();
	}
	
}
