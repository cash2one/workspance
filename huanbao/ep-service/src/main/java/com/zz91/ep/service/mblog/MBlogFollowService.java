package com.zz91.ep.service.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogFollowDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;

/**
 * author:fangchao 
 * date: 2013-10-23
 */
public interface MBlogFollowService {
	/**
	 * 函数名称：insert
	 * 功能描述：添加关注账户信息
	 * 输入参数：
	 * 		   @param MBlogFollow  follow 账户信息
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insert(MBlogFollow  follow);
	/**
	 * 函数名称：update
	 * 功能描述：用于取消关注
	 * 输入参数：
	 * 		   @param Interger  id 账户信息
	 * 		   @param String followStatus 关注的状态
	 *         @param type 互相关注的状态
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer update(Integer id, String followStatus,String type);
	/**
	 * 函数名称：updateTypeById
	 * 功能描述：用于来更新互相关注的状态
	 * 输入参数：
	 * 		   @param Integer id 账户信息
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateTypeById(Integer id,String type);
	/**
	 * 函数名称：queryByIdAndTargetId
	 * 功能描述：根据id查询用户的关注信息
	 * 输入参数：
	 * 		   @param Integer infoId 账户信息
	 * 		   @param Integer targetId 目标id
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public MBlogFollow queryByIdAndTargetId(Integer infoId,Integer targetId,String followStatus);
	/**
	 * 函数名称：queryFollowByConditions根据状态不同查询我关注的信息
	 * 功能描述：查询我关注的信息
	 * 输入参数：
	 * 		   @param Integer  infoId 账户信息id
	 *         @param Integer  Integer groupId分组id
	 *         
	 *         @param PageDto<MBlogFollow> page
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
//	public PageDto<MBlogFollow> queryFollowByConditions(Integer  infoId,Integer groupId,PageDto<MBlogFollow> page);
	/**
	 * 函数名称：queryFollowCountByConditions根据状态不同查询我关注的信息的数量
	 * 功能描述：统计查询我关注的信息
	 * 输入参数：
	 * 		   @param Integer  infoId 账户信息id
	 *         @param Integer  Integer groupId分组id
	 *         
	 *         @param PageDto<MBlogFollow> page
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer queryFollowCountByConditions(Integer  infoId,Integer groupId,String type);
	/**
	 * 函数名称：queryFollowByIdAndType查询互相关注的信息
	 * 功能描述：查询我关注的信息
	 * 输入参数：
	 * 		   @param Integer  infoId 账户信息id
	 *         @param Integer  Integer groupId分组id
	 *         
	 *         @param PageDto<MBlogFollow> page
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<MBlogFollow> queryFollowByIdAndType(Integer infoId,Integer type,PageDto<MBlogFollow> page);
	/**
	 * 函数名称：queryFollowCountByIdAndType统计互相关注的信息的数量
	 * 功能描述：查询我关注的信息
	 * 输入参数：
	 * 		   @param Integer  infoId 账户信息id
	 *         @param Integer  Integer groupId分组id
	 *         
	 *         @param PageDto<MBlogFollow> page
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryFollowCountByIdAndType(Integer infoId,Integer type);
//	/**
//	 * 函数名称：queryFansByConditions查询粉丝的信息
//	 * 功能描述：查询我关注的信息
//	 * 输入参数：
//	 * 		   @param Integer  targetId 账户信息id
//	 *         @param PageDto<MBlogFollow> page
//	 * 		   
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
//	 */
////	public PageDto<MBlogFollow> queryFansByConditions(Integer targetId,PageDto<MBlogFollow> page);
	/**
	 * 函数名称：queryFansCountByConditions查询粉丝的数量
	 * 功能描述：查询关注我的用户信息
	 * 输入参数：
	 * 		   @param Integer  targetId 账户信息id
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryFansCountByConditions(Integer targetId,String type);
	
	 /**
	 * 函数名称：updateFollowGoup
	 * 功能描述：为关注的人分组
	 * 输入参数：
	 * 		   @param Integer  infoId 账户信息id
	 *         @param Integer  Integer targetId分组id
	 *         
	 *         @param PageDto<MBlogFollow> page
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer updateFollowGroup(Integer infoId, Integer targetId,Integer groupId);
	 
	 public List<MBlogFollow> queryByInfoIdOrGroupId(Integer infoId, Integer groupId);
	 
	 public List<MBlogFollow> queryFansByTargetId(Integer targetId,String type,Integer start,Integer size);
	 
	 public Integer updateFollowStatus(String type,String followStatus,Integer infoId,Integer targetId,Integer groupId);
	 
	 public List<MBlogFollow> queryFollowByConditions(Integer  infoId,Integer groupId,String type,PageDto<MBlogInfoDto> page);
	 
	 public Integer updateNoteNameById(Integer infoId,Integer targetId,String noteName);
	 
	 public PageDto<MBlogInfoDto> pageQueryForkeywords(Integer infoId, String keywords,PageDto<MBlogInfoDto> page);
	
}
