/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.zz91.log.thread;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zz91.log.util.MongoUtil;

/**
 * 项目名称：日志统计
 * 模块描述：数据插入Thread
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-08-09　　　黄怀清　　　　　　　1.0.0　　　　　原插入文本改为mongDB
 */
public class LogThread implements Runnable {

	/*
	 * 日志队列,测试版,此同步方式可能不是线程安全的,后续优化
	 */
	public static BlockingQueue<Map<String, Object>> logsQueue = new LinkedBlockingQueue<Map<String,Object>>(10000);
	private static Logger LOG=Logger.getLogger(LogThread.class);
	
	public static boolean RUNING=true;

	
	/**
	 * 此处插入数据,测试版,现为单机跑,后续依数据插入时间和效率看是否需要线程池托管跑多个线程.
	 */
	@Override
	public void run() {
		
		while(RUNING){
			try {
				DBObject dbo = new BasicDBObject();
				//取出日志队列数据存入mongo
				Map<String, Object> data = logsQueue.take();
								
				dbo.putAll(data);
				
				MongoUtil.getInstance().dbc.save(dbo);
				
			} catch (Exception e) {
				LOG.debug(">>"+e.getMessage());
			}
		}
		
	}

}