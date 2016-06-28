/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs;

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

	public PageDto<BbsPostReplyDto> pageReplyOfPost(Integer postId, String iconTemplate, PageDto<BbsPostReplyDto> page);
	
	public PageDto<BbsPostReplyDO> pageReplyByAdmin(BbsPostReplyDO reply, PageDto<BbsPostReplyDO> page);
	
	public Integer updateCheckStatus(Integer id, String checkstatus, String admin);
	
	public Integer updateReplyByAdmin(BbsPostReplyDO reply);
	
	public Integer deleteByAdmin(Integer id);
	
	public Integer createReplyByAdmin(BbsPostReplyDO reply,String admin);
	
	public PageDto<BbsPostDO> pageReplyByUser(String account, String checkStatus, PageDto<BbsPostDO> page);
	
	public Integer queryBbsPostIdByReplyId(Integer id);
	
	public Integer countMyreply(String account, String checkStatus);
	
	
	
}
