package com.ast.ast1949.persist.company.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmCsLogAdded;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmCsLogAddDao;

@Component("crmCsLogAddDao")
public class CrmCsLogAddDaoImpl extends BaseDaoSupport implements
		CrmCsLogAddDao {

	private static String SQL_PREFIX = "crmCsLogAdd";

	@Override
	public Integer createAdded(CrmCsLogAdded added) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "createAdded"), added);
	}

	@Override
	public List<CrmCsLogAdded> queryAddedByLog(Integer logId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAddedByLog"), logId);
	}

}
