/**
 * @author shiqp
 * @date 2016-01-09
 *
 */
package com.ast.feiliao91.www.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ast.feiliao91.service.commom.CategoryService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.sms.SmsUtil;
public class InitSystem {

	private static Logger LOG = Logger.getLogger(InitSystem.class);
	final static String WEB_PROP="web.properties";
	
	@Resource
	private CategoryService categoryService;
	
	
	public void initSystem() {
		
		MemcachedUtils.getInstance().init(WEB_PROP);
		//初始化系统配置
		initBaseConfig();
		//短信初始化
		SmsUtil.getInstance().init(WEB_PROP);
		//初始化SEO
		SeoUtil.getInstance().init();
		// 邮件工具初始化
		MailUtil.getInstance().init(WEB_PROP);
		// 类别初始化
		CategoryFacade.getInstance().init(categoryService.queryAllCategory(), null);
		// 搜索引擎
		SearchEngineUtils.getInstance().init(WEB_PROP);
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
