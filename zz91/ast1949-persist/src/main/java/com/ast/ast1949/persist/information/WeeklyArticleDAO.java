/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.WeeklyArticleDO;
import com.ast.ast1949.dto.information.WeeklyDTO;

/**
 * @author yuyonghui
 *
 */
public interface WeeklyArticleDAO {

	/**
	 *   根据版面查询所有文章
	 * @param pageId  不能为空
	 * @return List<WeeklyDTO>
	 * 
	 */
	public List<WeeklyDTO> ListWeeklyArticleByPageId(Integer pageId);
	/**
	 *  查询期刊下所有bbs文章信息
	 *  @param   periodicalId
	 * 	@return   List<WeeklyDTO> 
	 * 
	 */
	public List<WeeklyDTO>  listBbsAndWeeklyArticle(Integer periodicalId);
	 /**
	  *  添加 版面
	  * @param weeklyPageDO 不能为空
	  * @return 结果>0 添加成功
	  *         反之添加失败
	  */
	public Integer insertWeeklyArticle(Integer pageId,Integer[] bbsPostIds);
	/**
	 *   删除文章
	 * @param entities 不能为空
	 * @return if>0 删除成功
	 *         反之删除失败
	 */
	public Integer deleteWeeklyArticle(int[] entities);
	/**
	 *   查询上一篇文章
	 * @param pageId 版面Id 不能为空
	 * @param bbsPostId 帖子Id 不能为空
	 * @return List<WeeklyDTO>
	 */
	public WeeklyArticleDO listOnArticle(Integer pageId,Integer bbsPostId);
	/**
	 *  查询下一篇文章
	 * @param pageId  版面Id 不能为空
	 * @param bbsPostId 帖子Id 不能为空
	 * @return WeeklyDTO
	 */
	public WeeklyArticleDO listDownArticle(Integer pageId,Integer bbsPostId);
}
