/**
 * @author shiqp 日期:2014-11-10
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;

public interface BbsPostNoticeRecommendDao {
	/**
	 * 最新推荐
	 * 
	 * @param bbs
	 * @param size
	 * @return List<BbsPostNoticeRecommend>
	 */
	public List<BbsPostNoticeRecommend> queryZuiXinRecommendByCondition(
			BbsPostNoticeRecommend bbs, Integer size);

	/**
	 * 某用户的推荐/关注数
	 * 
	 * @param bbs
	 * @return
	 */
	public Integer countNumbyCompanyId(BbsPostNoticeRecommend bbs);

	/**
	 * 被推荐或被关注的次数
	 * 
	 * @param bbs
	 * @return
	 */
	public Integer countNumByContentId(BbsPostNoticeRecommend bbs);

	/**
	 * 某用户关注的某个类别的多少条信息
	 * 
	 * @param account
	 * @param size
	 * @param category
	 * @return
	 */
	public List<BbsPostNoticeRecommend> queryNoticeByUser(String account,Integer size, Integer category);

	/**
	 * 插入新的推荐或关注
	 * 
	 * @param bbs
	 * @return
	 */
	public Integer insertNoticeOrRecomend(BbsPostNoticeRecommend bbs);

	/**
	 * 判断该问答或帖子有没有被关注或推荐过
	 * 
	 * @param bbs
	 * @return
	 */
	public Integer querySimpleNoOrRem(BbsPostNoticeRecommend bbs);

	public List<BbsPostNoticeRecommend> queryListNotice(String account,Integer category,PageDto<PostDto> page);
	
	public Integer queryListNoticeCount(String account,Integer category);

	/**
	 * 更改信息 state 状态 为 删除
	 * @param account
	 * @param contentId
	 * @param category
	 * @return
	 */
	public Integer updateStateToDel(String account, Integer contentId, Integer category);
	
}
