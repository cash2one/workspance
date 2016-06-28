/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
public interface BbsUserProfilerDao {

	public Integer queryIntegralByAccount(String account);
	
	public Integer countProfilerByAccount(String account);
	
	public Integer insertProfiler(BbsUserProfilerDO profiler);
	
	public List<BbsUserProfilerDO> queryTopByPostNum(Integer size);
	
	public Integer updatePostNumber(String account,Integer postNumber);
	
	public BbsUserProfilerDO queryProfilerOfAccount(String account);
	
	public Integer updateReplyCount(Integer replyCount,String account);
}
