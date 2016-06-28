/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostReplyDto;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
public interface BbsPostReplyService {

	public PageDto<BbsPostReplyDto> pageReplyOfPost(Integer postId,Integer companyId, String iconTemplate, PageDto<BbsPostReplyDto> page);
	
	public PageDto<BbsPostReplyDO> pageReplyByAdmin(BbsPostReplyDO reply, PageDto<BbsPostReplyDO> page);
	
	public Integer updateCheckStatus(Integer id, String checkstatus, String admin);
	
	public Integer updateReplyByAdmin(BbsPostReplyDO reply);
	
	public Integer deleteByAdmin(Integer id);
	
	public Integer createReplyByAdmin(BbsPostReplyDO reply,String admin);
	
	public PageDto<BbsPostDO> pageReplyByUser(String account, String checkStatus,Integer categoryId, PageDto<BbsPostDO> page);
	
	public Integer queryBbsPostIdByReplyId(Integer id);
	
	public Integer countMyreply(String account, String checkStatus,Integer bbsPostCategoryId);
	
	public Integer queryReplyOfPostCount(Integer postId, Integer companyId);
	
	public BbsPostReplyDO queryById(Integer id);
	
	public Integer updateIsDel(Integer id, String isDel);
	
	public PageDto<BbsPostReplyDO> pageReplyByAccount(String account,String checkStatuse,Integer categoryId,PageDto<BbsPostReplyDO> page);
	
	public Integer updateReplyByUser(BbsPostReplyDO bbsPostReplyDO,String membershipCode);
	/**
	 * 最佳回答者
	 * @return
	 */
	public List<BbsPostReplyDO> queryBestAnswerByViewCount(Integer size);
	/**
	 * 根据replyId获取回复列表
	 * @param replyId
	 * @return
	 */
	public List<BbsPostReplyDto> queryReplyByReplyId(Integer replyId);

	public BbsPostReplyDO queryLatestReplyByPostId(Integer bbsPostId);
	public Integer queryReplyByUserCount(String account, String checkStatus,Integer bbsPostCategoryId);
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
	public PageDto<BbsPostDO> queryReplyByUser(String account,Integer categoryId,PageDto<BbsPostDO> page);
	/**
	 * 对某回答进行点赞
	 * @param companyId
	 * @param replyId
	 * @return
	 */
	public Integer zanBbsPostReply(Integer companyId,Integer replyId);
}
