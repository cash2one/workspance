/**
 * @author shiqp
 * @date 2016-01-15
 *
 */
package com.ast.feiliao91.trade.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.service.commom.CategoryService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.facade.GCategoryFacade;
import com.ast.feiliao91.service.goods.GoodsCategoryService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.sms.SmsUtil;
public class InitSystem {

	private static Logger LOG = Logger.getLogger(InitSystem.class);
	final static String WEB_PROP="web.properties";
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private CategoryService categoryService;
	
	public void initSystem() {
		//初始化缓存配置
		MemcachedUtils.getInstance().init(WEB_PROP);
		//初始化系统配置
		initBaseConfig();
		//初始化SEO
		SeoUtil.getInstance().init();
		//初始化产品类别库
		GCategoryFacade.getInstance().init(goodsCategoryService.queryAllGoodsCategory(), null);
		//初始化类别库
		CategoryFacade.getInstance().init(categoryService.queryAllCategory(), null);
		
		MailUtil.getInstance().init(WEB_PROP);
		
		SmsUtil.getInstance().init(WEB_PROP);
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
