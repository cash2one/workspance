package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.OauthAccessDao;
import com.zz91.ep.domain.comp.OauthAccess;

@Component("oauthAccessDao")
public class OauthAccessDaoImpl extends BaseDao implements OauthAccessDao{

	final static String SQL_FIX = "oauthAccess";
	@Override
	public Integer insert(OauthAccess oauthAccess) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_FIX, "insert"),oauthAccess);
	}

	@Override
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("openType", openType);
		return (OauthAccess) getSqlMapClientTemplate().queryForObject(buildId(SQL_FIX, "queryAccessByOpenIdAndType"), map);
	}

	@Override
	public Integer updateByOpenId(String openId, String targetAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("targetAccount", targetAccount);
		return getSqlMapClientTemplate().update(buildId(SQL_FIX, "updateByOpenId"), map);
	}

	@Override
	public OauthAccess queryAccessByAccountAndType(String account,
			String openType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("openType", openType);
		return (OauthAccess) getSqlMapClientTemplate().queryForObject(buildId(SQL_FIX, "queryAccessByAccountAndType"), map);
	}
	

}
