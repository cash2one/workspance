/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.persist.bbs;

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
public interface BbsPostReplyDao {

	public List<BbsPostReplyDto> queryReplyOfPost(Integer postId, PageDto<BbsPostReplyDto> page);
	
	public Integer queryReplyOfPostCount(Integer postId);
	
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
	
	public List<BbsPostDO> queryReplyByUser(String account, String checkStatus, PageDto<BbsPostDO> page);
	
	public Integer queryReplyByUserCount(String account, String checkStatus);
	
	public Integer queryBbsPostByReplyId(Integer id);
}
