/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-8-16
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.bbs.BbsViewLogDao;
import com.ast.ast1949.service.bbs.BbsViewLogService;
import com.zz91.util.datetime.DateUtil;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-8-16
 */
@Component("bbsViewLogService")
public class BbsViewLogServiceImpl implements BbsViewLogService {

	@Resource
	private BbsViewLogDao bbsViewLogDao;
	
	@Override
	public void viewLog(Integer postId) {
		long targetDate=DateUtil.getTheDayZero(new Date(), 0);
		Integer i=bbsViewLogDao.countViewLog(postId, targetDate);
		if(i!=null && i.intValue()>0){
			bbsViewLogDao.updateViewLogNum(postId, targetDate);
		}else{
			bbsViewLogDao.insertViewNum(postId, targetDate);
		}
		
	}
}
