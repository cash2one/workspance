/**
 * 
 */
package com.zz91.ep.service.common.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.sys.FeedbackDao;
import com.zz91.ep.domain.sys.Feedback;
import com.zz91.ep.service.common.FeedbackService;

/**
 * @author mays
 *
 */
@Component("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	@Resource
	private FeedbackDao feedbackDao;
	
	@Override
	public Integer create(Feedback feedback) {
		feedback.setResponseStatus("U");
		return feedbackDao.insert(feedback);
	}


}
