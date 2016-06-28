/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.ast.feiliao91.admin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.service.commom.CategoryService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.facade.GCategoryFacade;
import com.ast.feiliao91.service.goods.GoodsCategoryService;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Component("initSystem")
public class InitSystem {
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private CategoryService categoryService;
	
	private static Logger LOG = Logger.getLogger(InitSystem.class);

	final static String WEB_PROP="web.properties";
	
	@PostConstruct
	public void initSystem() {
		
		MemcachedUtils.getInstance().init(WEB_PROP);
		//初始化类别库
		CategoryFacade.getInstance().init(categoryService.queryAllCategory(), null);
		//初始化产品类别库
		GCategoryFacade.getInstance().init(goodsCategoryService.queryAllGoodsCategory(), null);
		//初始化系统配置
		initBaseConfig();

	//	SearchEngineUtils.getInstance().init(WEB_PROP);
	//	MailUtil.getInstance().init(WEB_PROP);
	//	SmsUtil.getInstance().init(WEB_PROP);
	}

	@PreDestroy
	public void destroySystem() {
		MemcachedUtils.getInstance().getClient().shutdown();
	}

	public void initBaseConfig() {
		LOG.info(">>>>>>>>Init system properties cache start...");
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("web.properties");
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
				MemcachedUtils.getInstance().getClient().set(String.valueOf(key), 0,
						String.valueOf(p.get(key)));
			}
		}
		LOG.info(">>>>>>>>Init system properties cache end...");
	}

//	public void initParamConfig() {
//		LOG.info(">>>>>>>>Init system param cache start...");
//		List<ParamType> typeList = paramService.listAllParamTypes();
//
//		Map<String, Map<String, Param>> paramCache = new LinkedHashMap<String, Map<String, Param>>();
//		for (ParamType type : typeList) {
//			List<Param> paramList = paramService
//					.listParamByTypes(type.getKey());
//			if (paramList != null) {
//				Map<String, Param> map = new LinkedHashMap<String, Param>();
//				for (Param p : paramList) {
//					map.put(p.getKey(), p);
//				}
//				paramCache.put(type.getKey(), map);
//			}
//		}
//
//		MemcachedUtils.getInstance().getClient().set(ParamFacade.CACHE_KEY,
//				ParamFacade.EXPIRATION, paramCache);
//		LOG.info(">>>>>>>>Init system param cache end...");
//	}

}
