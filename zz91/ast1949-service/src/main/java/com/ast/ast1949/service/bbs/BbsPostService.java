/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 11, 2010 by Rolyer.
 */
package com.ast.ast1949.service.bbs;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface BbsPostService {
	
	final static Integer BBS_CATEGORY_QA = 1;
	final static Integer BBS_CATEGORY_POST = 2;
	final static Integer BBS_CATEGORY_XY = 3;
	final static Integer BBS_CATEGORY_SQ = 106;
	final static String CHECK_STATUS_UNPASS = "3";
	

	public Map<String, List<BbsPostDO>> queryRecentPostOfAccounts(Set<String> account);
	
	public PageDto<PostDto> pagePostByCategory(Integer categoryId,Integer searchType, PageDto<PostDto> page);
	
	public PageDto<PostDto> pagePostBySearch(String keywords , PageDto<PostDto> page);
	
	public List<BbsPostDO> querySimplePostByCategory(Integer categoryId, Integer max);
	
	public PageDto<PostDto> pagePostByAdmin(String account, BbsPostDO post, PageDto<PostDto> page, String selectTime,String from,String to);
	
	public Integer updateCheckStatus(Integer id, String checkStatus, String admin);
	
	public Integer updateIsDel(Integer id, String isDel);
	
	public Integer deleteBbsPost(Integer id);
	
	public BbsPostDO queryPostById(Integer id);
	
	public Integer createPostByAdmin(BbsPostDO post,String admin);
	
	public Integer updatePostByAdmin(BbsPostDO post);
	
	public List<BbsPostDO> queryPostWithContentByType(String type, Integer size);
	
	public List<PostDto> queryPostByType(String type, Integer size);
	
	public List<BbsPostDO> queryNewestPost(Integer size);
	
	public List<BbsPostDO> queryMostViewedPost(Integer size);
	
	public List<BbsPostDO> query24HourHot(Integer size,Integer categoryId);
	
	public Integer createPostByUser(BbsPostDO post, String membershipCode);
	
	public PageDto<BbsPostDO> pagePostByUser(String account, String check_status, String is_del,Integer bbsPostCategoryId,String replyAccount,String title, PageDto<BbsPostDO> page);
	
	public Integer updatePostByUser(BbsPostDO post, String membershipCode);
	
	public PageDto<PostDto> pagePostBySearchEngine(String keywords, PageDto<PostDto> page);
	
	public PageDto<PostDto> pagePostForNoAnswerBySearchEngine(String keywords, PageDto<PostDto> page);
	/**
	 * 根据关键字获取相关帖子或问答
	 * @param keywords
	 * @param page
	 * @param type 帖子或者问答
	 * @param page 用来标志搜索关键字的条件
	 * @return
	 */
	public PageDto<BbsPostDO> pagePostByNewSearchEngine(String keywords, PageDto<BbsPostDO> page,Integer type,Integer flag);
	/**
	 * 互助首页废料学院
	 * @param keywords
	 * @param page
	 * @param assistId
	 * @return
	 */
	public PageDto<BbsPostDO> pageCollegeByEngine(String keywords, PageDto<BbsPostDO> page,Integer assistId);
	
	public List<BbsPostDO> queryHotPost(String account, Integer size);
	
	public String queryContent(Integer id);
	
	public Integer updateContent(Integer id, String content);
	
	public List<ExtTreeDto> queryPostTypechild(String parentCode);
	
	public String queryPostTypeName(String postType);
	
	 /******
	  * 互助首页，以及最终页的 “最新话题查询”
	  * @param page
	  * 分页
	  * @return
	  */
	public PageDto<PostDto> pageTheNewsPost(Integer maxSize , PageDto<PostDto> page);
	/****************
	 * 互助首页，更新 互助周报。
	 * @param type
	 * @return
	 */
	public PageDto<PostDto> pageMorePostByType(String type, PageDto<PostDto> page);
	
	public Integer countMyposted(String account, String checkStatus,String isDel,Integer bbsPostCategoryId);
	/**
	 * 统计我发贴总数
	 * @param account
	 * @param checkStatus
	 * @param isDel
	 * @param bbsPostCategoryId
	 * @return
	 */
	public Integer countMyBbs(String account, String checkStatus,String isDel,Integer bbsPostCategoryId);
	
	public Integer updateCheckStatusForFront(Integer id, String checkStatus);
	
	public BbsPostDO queryOnBbsPostById(Integer id);
	
	public BbsPostDO queryDownBbsPostById(Integer id);
	
	/**
	 * 按类别统计帖子数
	 * */
	public Integer queryCountPostByTime(String fromDate, String toDate,Integer categoryId);
	/**
	 * 最热问答
	 * **/
	public List<PostDto> query24HourHotByAnswer(Integer size,Integer categoryId);
	
	/**
	 * 推荐到头条
	 */
	public Integer recommendPostById(Integer postId);
	/**
	 * 热门关注
	 */
	public List<BbsPostDO> queryBbsPostByNoticeAReplyTime(Integer size);
	/**
	 * 某辅助类别的帖子和问答
	 */
	public List<BbsPostDO> queryBbsPostByAssistId(Integer assistId,Integer size);
	/**
	 * 等待回答
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryWaitAnswerBbsPost(Integer size);
	/**
	 * 热门问答
	 * @param size
	 * @return
	 */
	public List<BbsPostDO> queryHotBbsPost(Integer size,Integer categoryId);
	/**
	 * 发现问题列表
	 * @param page
	 * @param date
	 * @return
	 */
	public PageDto<BbsPostDO> ListForQA(PageDto<BbsPostDO> page,String date);
	/**
	 * 等待问答列表
	 * @param page
	 * @return
	 */
	public PageDto<BbsPostDO> ListForWait(PageDto<BbsPostDO> page);
	/**
	 * 最新问题列表
	 * @param page
	 * @param date
	 * @return
	 */
	public PageDto<BbsPostDO> ListForNewQA(PageDto<BbsPostDO> page,String date);
    /**
     * 热门关注列表
     * @param page
     * @return
     */
	public PageDto<BbsPostDO> ListForHotNotice(PageDto<BbsPostDO> page);
	/**
	 * 社区列表页
	 * @param page
	 * @param assistId
	 * @param flag
	 * @return
	 */
	public PageDto<BbsPostDO> ListBbsPost(PageDto<BbsPostDO> page,Integer assistId,Integer flag);
	/**
	 * 等我答列表页
	 * @param page
	 * @return
	 */
	public PageDto<PostDto> ListWaitAnswer(PageDto<PostDto> page);
	/**
	 * 某用户帖子或问答列表
	 * @param categoryId
	 * @param account
	 * @param page
	 * @return
	 */
	public PageDto<PostDto> ListBbsPostByUser(Integer categoryId,String account,PageDto<PostDto> page,Integer type,String keywords);
	/**
	 * 热门问答列表页
	 * @param page
	 * @return
	 */
	public PageDto<BbsPostDO> ListHostBbsPost(PageDto<BbsPostDO> page);
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
	public PageDto<BbsPostDO> ListBbsPostByCompanyId(Integer companyId,Integer categoryId,PageDto<BbsPostDO> page);
	/**
	 * 社区列表页的搜索引擎
	 * @param keywords
	 * @param page
	 * @param type
	 * @param flag
	 * @return
	 */
	public PageDto<BbsPostDO> pagePostForBbsCategory(String keywords,PageDto<BbsPostDO> page,Integer type,Integer flag);
	/**
	 * 问答列表页的搜索引擎
	 * @param keywords
	 * @param page
	 * @param flag
	 * @return
	 */
	public PageDto<BbsPostDO> pagePostForViewQue(String keywords,PageDto<BbsPostDO> page,Integer flag);
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
	public PageDto<BbsPostDO> pagePostNew(Integer cateGoryId,PageDto<BbsPostDO> page);
	/**
	 * 根据id将帖子转移至商圈功能
	 */
    public Integer changShangQuan(Integer id);
}
