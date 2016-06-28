/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-7
 */
package com.zz91.ads.board.servlet;

import javax.annotation.PreDestroy;

import com.zz91.util.db.pool.DBPoolFactory;

/**
 * @author kongsj
 *
 */
public class InitSystem {

	public void initSystem() {
		 
		// 初始化 jdbc
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		
	}

	@PreDestroy
	public void destroySystem() {
	}

}
