package com.ast.feiliao91.persist.trade.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.trade.YfbLog;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.trade.YfbLogDao;

@Component
public class YfbLogDaoImpl extends BaseDaoSupport implements YfbLogDao {
	final static String SQL_FIX = "yfbLog";

	@Override
	public Integer insert(YfbLog yfbLog) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), yfbLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YfbLog> queryByCompanyId(Integer companyId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"), companyId);
	}

	@Override
	public Integer update(String payOrder) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"),payOrder);
	}

}
