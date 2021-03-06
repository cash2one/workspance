/*
 * 文件名称：InitSystem.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.company;

import org.apache.log4j.Logger;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.solr.SolrQueryUtil;
import com.zz91.util.seo.SeoUtil;

/**
 * 项目名称：中国环保网
 * 模块编号：业务控制层
 * 模块描述：系统启动初始化。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-23　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class InitSystem {
	
	private static Logger LOG = Logger.getLogger(InitSystem.class);

	/**
	 * 函数名称：startup
	 * 功能描述：Spring加载InitSystem时，默认调用方法（可在spring-company-config.xml中配置）
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public void startup() {
		LOG.info(">>>>>>>>Init system start...");
		//缓存工具初始化
		MemcachedUtils.getInstance().init("web.properties");
		//SEO初始化
		SeoUtil.getInstance().init();
		//搜索引擎
		//SorlUtil.getInstance().init("web.properties");
		
		SolrQueryUtil.getInstance().init("file:/mnt/tools/config/search/search.solr4.slave.properties");
		
		// coreseek 搜索引擎
		SearchEngineUtils.getInstance().init("web.properties");
		
		LOG.info(">>>>>>>>Init system end...");
	}
	
	/**
	 * 函数名称：destroy
	 * 功能描述：Spring销毁InitSystem时，默认调用方法（可在spring-company-config.xml中配置）
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public void destroy() {
		LOG.info(">>>>>>>>destroy system...");
	}
}
