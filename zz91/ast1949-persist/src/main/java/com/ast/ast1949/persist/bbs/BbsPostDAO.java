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
import com.ast.ast1949.dto.bbs.BbsPostSearchDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.dto.company.CompanyDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface BbsPostDAO {
	
	public List<BbsPostDO> queryRecentByAccount(String account, Integer maxSize);
	
	public List<PostDto> queryPostByCategory(Integer categoryId,Integer searchType, PageDto<PostDto> page);
	
	public Integer queryPostByCategoryCount(Integer categoryId,Integer searchType);
	
	public List<PostDto> queryPostBySearch(String keywords, PageDto<PostDto> page);
	
	public Integer queryPostBySearchCount(String keywords);
	/**
	 * 最新问答
	 * @param categoryId
	 * @param maxSize
	 * @return
	 */
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId, Integer maxSize);
	
	public Integer queryPostByAdminCount(String account, BbsPostDO post,String selectTime,String from,String to);
	
	public List<BbsPostDO> queryPostByAdmin(String account, BbsPostDO post, PageDto<PostDto> page,String selectTime,String from,String to);
	
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
	
	public List<BbsPostDO> queryPostByUser(BbsPostSearchDto bbsPostSearchDto, PageDto<BbsPostDO> page);
	
	public Integer queryPostByUserCount(BbsPostSearchDto bbsPostSearchDto);
	/**
	 * 统计我的发贴和问答数
	 * @param bbsPostSearchDto
	 * @return
	 */
	public Integer queryMyBbsCount(BbsPostSearchDto bbsPostSearchDto);
	
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
	
	public List<BbsPostDO> query24HourHot(String targetDate, Integer size,Integer categoryId);
	
	public BbsPostDO queryOnBbsPostById(Integer id);
	
	public BbsPostDO queryDownBbsPostById(Integer id);
	/***
	 *按类别统计所有的帖子 
	 * ****/
	public Integer queryCountPostByTime(String fromDate,String toDate,Integer categoryId);
	/**
	 * 24小时最热问答
	 * **/
	public List<PostDto> query24HourHotByAnswer(String targetDate, Integer size,Integer categoryId);
	
	public Integer recommendPostById(Integer postId);
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Integer> bbsPostCompany(PageDto<CompanyDto> page);
	/**
	 * 统计有发布帖子的公司
	 * @return
	 */
	public Integer bbsPostCompanyCount();
	/**
	 * 热门关注
	 */
	public List<BbsPostDO> queryBbsPostByNoticeAReplyTime(Integer size);
	/**
	 * 某辅助类别的帖子
	 */
	public List<BbsPostDO> queryBbsPostByAssistId(Integer assistId,Integer size);
	/**
	 * 等待回答
	 */
	public List<BbsPostDO> queryWaitAnswerBbsPost(Integer size);
	/**
	 * 热门回答
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryHotBbsPost(Integer size,Integer categoryId);
	/**
	 * 发现问题列表（按1月内点击率排序的问答列表）
	 * @param page
	 * @param date
	 * @return
	 */
	public List<BbsPostDO> queryListForQA(PageDto<BbsPostDO> page,String date);
	/**
	 * 发现问题列表记录总条数
	 * @param page
	 * @param date
	 * @return
	 */
	public Integer countListForQA(String date);
	/**
	 * 等待问答列表
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryListForWait(PageDto<BbsPostDO> page);
	/**
	 * 等待问答列表记录总条数
	 * @return
	 */
	public Integer countListForWait();
	/**
	 * 最新问题列表
	 * @param page
	 * @param date
	 * @return
	 */
	public List<BbsPostDO> queryListForNewQA(PageDto<BbsPostDO> page,String date);
	/**
	 * 最新列表记录总条数
	 * @param date
	 * @return
	 */
	public Integer countListForNewQA(String date);
	/**
	 * 热门关注列表
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryListForHotNotice(PageDto<BbsPostDO> page);
	/**
	 * 热门关注列表记录总条数
	 * @return
	 */
	public Integer countListForHotNotice();
	/**
	 * 辅助类别下的全部、热门、精华帖子列表
	 * @param page
	 * @param assistId
	 * @return
	 */
	public List<BbsPostDO> queryAllListBbsPost(PageDto<BbsPostDO> page,Integer assistId);
	/**
	 * 某辅助类别下帖子列表记录的总数
	 * @param assistId
	 * @return
	 */
	public Integer countBbsPostByassistId(Integer assistId);
	/**
	 * 全部、热门、精华头条列表
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryToutiaoByPostType(PageDto<BbsPostDO> page);
	/**
	 * 头条列表记录总条数
	 * @return
	 */
	public Integer countToutiaoByPostType();
	/**
	 * 等我答列表
	 * @param page
	 * @return
	 */
	public List<PostDto> queryWaitAnswerByReplyCount(PageDto<PostDto> page);
	/**
	 * 等我答列表记录总数
	 * @return
	 */
	public Integer countWaitAnswerByReplyCount();
	/**
	 * 某用户的帖子或问答
	 * @param categoryId
	 * @param account
	 * @param page
	 * @return
	 */
	public List<PostDto> queryBbsPostByUser(Integer categoryId,String account,PageDto<PostDto> page,String keywords);
	/**
	 * 某用户的问答或帖子数
	 * @param categoryId
	 * @param account
	 * @return
	 */
	public Integer countBbsPostByUser(Integer categoryId,String account,String keywords);
	/**
	 * 修改关注数
	 * @param id
	 * @param noticeCount
	 */
	public void updateNoticeCount(Integer id,Integer noticeCount);
	/**
	 * 修改收藏数
	 * @param id
	 * @param collectCount
	 */
	public void updateCollectCount(Integer id,Integer collectCount);
	/**
	 * 修改推荐数
	 * @param id
	 * @param recommendCount
	 */
	public void updateRecommendCount(Integer id,Integer recommendCount);
	/**
	 * 热门问答列表页
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> queryListHotBbsPost(PageDto<BbsPostDO> page);
	/**
	 * 问答列表页记录数
	 * @return
	 */
	public Integer countListHotBbsPost();
	/**
	 * 某用户的帖子或问答数
	 * @param companyId
	 * @param categoryId
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryBbsPostByCompanyId(Integer companyId,Integer categoryId,Integer size);
	/**
	 * 某用户的帖子或问答数量
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	public Integer countBbsPostByCompanyId(Integer companyId,Integer categoryId);
	
	/**
	 * 新某用户的帖子或问答数量
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	public Integer countBbsByCompanyId(Integer companyId,Integer categoryId);
	
	/**
	 * 某用户的帖子或问答的列表
	 * @param companyId
	 * @param categoryId
	 * @param page
	 * @return
	 */
	public List<BbsPostDO> ListBbsPostByCompanyId(Integer companyId,Integer categoryId,PageDto<BbsPostDO> page);
	/**
	 * 大家都在关注的bbsPost
	 * @param categoryId
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryPostByNoticeAndView(Integer categoryId,Integer size);
	/**
	 * 获取最新的帖子或问答
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryNewPost(Integer size);
	/**
	 * 浏览量
	 * @param visitCount
	 * @param id
	 */
	public Integer updateVisitedCountById(Integer visitCount,Integer id);
	/**
	 * 最新商机 热门关注 热门回复
	 */
	public List<BbsPostDO> pagePostNew(Integer cateGoryId,
			PageDto<BbsPostDO> page);
	/**
	 * 最新商机 热门关注 热门回复 de 信息记录
	 */
	public Integer pagePostNewCount(Integer cateGoryId);
	/**
	 * 将帖子转移至商圈
	 * @param id
	 * @return
	 */
	public Integer changShangQuan(Integer id);
	
}
