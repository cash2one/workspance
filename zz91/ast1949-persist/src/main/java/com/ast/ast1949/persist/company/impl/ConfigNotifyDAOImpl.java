package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.ConfigNotifyDO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.ConfigNotifyDAO;

@Component("configNotifyDAO")
public class ConfigNotifyDAOImpl extends BaseDaoSupport implements ConfigNotifyDAO{
	
	final static String SQL_PREFIX = "configNotify";

	@Override
	public Integer insertConfigNotify(Integer companyId, String notifyCode,
			Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("notifyCode", notifyCode);
		root.put("status", status);
		return (Integer)getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertConfigNotify"),root);
	}

	@Override
	public Integer updateConfigNotifyForSend(Integer companyId,
			String notifyCode, Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("notifyCode", notifyCode);
		root.put("status", status);
		return (Integer)getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateConfigNotifyForSend"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigNotifyDO> selectConfigNotify(Integer companyId,String notifyCode,Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("notifyCode", notifyCode);
		root.put("status", status);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "selectConfigNotify"),root);
	}

	@Override
	public Integer countConfigByCode(String notifyCode, Integer companyId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("notifyCode", notifyCode);
		root.put("companyId", companyId);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countConfigByCode"),root);
	}

	@Override
	public Integer deleteConfigByCode(String notifyCode, Integer companyId,Integer status) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("notifyCode", notifyCode);
		root.put("companyId", companyId);
		root.put("status", status);
		return (Integer)getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "deleteConfigByCode"),root);
	}

}
