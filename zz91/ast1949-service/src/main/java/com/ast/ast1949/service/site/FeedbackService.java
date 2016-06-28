package com.ast.ast1949.service.site;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;

public interface FeedbackService {

	/**
	 * 留言类型为给商务助理的留言
	 */
	final static int CATEGORY_VIP = 3;

	/**
	 * 留言类型为用户给网站反馈问题
	 */
	final static int CATEGORY_MEMBER = 1;

	/**
	 * 留言类型为积分商城的留言
	 */
	final static int CATEGORY_SCORE = 2;

	/**
	 * 留言状态：已回复
	 */
	final static String STATUS_REPLYED = "1";

	/**
	 * 留言状态：不回复
	 */
	final static String STATUS_NO_REPLY = "2";

	/**
	 * 留言状态：申请中
	 */
	final static String STATUS_APPLY = "0";

	/**
	 * 给商务助理留言 check_person是对应的助理，不能为null 留言者不可以是匿名，即company_id,account不允许为null
	 * category不能为null，并且等于 CATEGORY_VIP
	 * 
	 * 
	 */
	public Integer insertFeedbackByVIP(FeedbackDo feedback);

	/**
	 * 任何用户可以向网站反馈问题 反馈问题不能匿名，即companyId,account不能为空
	 * category不能为null，并且等于CATEGORY_MEMBER
	 */
	public Integer insertFeedbackByMember(FeedbackDo feedback);

	/**
	 * 在积分商城给网站留言 可以匿名留言，即companyId,account可选 category不能为null，等于CATEGORY_SCORE
	 */
	public Integer insertFeedbackByScore(FeedbackDo feedback);

	/**
	 * 前台生意管家中查找自己发出的留言信息。 account可填可不填，companyId,category不能为null，检索结果按照发布时间倒序排列
	 */
	public PageDto<FeedbackDo> pageFeedbackHistoryByUser(Integer companyId, String account,
			Integer category, PageDto<FeedbackDo> page);

	/**
	 * 根据类别查找反馈的问题或留言，一般用于在网站某个功能模块上显示该功能模块的所有留言信息 status：选填 category：必填
	 * 
	 * 
	 */
	public PageDto<FeedbackDo> pageFeedbackByCategory(Integer category, String checkStatus,
			PageDto<FeedbackDo> page);

	/**
	 * 在CRM查找用户的留言信息 可用的查询条件有： feedback.check_status：留言状态，可以为null，表示查找全部留言信息
	 * feedback.check_person：留言处理人，即商务助理，可以为null，表示查找全部留言信息
	 * feedback.category：等于CATEGORY_VIP
	 * 
	 * 
	 */
//	public PageDto<FeedbackDto> pageFeedbackByCrm(FeedbackDo feedback, Integer adminUserId,
//			PageDto<FeedbackDto> page);

	/**
	 * 在网站后台管理中查找用户的留言信息 可用的查询条件有： feedback.check_status：留言状态，可以为null，表示查找全部留言信息
	 * feedback.check_person：留言处理人，即商务助理，可以为null，表示查找全部留言信息
	 * feedback.category：不能为null
	 */
	public PageDto<FeedbackDto> pageFeedbackByAdmin(FeedbackDo feedback,
			PageDto<FeedbackDto> page);

	/**
	 * 根据留言ID查找一条留言信息 id不能为null
	 */
	public FeedbackDo queryFeedbackById(Integer id);

	/**
	 * 回复留言信息 id：待回复的留言ID，不能为null replyContent：回复的内容，不能为null admin：后台管理员，不能为null
	 * 回复时，自动将check_status的状态更改为STATUS_REPLYED
	 * 
	 * 
	 */
	public Integer replyFeedback(Integer id, String replyContent, String admin);

	/**
	 * 管理员删除留言信息 id：不能为null
	 */
	public Integer deleteFeedbackByAdmin(Integer id);

	/**
	 * 将留言状态置为不回复，留言状态变为STATUS_NO_REPLY
	 */
	public Integer notReplyFeedback(Integer id, String admin);
}
