package com.zz91.ads.count.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zz91.ads.count.service.AdsService;
import com.zz91.ads.count.thread.ControlThread;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.RandomUtils;

/**
 * 
 * @author root
 * 
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1315545405117443146L;

	public void init() throws ServletException {
		
		MemcachedUtils.getInstance().init();
		
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zzads-count.properties");
		
		ControlThread controlThread = new ControlThread();
		controlThread.start();
		
		AdsService.CACHE_VERSION = RandomUtils.random(2);
	}

	public void destroy() {
		ControlThread.shutdown();
		MemcachedUtils.getInstance().getClient().shutdown();
	}
	
}
