package com.ast.ast1949.persist.oauth.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.oauth.OauthAccessDao;

@Component("oauthAccessDao")
public class OauthAccessDaoImpl extends BaseDaoSupport implements OauthAccessDao{

	final static String SQL_FIX = "oauthAccess";
	@Override
	public Integer insert(OauthAccess oauthAccess) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),oauthAccess);
	}

	@Override
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("openType", openType);
		return (OauthAccess) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAccessByOpenIdAndType"), map);
	}

	@Override
	public Integer updateByOpenId(String openId, String targetAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("targetAccount", targetAccount);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateByOpenId"), map);
	}

	@Override
	public OauthAccess queryAccessByAccountAndType(String account,
			String openType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("openType", openType);
		return (OauthAccess) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAccessByAccountAndType"), map);
	}
	

}
