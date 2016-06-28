package com.zz91.log.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zz91.log.util.MongoUtil;

/**
 * 
 * @author Leon
 * 
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1315545405117443146L;

	public void init() throws ServletException {
		MongoUtil.getInstance().init("file:/usr/tools/config/db/db-zzlog-mongo.properties");
//		Thread logRecord = new Thread(new LogThread());
//		logRecord.start();
	}

	public void destroy() {
//		LogThread.RUNING=false;
		MongoUtil.getInstance().destroy();
	}
}
