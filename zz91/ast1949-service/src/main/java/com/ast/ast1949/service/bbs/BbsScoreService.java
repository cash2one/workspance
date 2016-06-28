package com.ast.ast1949.service.bbs;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsScoreDto;

public interface BbsScoreService {
	
	final static Integer POST_PIC = 2;

	public Integer insert(BbsScore bbsScore);

	public PageDto<BbsScoreDto> page(BbsScoreDto bbsScoreDto,PageDto<BbsScoreDto> page);

	public Integer sumScore(BbsScore bbsScore);

	/**
	 * 计算发贴获得积分
	 * @param bbsPostDO
	 * @return
	 */
	public Integer postScore(BbsPostDO bbsPostDO);

	/**
	 * 计算回复获得积分
	 * @return
	 */
	public Integer replyScore(BbsPostReplyDO bbsPostReplyDO);

	/**
	 * 审核 帖子|问题
	 * @param id
	 * @param isPass
	 * @return
	 */
	public Integer checkPost(Integer id, Integer isPass);

	/**
	 * 审核 回复|回答
	 * @param id
	 * @param isPass
	 * @return
	 */
	public Integer checkReply(Integer id, Integer isPass);

	/**
	 * 删除 帖子|问题
	 * @param id
	 * @param isDel
	 * @return
	 */
	public Integer delPost(Integer id, Integer isDel);
	/**
	 * 删除 帖子|问题
	 * @param id
	 * @param isDel
	 * @return
	 */
	public Integer delreply(Integer id, Integer isDel);

}
