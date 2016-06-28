/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-12-2
 */
package com.zz91.ads.count.thread;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zz91.ads.count.domain.AdLog;

/**
 * @author root
 * 
 *         created on 2010-12-2
 */
public class AdLogThread implements Runnable {

	Logger LOG=Logger.getLogger("com.zz91.ads.count.adslog");
	
//	private String taskName = "";
	private AdLog adlog;

	public AdLogThread() {

	}

	public AdLogThread(AdLog log) {
		this.adlog = log;
	}
	
	@Override
	public void run() {
		if(adlog!=null){
			LOG.info(JSONObject.fromObject(adlog).toString());
		}
	}

}
