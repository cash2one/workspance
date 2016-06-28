/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-10
 */
package com.ast.ast1949.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FeedbackDto;
import com.ast.ast1949.persist.site.FeedbackDao;
import com.ast.ast1949.service.site.FeedbackService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-3-10
 */
@Component("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackDao feedbackDao;

	@Override
	public Integer deleteFeedbackByAdmin(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return feedbackDao.deleteFeedbackById(id);
	}

	@Override
	public Integer insertFeedbackByMember(FeedbackDo feedback) {
		Assert.notNull(feedback, "the object of feedback must not be null");
		Assert.notNull(feedback.getCompanyId(), "the companyId must not be null");
		Assert.notNull(feedback.getAccount(), "the account must not be null");
		if(feedback.getCategory()==null){
			feedback.setCategory(CATEGORY_MEMBER);
		}
		feedback.setCheckStatus(STATUS_APPLY);
		return feedbackDao.insertFeedback(feedback);
	}

	@Override
	public Integer insertFeedbackByScore(FeedbackDo feedback) {
		Assert.notNull(feedback, "the object of feedback must not be null");
		if (feedback.getAccount() == null) {
			feedback.setAccount("");
		}
		if (feedback.getCompanyId() == null) {
			feedback.setCompanyId(0);
		}
		if (feedback.getEmail() == null) {
			feedback.setEmail("");
		}
		feedback.setCategory(CATEGORY_SCORE);
		feedback.setCheckStatus(STATUS_APPLY);
		return feedbackDao.insertFeedback(feedback);
	}

	@Override
	public Integer insertFeedbackByVIP(FeedbackDo feedback) {
		Assert.notNull(feedback, "the object of feedback must not be null");
		Assert.notNull(feedback.getCompanyId(), "the companyId must not be null");
		Assert.notNull(feedback.getAccount(), "the account must not be null");

		feedback.setCategory(CATEGORY_VIP);
		feedback.setCheckStatus(STATUS_APPLY);
		return feedbackDao.insertFeedback(feedback);
	}

	@Override
	public Integer notReplyFeedback(Integer id, String admin) {
		Assert.notNull(id, "the id must not be null");
		return feedbackDao.updateCheckStatus(id, STATUS_NO_REPLY, admin);
	}

	@Override
	public PageDto<FeedbackDto> pageFeedbackByAdmin(FeedbackDo feedback, PageDto<FeedbackDto> page) {
		Assert.notNull(page, "the object of page must not be null");
		Assert.notNull(feedback, "the object of feedback must not be null");
		if(page.getSort()==null){
			page.setSort("id");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		List<FeedbackDto> list=feedbackDao.queryFeedbackWithUserInfo(feedback, page);
		for(FeedbackDto dto:list){
			if(dto.getCompany()==null){
				dto.setCompany(new Company());
			}
			if(dto.getContact()==null){
				dto.setContact(new CompanyAccount());
			}
		}
		page.setRecords(list);
		page.setTotalRecords(feedbackDao.queryFeedbackWithUserInfoCount(feedback));
		return page;
	}

	@Override
	public PageDto<FeedbackDo> pageFeedbackByCategory(Integer category, String checkStatus,
			PageDto<FeedbackDo> page) {
		Assert.notNull(page, "the object of page must not be null");
		Assert.notNull(category, "the category must not be null");

		FeedbackDo feedback = new FeedbackDo();
		feedback.setCategory(category);
		feedback.setCheckStatus(checkStatus);
		page.setTotalRecords(feedbackDao.queryFeedbackCount(feedback));
		page.setRecords(feedbackDao.queryFeedback(feedback, page));
		return page;
	}

//	@Override
//	public PageDto<FeedbackDto> pageFeedbackByCrm(FeedbackDo feedback, Integer adminUserId,
//			PageDto<FeedbackDto> page) {
//		Assert.notNull(page, "the object of page must not be null");
//
//		feedback.setCategory(CATEGORY_VIP);
//		page.setTotalRecords(feedbackDao.queryFeedbackWithUserInfoCount(feedback, adminUserId));
//		page.setRecords(feedbackDao.queryFeedbackWithUserInfo(feedback, adminUserId, page));
//		return page;
//	}

	@Override
	public PageDto<FeedbackDo> pageFeedbackHistoryByUser(Integer companyId, String account,
			Integer category, PageDto<FeedbackDo> page) {
		Assert.notNull(page, "the object of page must not be null");
		Assert.notNull(companyId, "the companyId must not be null");
		Assert.notNull(category, "the category must not be null");

		FeedbackDo feedback = new FeedbackDo();
		feedback.setCompanyId(companyId);
		feedback.setCategory(category);
		if (!StringUtils.isEmpty(account)) {
			feedback.setAccount(account);
		}
		page.setTotalRecords(feedbackDao.queryFeedbackCount(feedback));
		page.setRecords(feedbackDao.queryFeedback(feedback, page));
		return page;
	}

	@Override
	public FeedbackDo queryFeedbackById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return feedbackDao.queryFeedbackById(id);
	}

	@Override
	public Integer replyFeedback(Integer id, String replyContent, String admin) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(replyContent, "the replyContent must not be null");
		Assert.notNull(admin, "the admin must not be null");

		return feedbackDao.updateFeedbackForReply(id, replyContent, admin, STATUS_REPLYED);
	}

}
