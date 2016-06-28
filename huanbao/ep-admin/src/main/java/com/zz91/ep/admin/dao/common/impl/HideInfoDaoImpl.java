package com.zz91.ep.admin.dao.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.common.HideInfoDao;
import com.zz91.ep.domain.common.HideInfo;
@Repository("HideInfoDao")
public class HideInfoDaoImpl extends BaseDao implements HideInfoDao {

	final static String SQL_PREFIX="hideInfo";
	
	@Override
	public Integer delete(Integer targetId,String targetType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "delete"), root);
	}

	@Override
	public Integer insert(HideInfo hideInfo) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insert"), hideInfo);
	}

	@Override
	public HideInfo queryHideInfoByIdAndType(Integer targetId, String targetType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", targetId);
		map.put("targetType", targetType);
		return (HideInfo) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryHideInfoByIdandTargeId"),map);
	}

	@Override
	public Integer querycount(Integer targetId,String targetType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("targetId", targetId);
		root.put("targetType", targetType);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCount"),root);
	}

	@Override
	public Integer update(HideInfo hideInfo) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "update"), hideInfo);
	}

}
