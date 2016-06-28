package com.zz91.crm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.SysAreaDao;
import com.zz91.crm.domain.SysArea;

@Repository("sysAreaDao")
public class SysAreaDaoImpl extends BaseDao implements SysAreaDao {

	final static String SQL_PREFIX = "sysArea";

	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> querySysAreaByCode(String code, Short type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("code", code);
		root.put("type", type);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySysAreaByCode"), root);
	}

	@Override
	public Integer insertSysArea(SysArea sysArea) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertSysArea"), sysArea);
	}

	@Override
	public String queryNameByCode(String code) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameByCode"), code);
	}

	@Override
	public String queryCodeByName(String name) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCodeByName"), name);
	}
	
}
