/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-10
 */
package com.ast.ast1949.persist.site.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.site.FeedbackDao;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-10
 */
@Component("feedbackDao")
public class FeedbackDaoImpl extends BaseDaoSupport implements FeedbackDao {

	final static String SQL_PREFIX = "feedback";

	@Override
	public Integer deleteFeedbackById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteFeedbackById"),
				id);
	}

	@Override
	public Integer insertFeedback(FeedbackDo feedback) {
		Assert.notNull(feedback, "the object of feedback must not be null");
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insertFeedback"), feedback);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FeedbackDo> queryFeedback(FeedbackDo feedback, PageDto<FeedbackDo> page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("feedback", feedback);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryFeedback"),
				param);
	}

	@Override
	public FeedbackDo queryFeedbackById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (FeedbackDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryFeedbackById"), id);
	}

	@Override
	public Integer queryFeedbackCount(FeedbackDo feedback) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("feedback", feedback);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryFeedbackCount"), param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FeedbackDto> queryFeedbackWithUserInfo(FeedbackDo feedback,
			PageDto<FeedbackDto> page) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("feedback", feedback);
		param.put("page", page);

		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryFeedbackWithUserInfo"), param);
	}

	@Override
	public Integer queryFeedbackWithUserInfoCount(FeedbackDo feedback) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("feedback", feedback);
		
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryFeedbackWithUserInfoCount"), param);
	}

	@Override
	public Integer updateFeedbackForReply(Integer id, String replyContent, String checkPerson,
			String checkStatus) {
		Assert.notNull(id, "the id must not be null");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("replyContent", replyContent);
		param.put("checkPerson", checkPerson);
		param.put("checkStatus", checkStatus);

		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateFeedbackForReply"), param);
	}

	@Override
	public Integer updateCheckStatus(Integer id, String checkStatus, String checkPerson) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(checkStatus, "the checkStatus must not be null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("checkStatus", checkStatus);
		param.put("checkPerson", checkPerson);
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatus"), param);
	}

}
