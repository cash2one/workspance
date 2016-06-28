/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-17 by liulei
 */
package com.ast.ast1949.persist.bbs;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.bbs.BbsPostDTO;
import com.ast.ast1949.dto.information.WeeklyDTO;

/**
 * @author liulei
 *
 */
public interface BbsDAO {
	
	/**
	 * 添加主贴回复信息
	 * @param bbsPostReplyDO为主贴回复信息，不能为空，否则抛出异常
	 * @return 添加成功，返回添加记录主键值,否则为0
	 */
	public Integer insertBbsPostReply(BbsPostReplyDO bbsPostReplyDO);
	
	/**
	 * 根据id查询模块详细信息
	 * @param id为模块信息主键，不能为空，否则抛出异常
	 * @return BbsPostCategoryDO主贴信息，没找到返回为空
	 */
	public BbsPostCategoryDO queryBbsPostCategoryById(Integer id);
	
	/**
	 * 添加个人基本信息
	 * @param bbsUserProfilerDO为个人基本信息，不能为空，否则抛出异常
	 * @return 添加成功，返回添加记录主键值,否则为0
	 */
	public Integer insertBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO);
	
	/**
	 * 修改个人头像
	 * @param account为公司账号名,不能为空，否则抛出异常
	 * @param picturePath为头像路径，可为空
	 * @return 修改成功，返回1，否则0	
	 */
	public Integer updateBbsUserPicturePath(String account,String picturePath);
	
	/**
	 * 查询企业签名信息总个数
	 * @param account为公司账号名，不能为null，否则抛出异常
	 * @return 没查询到返回为0
	 */
	public Integer queryBbsSignByAccountCount(String account);
	
	/**
	 * 查询企业签名BbsSignDO信息
	 * @param account为公司账号名，不能为null，否则抛出异常
	 * @param startIndex为查询起始位置，可为空
	 * @param sort为排序字段，可为空
	 * @param dir为排序顺序，可为空
	 * @param size为排序大小，可为空
	 * @return 主贴BbsSignDO信息集，没查询到返回为null
	 */
	public List<BbsSignDO> queryBbsSignByAccount(String account,
			Integer startIndex,Integer size,String sort,String dir);
	
	/**
	 * 添加企业标签信息
	 * @param bbsSignDO为企业标签信息，不能为空，否则抛出异常
	 * @return 添加成功，返回添加记录主键值,否则为0
	 */
	public Integer insertBbsSign(BbsSignDO bbsSignDO);
	
	/**
	 * 修改部分个人基本信息
	 * @param bbsUserProfilerDO为个人基本信息，不能为空，否则抛出异常
	 * @return 修改成功，返回1,否则为0
	 */
	public Integer updateSomeBbsUserProfiler(BbsUserProfilerDO bbsUserProfilerDO);
	
	/**
	 * 查询最经回复我的帖子的昵称
	 * @param account为公司账号名，不能为null，否则抛出异常
	 * @param startIndex为查询起始位置，可为空
	 * @param sort为排序字段，可为空
	 * @param dir为排序顺序，可为空
	 * @param size为排序大小，可为空
	 * @return BbsPostDTO信息集，没查询到返回为null
	 */
	public List<BbsPostDTO> queryUserNicknameByReply(String account,Integer startIndex,
			Integer size,String sort,String dir);

	/**
	 * 添加回复次数，每次回复，回复次数添加1
	 * @param postId为主贴ID
	 * @return 修改成功，返回1,否则为0
	 */
	public Integer updateBbsPostReplyCount(Integer id);
	
	
	public Integer updateBbsPostReplyCountForDel(Integer id);
	
	public Integer updateReplyCount (Integer id , Integer replyCount);
	/**
	 * 添加浏览次数，每次点击添加1
	 * @param id为主贴主键
	 * @return 修改成功，返回1,否则为0
	 */
	public Integer updateBbsPostVisitedCount(Integer id);
	
	/**
	 * 根据帖子ID更新回复时间
	 * @param id为主贴主键，不能为null
	 * @return 成功返回大于0，否则等于0
	 */
	public Integer updateBbsPostReplyTime(Integer id);
	
	/**
	 * 回帖时，个人基本信息回贴数+1
	 * @param account为账号名，不能为null
	 * @return 成功返回大于0，否则等于0
	 */
	public Integer updateBbsUserProfilerReplyNumber(String account);
	
	/**
      *   分页列表显示bbsPost发帖信息
      * @param weeklyDTO 不能为空
      * @return  List<BbsPostDO>
      */
	public List<BbsPostDO> listBbsPostByPage(WeeklyDTO weeklyDTO);
	
	/**
	 *  返回所有发帖纪录总数
	 *   @param weeklyDTO 不能为空
	 * @return bbsPost结果集
	 */
	public Integer countBbsPost(WeeklyDTO weeklyDTO);

	/**
	 *   根据UserProfilerId 
	 *   查詢bbs用戶信息 companyId,account,nickname
	 * @param id 不能为空
	 * @return BbsUserProfilerDO 
	 *         companyId,account,nickname
	 */
	public BbsUserProfilerDO queryBbsUserProfilerById(Integer id);
	
	/**
	 * 查询本周最新资讯信息
	 * @param firstDate 本周第一天
	 * @param lastDate	本周最后一天
	 * @param size  条数
	 * @return	List<BbsPostDTO>
	 */
	public List<BbsPostDTO> queryNewBbsOnWeek(Date firstDate,Date lastDate,Integer size);
	
	public String queryUserProfilerPictureByCompanyId(Integer companyId);
	
	public Integer updateBbsUserProfilerReplyNumberForDel(String account);

	public List<BbsPostCategoryDO> queryAllBbsPostCategory();

}
