package com.ast.ast1949.persist.site;

import java.util.List;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;

public interface FeedbackDao {
 
	/**
	 * 添加留言信息
	 */
	public Integer insertFeedback(FeedbackDo feedback);
	/**
	 * 根据ID查找一个留言信息
	 */
	public FeedbackDo queryFeedbackById(Integer id);
	/**
	 * 更新留言回复
	 *
	 *  
	 */
	public Integer updateFeedbackForReply(Integer id, String replyContent, String checkPerson,String checkStatus);
	/**
	 * 管理员删除留言信息
	 * id：不能为null
	 */
	public Integer deleteFeedbackById(Integer id);
	/**
	 * 根据条件查找留言信息
	 * 可选的条件有：
	 * feedback.companyId
	 * feedback.account
	 * feedback.category
	 * feedback.checkStatus
	 *
	 *  
	 */
	public List<FeedbackDo> queryFeedback(FeedbackDo feedback, PageDto<FeedbackDo> page);
	/**
	 * 统计总数
	 *
	 *  
	 */
	public Integer queryFeedbackCount(FeedbackDo feedback);
	/**
	 * 查找留言信息及留言账户的相关信息
	 * 可选的条件有：
	 * feedback.companyId
	 * feedback.account
	 * feedback.category
	 * feedback.checkStatus
	 * feedback.checkPerson
	 */
	public List<FeedbackDto> queryFeedbackWithUserInfo(FeedbackDo feedback, PageDto<FeedbackDto> page);
	/**
	 * 统计总数
	 *
	 *  
	 */
	public Integer queryFeedbackWithUserInfoCount(FeedbackDo feedback);
	/**
	 * 更新状态
	 * @param id 编号
	 * @param checkStatus 状态：0 新留言；1 已处理的留言；2 不予处理的留言。
	 * @param checkPerson 处理人
	 * @return
	 */
	public Integer updateCheckStatus(Integer id,String checkStatus, String checkPerson);
}
 
