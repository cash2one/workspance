/**
 * 
 */
package com.zz91.ep.admin.init;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.search.solr.SolrQueryUtil;

/**
 * @author root
 *
 */
public class InitSystem {
	private static Logger LOG = Logger.getLogger(InitSystem.class);
	@Autowired
	private ParamService paramService;
	public void startup(){
		MemcachedUtils.getInstance().init("web.properties");
		MailUtil.getInstance().init("web.properties");
		//缓存参数列表
		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		//搜索引擎
//		SolrQueryUtil.getInstance().init("solr.slave.properties");
		//SolrUpdateUtil.getInstance().init("solr.master.properties");
		
		LogUtil.getInstance().init("web.properties");
	}
	
	public void initParamConfig() {
		LOG.info(">>>>>>>>Init system param cache start...");

		ParamUtils.getInstance().init(paramService.queryUsefulParam(), "memcached");
		LOG.info(">>>>>>>>Init system param cache end...");
	}
	public void shutdown(){
		
	}
}
