/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.ast.ast1949.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.download.DownloadInfoDao;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.service.site.MemberRuleService;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
import com.ast.ast1949.web.thread.PdfToSwfThread;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.mongo.MongoDBUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.sms.SmsUtil;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Mays (x03570227@gmail.com)
 * 
 */
@Component("initSystem")
public class InitSystem {

	private static Logger LOG = Logger.getLogger(InitSystem.class);
	@Autowired
	private ParamService paramService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryProductsService categoryProductsService;
	@Resource
	private DownloadInfoDao downloadInfoDao;
	@Resource
	private MemberRuleService memberRuleService;
	@Resource
	private CategoryYuanliaoService categoryYuanliaoService;

	final static String WEB_PROP="web.properties";
	
	@PostConstruct
	public void initSystem() {
		
		MemcachedUtils.getInstance().init(WEB_PROP);
		
		//初始化系统配置
		initBaseConfig();

		//初始化系统配置
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		
		//初始化系统类别库
		CategoryFacade.getInstance().init(categoryService.queryCategoryList(),null);
		
		//初始化原料类别库
		YuanliaoFacade.getInstance().init(categoryYuanliaoService.queryAllCategoryYuanliao(),null);
		
		//初始化类别code
		CategoryProductsFacade.getInstance().init(categoryProductsService.queryAllCategoryProducts(), null);
		
		//初始化规则
		MemberRuleFacade.getInstance().init(memberRuleService.queryMemberRuleList(), null);
		
		SearchEngineUtils.getInstance().init(WEB_PROP);
		
		LogUtil.getInstance().init(WEB_PROP);
		
		MailUtil.getInstance().init(WEB_PROP);
		
		TagsUtils.getInstance().init(WEB_PROP);
		
		SmsUtil.getInstance().init(WEB_PROP);
		
		// pdf转化swf 线程启动
		PdfToSwfThread pdfToSwfThread = new PdfToSwfThread(downloadInfoDao);
		pdfToSwfThread.setName("pdfToSwfThread");
		pdfToSwfThread.start();
		
		// 敏感词初始化
		SensitiveUtils.init(WEB_PROP);
		// 门市部死链Xml
//		XmlFileUtils.init(WEB_PROP);
		MongoDBUtils.getInstance().init(WEB_PROP,"");
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
			// 设置绑定号码标志
			if (tmp != null && PhoneService.BIND_FLAG.equals(tmp)) {
				MemcachedUtils.getInstance().getClient().set(PhoneService.BIND_FLAG, 0,String.valueOf(p.get(key)));
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
