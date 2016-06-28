package com.ast.ast1949.persist.sample.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.sample.SampleMsgset;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.sample.SampleMsgsetDao;

@Component("sampleMsgsetDao")
public class SampleMsgsetDaoImpl extends BaseDaoSupport implements SampleMsgsetDao {

	final static String SQL_FIX = "sampleMsgset";

	@Override
	public SampleMsgset queryById(Integer id) {
		return (SampleMsgset) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public SampleMsgset queryByCompanyId(Integer companyId) {
		return (SampleMsgset) getSqlMapClientTemplate().queryForObject(	addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

	@Override
	public Integer insert(SampleMsgset sampleMsgset) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), sampleMsgset);
	}

	@Override
	public Integer update(SampleMsgset sampleMsgset) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), sampleMsgset);
	}

}