/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;


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
	
	/***
	 * 查询是否存在此用户
	 * **/
	public Integer countUserProfilerByAccount(String accountName);
	
	public BbsUserProfilerDO queryUserByAccount(String accountName);
	
	public BbsUserProfilerDO queryUserByCompanyId(Integer companyId);
	/**
	 * 查询最新用户
	 * */
	public List<BbsUserProfilerDO> queryNewUser(Integer size);
	
	/**
	 * 分页展示用户信息
	 */
	public PageDto<BbsUserProfilerDto> pageUserByAdmin(BbsUserProfilerDto bbsUserProfilerDto,PageDto<BbsUserProfilerDto> page);

	/**
	 * 计算积分排名 名次结果要加1
	 * @param i
	 * @return
	 */
	Integer queryRank(Integer i);
}
