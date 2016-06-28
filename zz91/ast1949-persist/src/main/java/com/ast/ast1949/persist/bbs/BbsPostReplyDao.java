/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsPostZan;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyAccountSearchDto;
import com.ast.ast1949.dto.company.CompanyDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
public interface BbsPostReplyDao {

	public List<BbsPostReplyDto> queryReplyOfPost(Integer postId,Integer companyId,PageDto<BbsPostReplyDto> page);
	
	public Integer queryReplyOfPostCount(Integer postId,Integer companyId);
	
	/**
	 * 允许的查询条件：
	 * 		check_status=#checkStatus# 
	 * 		bbs_post_id=#bbsPostId# 
	 * 		title like concat(#title#,%%)
	 * 		account = #account#
	 * 返回reply表所有字段信息
	 */
	public List<BbsPostReplyDO> queryReplyByAdmin(BbsPostReplyDO reply, PageDto<BbsPostReplyDO> page);
	
	public Integer queryReplyByAdminCount(BbsPostReplyDO reply);
	
	/**
	 * 更新的字段：
	 * 		check_person=#admin#
	 * 		check_status=#checkStatus#
	 * 		check_time=now()
	 * 		gmt_modified=now()
	 */
	public Integer updateCheckStatus(Integer id, String checkstatus, String admin);
	
	/**
	 * 更新的字段：
	 * 		content=#content#
	 * 		gmt_modified=now()
	 */
	public Integer updateReplyByAdmin(BbsPostReplyDO reply);
	
	public Integer deleteByAdmin(Integer id);
	
	/**
	 *	写入的字段
	 *		title=#title#
	 *		bbs_post_id=#bbsPostId#
	 *		content=#content#
	 *		is_del=#isDel#
	 *		check_status=#checkStatus#
	 *		gmt_created,gmt_modified   now()
	 */
	public Integer createReplyByAdmin(BbsPostReplyDO reply);
	
	public List<BbsPostDO> queryReplyByUser(String account, String checkStatus,Integer bbsPostCategoryId,PageDto<BbsPostDO> page);
	
	public Integer queryReplyByUserCount(String account, String checkStatus,Integer bbsPostCategoryId);
	
	public Integer queryBbsPostByReplyId(Integer id);

	/**
	 * 统计该用户收到多少个回答
	 * @param account
	 * @return
	 */
	public Integer countBeReply(String account,Integer categoryId);

	/**
	 * 检索最新
	 * @param id
	 * @return
	 */
	public BbsPostReplyDO queryById(Integer id);

	/**
	 * 检索最早回复的帖子或回答
	 * @param bbsPostId
	 * @param size
	 * @return
	 */
	public List<BbsPostReplyDO> queryReplyByPostId(Integer bbsPostId, Integer size);
	
	public Integer updateIsDel(Integer id, String isDel);
	
	public List<BbsPostReplyDO> queryReplyByAccount(String account, String checkStatus,Integer bbsPostCategoryId,PageDto<BbsPostReplyDO> page);
	
	public Integer updateReplyByUser(BbsPostReplyDO bbsPostReplyDO);
	/**
	 * 统计发布回帖的公司
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public List<Integer> bbsPostReplyCompany(CompanyAccountSearchDto searchDto,PageDto<CompanyDto> page);
	/**
	 * 统计发布回帖的公司数量
	 * @param searchDto
	 * @return
	 */
	public Integer bbsPostReplyCompanyCount(CompanyAccountSearchDto searchDto);
	
	/**
	 * 统计接收回帖的公司
	 * @param searchDto
	 * @param page
	 * @return
	 */
	public List<Integer> receviceReplyCompany(CompanyAccountSearchDto searchDto,PageDto<CompanyDto> page);
	/**
	 * 统计接收回帖的公司数量
	 * @param searchDto
	 * @return
	 */
	public Integer receviceReplyCompanyCount(CompanyAccountSearchDto searchDto);
	/**
	 * 最佳回答者
	 * @return
	 */
	public List<BbsPostReplyDO> queryBestAnswerByViewCount(Integer size);
	/**
	 * 根据获取的replyId获取回复列表
	 * @param replyId
	 * @return
	 */
	public List<BbsPostReplyDto> queryReplyByReplyId(Integer replyId);
	/**
	 * 某用户回复的问答或帖子
	 * @param account
	 * @param categoryId
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryBbsReplyByUser(String account,Integer categoryId,PageDto<PostDto> page,String keywords);
	/**
	 * 某用户回复的问答或帖子数
	 * @param account
	 * @param categoryId
	 * @return
	 */
	public Integer countBbsReplyByUser(String account,Integer categoryId,String keywords);

	/**
	 * 搜索某帖子最新回复
	 * @param bbsPostId
	 * @param size
	 * @return
	 */
	public BbsPostReplyDO queryLatestReplyByPostId(Integer bbsPostId);
	/**
	 * 回答数，回帖数，科学学院回帖数
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	public Integer countReplyByCompanyId(Integer companyId,Integer categoryId);
	/**
	 * 我的回答
	 * @param categoryId
	 * @param companyId
	 * @param size
	 * @return
	 */
	public List<BbsPostReplyDO> queryReplyByCompanyId(Integer categoryId,Integer companyId,Integer size);
	/**
	 * 我的回答列表页
	 * @param account
	 * @param categoryId
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryReplyByUser(String account,Integer categoryId,PageDto<BbsPostDO> page);
	/**
	 * 该用户有无对该回答点赞
	 * @param companyId
	 * @param replyId
	 * @return
	 */
	public Integer countCompanyByZan(Integer companyId,Integer replyId);
	/**
	 * 点赞与公司关系插入
	 * @param bbsPostZan
	 * @return
	 */
	public Integer insertZan(BbsPostZan bbsPostZan);
	/**
	 * 点赞数的更新
	 * @param id
	 * @param zanCount
	 */
	public void updateZanCount(Integer id,Integer zanCount);
}
