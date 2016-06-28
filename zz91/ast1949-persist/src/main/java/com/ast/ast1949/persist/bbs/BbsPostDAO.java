/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 8, 2010 by Rolyer.
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostType;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface BbsPostDAO {
	
	public List<BbsPostDO> queryRecentByAccount(String account, Integer maxSize);
	
	public List<PostDto> queryPostByCategory(Integer categoryId, PageDto<PostDto> page);
	
	public Integer queryPostByCategoryCount(Integer categoryId);
	
	public List<PostDto> queryPostBySearch(String keywords, PageDto<PostDto> page);
	
	public Integer queryPostBySearchCount(String keywords);
	
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId, Integer maxSize);
	
	public Integer queryPostByAdminCount(String account, BbsPostDO post);
	
	public List<BbsPostDO> queryPostByAdmin(String account, BbsPostDO post, PageDto<PostDto> page);
	
	public Integer updateCheckStatus(Integer id, String checkStatus, String admin);
	
	public Integer updateIsDel(Integer id, String isDel);
	
	public Integer deleteBbsPost(Integer id);
	
	/**
	 * 查找全部字段，包括content
	 */
	public BbsPostDO queryPostById(Integer id);
	
	public Integer insertPost(BbsPostDO post);
	
	/**
	 * 更新的字段：
	 * 		title
	 * 		content
	 * 		bbs_post_category_id
	 * 		post_type
	 * 		integral
	 * 		tags
	 * 		visited_count
	 * 		reply_count
	 * 		post_time
	 * 		reply_time
	 */
	public Integer updatePostByAdmin(BbsPostDO post);
	
	public List<BbsPostDO> queryPostWithContentByType(String type, Integer size);
	public List<PostDto> queryPostByType(String type, Integer size);
	
	public List<BbsPostDO> queryPostOrderBy(String sort, String dir, Integer size);
	
	public List<BbsPostDO> queryPostByViewLog(long targetDate, Integer size);
	
	public List<BbsPostDO> queryPostByUser(String account, String checkStatus, String isDel, PageDto<BbsPostDO> page);
	
	public Integer queryPostByUserCount(String account, String checkStatus, String isDel);
	
	public Integer updatePostByUser(BbsPostDO post);
	
	public PostDto queryPostWithProfileById(Integer id);
	
	public List<BbsPostDO> queryPostOrderVisitCount(String account, Integer size);
	
	public String queryContent(Integer id);
	
	public Integer updateContent(Integer id, String content);
	
	public List<BbsPostType> queryPostTypeChild(String parentCode);
	
	public Integer queryPostTypeChildCount(String parentCode);	
	
	public String queryPostTypeName(Integer postType);
	
	/*********
	 * 互助首页，以及最终页的 “最新话题查询”
	 * @param size
	 * 查询的记录条数
	 * @return
	 */
	public List<BbsPostDO> queryTheNewsPost(String account, Integer maxSize);
	/****************
	 * 互助首页，更新 互助周报。
	 * @param type
	 * @return
	 */
	public List<PostDto> queryMorePostByType(String type,PageDto<PostDto> page);
	
	public Integer queryMorePostByTypeCount(String type);
	
	/****************
	 * 再生地图，查询资讯
	 * @param title (关键字)
	 * @param size
	 * @return
	 */
	public List<PostDto> queryPostByKey(String title, Integer size);
	
	public Integer updateCheckStatusForFront(Integer id, String checkStatus);
	
	public List<BbsPostDO> queryMostViewedPost(String targetDate, Integer size);
	
	public List<BbsPostDO> query24HourHot(String targetDate, Integer size);
	
	public BbsPostDO queryOnBbsPostById(Integer id);
	
	public BbsPostDO queryDownBbsPostById(Integer id);
}
