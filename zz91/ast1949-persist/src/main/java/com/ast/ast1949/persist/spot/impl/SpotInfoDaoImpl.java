package com.ast.ast1949.persist.spot.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotInfoDao;

@Component("spotInfoDao")
public class SpotInfoDaoImpl extends BaseDaoSupport implements SpotInfoDao {

	final static String SQL_FIX = "spotInfo";

	@Override
	public Integer insert(SpotInfo spotInfo) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), spotInfo);
	}

	@Override
	public SpotInfo queryOne(Integer id) {
		return (SpotInfo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryOne"), id);
	}

	@Override
	public Integer update(SpotInfo spotInfo) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), spotInfo);
	}

}
