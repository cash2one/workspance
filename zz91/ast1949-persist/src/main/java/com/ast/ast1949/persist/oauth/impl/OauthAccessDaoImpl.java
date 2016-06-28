package com.ast.ast1949.persist.oauth.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.oauth.OauthAccessDao;
import com.zz91.util.datetime.DateUtil;

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
	public Integer countAccessByOpenId(String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("from", DateUtil.toString(new Date(), "yyyy-MM-dd"));
		map.put("to", DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd"));
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countAccessByOpenId"), map);
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
	
	@Override
	public Integer queryByWXCode(String code,String gmtLimit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("gmtLimit", gmtLimit);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByWXCode"), map);
	}

	@Override
	public Integer updateWXTargetAccountById(Integer id, String targetAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("targetAccount", targetAccount);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateWXTargetAccountById"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OauthAccess> queryTargetAccount(PageDto<CompanyDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryTargetAccount"), map);
	}
	@Override
	public Integer queryTargetAccountCount(){
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryTargetAccountCount"));
	}

	@Override
	public void deleteByTargetAccount(String targetAccount) {
		getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "deleteByTargetAccount"), targetAccount);
	}
}
