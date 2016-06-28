/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;

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
	
	/**
	 * 判断是否存在此用户
	 * */
	public Integer countUserProfilerByAccount(String accountName);
	
	public BbsUserProfilerDO queryUserByAccount(String accountName);
	
	public BbsUserProfilerDO queryUserByCompanyId(Integer companyId);
	/**
	 * 查询最新用户
	 * */
	public List<BbsUserProfilerDO> queryNewUser(Integer size);
	
	public List<BbsUserProfilerDO> queryByAdmin(BbsUserProfilerDto searchDto,PageDto<BbsUserProfilerDto> page);
	
	public Integer queryCountByAdmin(BbsUserProfilerDto searchDto);

	/**
	 * 计算该积分排名
	 * @param integral
	 * @return
	 */
	public Integer queryRank(Integer integral);
	
}
