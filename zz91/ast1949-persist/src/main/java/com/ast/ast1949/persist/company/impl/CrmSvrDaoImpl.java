package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmSvr;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmSvrDao;

@Component("crmSvrDao")
public class CrmSvrDaoImpl extends BaseDaoSupport implements CrmSvrDao {

	private static String SQL_PREFIX = "crmSvr";
	@Override
	public List<CrmSvr> querySvr() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySvr"));
	}
	
	@Override
	public List<CrmSvr> queryLdbSvr() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryLdbSvr"));
	}
	
	@Override
	public List<CrmSvr> querySvrByGroup(String group) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySvrByGroup"), group);
	}

	@Override
	public Map<String, String> queryMembershipOfSvr(String group) {
		return getSqlMapClientTemplate().queryForMap(addSqlKeyPreFix(SQL_PREFIX, "queryMembershipOfSvr"), group, "key", "value");
	}

	@Override
	public String queryApi(String svrCode, String apiName) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("svrCode", svrCode);
		root.put("apiName", apiName+"_api");
		
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryApi"), root);
	}

}
