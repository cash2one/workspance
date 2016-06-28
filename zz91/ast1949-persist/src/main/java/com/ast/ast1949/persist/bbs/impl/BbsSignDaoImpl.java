package com.ast.ast1949.persist.bbs.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsSignDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsSignDao;

@Component("bbsSignDao")
public class BbsSignDaoImpl extends BaseDaoSupport implements BbsSignDao {
	final static String SQL_PREFIX = "bbsSign";

	@Override
	public BbsSignDO querySignByCompanyId(Integer companyId) {
		return (BbsSignDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "querySignByCompanyId"), companyId);
	}
}
