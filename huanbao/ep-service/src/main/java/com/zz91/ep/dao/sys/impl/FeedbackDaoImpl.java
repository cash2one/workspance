/**
 * 
 */
package com.zz91.ep.dao.sys.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.sys.FeedbackDao;
import com.zz91.ep.domain.sys.Feedback;

/**
 * @author mays
 *
 */
@Component("feedbackDao")
public class FeedbackDaoImpl extends BaseDao implements FeedbackDao {

	final static String PREFIX="feedback";
	
	@Override
	public Integer insert(Feedback feedback) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(PREFIX, "insert"), feedback);
	}

}
