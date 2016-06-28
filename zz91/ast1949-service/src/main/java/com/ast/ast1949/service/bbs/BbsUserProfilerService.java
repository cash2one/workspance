/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
public interface BbsUserProfilerService {

	public Integer queryIntegralByAccount(String account);
	
	public boolean isProfilerExist(String account);
	
	public Integer createEmptyProfilerByUser(String account);
	
	public List<BbsUserProfilerDO> queryTopByPostNum(Integer size);
	
	public BbsUserProfilerDO queryProfilerOfAccount(String account);
	
	 // 修改发帖数量
	public Integer updatePostNumber(String account);
	
	public Integer updateReply(Integer replsyCount,String account);
	
	public Integer updateBbsReplyCount(String account);
}
