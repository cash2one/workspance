package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAccessView;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyAccessViewDao;

@Component("companyAccessViewDao")
public class CompanyAccessViewDaoImpl extends BaseDaoSupport implements CompanyAccessViewDao{

	final static String SQL_FIX = "companyAccessView";
	
	@Override
	public Integer insert(CompanyAccessView companyAccessView) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), companyAccessView);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAccessView> queryByCondition(Integer companyId,
			Integer targetId, String account, String gmtTarget, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		map.put("account", account);
		map.put("gmtTarget", gmtTarget);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(Integer companyId, Integer targetId,
			String account, String gmtTarget) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("targetId", targetId);
		map.put("account", account);
		map.put("gmtTarget", gmtTarget);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}
	
}
