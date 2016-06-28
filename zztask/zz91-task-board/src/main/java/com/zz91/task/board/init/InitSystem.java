package com.zz91.task.board.init;

import org.apache.log4j.Logger;

import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.search.solr.SolrUpdateUtil;
import com.zz91.util.sms.SmsUtil;

/**
 * 系统启动时加载数据库中的任务信息
 * 
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-16
 */
public class InitSystem {

	final static Logger LOG = Logger.getLogger(InitSystem.class);
	final static String WEB_PROP="web.properties";
	
	public void init() {
	    MailUtil.getInstance().init(WEB_PROP);
	    SmsUtil.getInstance().init(WEB_PROP);
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
//		SolrUtil.getInstance().init("file:/usr/tools/config/search/search.properties");
		SolrUpdateUtil.getInstance().init("file:/mnt/tools/config/search/search.properties");
	}

}
