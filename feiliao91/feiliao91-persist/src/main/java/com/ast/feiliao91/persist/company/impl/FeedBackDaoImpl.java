package com.ast.feiliao91.persist.company.impl;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.FeedBack;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.FeedBackDao;
@Component("feedBackDao")
public class FeedBackDaoImpl extends BaseDaoSupport implements FeedBackDao{
	final static String SQL_PREFIX="feedback";
	@Override
	public Integer insert(FeedBack feedback) {
		
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"),feedback);
	}
	@Override
	public FeedBack selectById(Integer id) {
		return (FeedBack) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "selectById"), id);
	}

}
