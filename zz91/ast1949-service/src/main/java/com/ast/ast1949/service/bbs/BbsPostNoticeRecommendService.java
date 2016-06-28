package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostNoticeRecommend;
import com.ast.ast1949.domain.bbs.BbsPostTags;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostNoticeRecommendDto;
import com.ast.ast1949.dto.bbs.NoticeDto;
import com.ast.ast1949.dto.bbs.PostDto;

/**
 * @author shiqp 日期：2014-11-10
 */
public interface BbsPostNoticeRecommendService {

	final static Integer CATEGORY_POST = 1;  //帖子
	final static Integer CATEGORY_QA = 0;  // 问题
	final static Integer CATEGORY_TAG = 3; // 标签 
	final static Integer CATEGORY_USER_PAGE = 2; //个人主页
	
	final static Integer TYPE_NOTICE = 1; //关注
	final static Integer TYPE_RECOMMEND = 0; //推荐

	/**
	 * 最新推荐
	 * 
	 * @param bbs
	 * @param size
	 * @return
	 */
	public List<BbsPostNoticeRecommendDto> queryZuiXinRecommendByCondition(
			BbsPostNoticeRecommend bbs, Integer size);

	/**
	 * 某用户关注的某个类别的多少条信息
	 * 
	 * @param account
	 * @param category
	 * @param size
	 * @return
	 */
	public List<NoticeDto> ListNoticeByUser(String account, Integer category,
			Integer size);

	/**
	 * 插入新的推荐或关注
	 * 
	 * @param bbs
	 * @return
	 */
	public Integer insertNoticeOrRecomend(BbsPostNoticeRecommend bbs);
	
	/**
	 * 删除关注的标签
	 * 
	 */
	public Integer deleteTag(String account,Integer contentId);

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
	 * 某用户的关注的某类别信息
	 * 
	 * @param account
	 * @param category
	 * @param size
	 * @return
	 */
	public List<BbsPostNoticeRecommend> queryNoticeByUser(String account,Integer category, Integer size);
	public List<BbsPostDO> queryNoticeByAccount(String account,Integer category, Integer size);

	/**
	 * 关注帖子、问题分页
	 * @param account
	 * @param category
	 * @param page
	 * @return
	 */
	public PageDto<PostDto> pageNotice(String account,Integer category,PageDto<PostDto> page);
	
	/**
	 * 检索我关注的领域 (我关联的标签)
	 * 默认最新的 一百个标签
	 * @return
	 */
	public List<BbsPostTags> queryListForMyTags(String account);

	/**
	 * 检索最新 10个标签 作为 搜索引擎关键字使用 使用于待我答页面
	 * @param account
	 * @return
	 */
	public List<BbsPostTags> queryListForMyFollowSearch(String account);
}
