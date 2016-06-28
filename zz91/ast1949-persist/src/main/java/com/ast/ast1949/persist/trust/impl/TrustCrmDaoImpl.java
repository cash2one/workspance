package com.ast.ast1949.persist.trust.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustCrm;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCrmDto;
import com.ast.ast1949.dto.trust.TrustCrmSearchDto;
import com.ast.ast1949.persist.BaseDaoSupportMultipleDataSource;
import com.ast.ast1949.persist.trust.TrustCrmDao;

@Component("trustCrmDao")
public class TrustCrmDaoImpl extends BaseDaoSupportMultipleDataSource implements TrustCrmDao {

	final static String SQL_FIX = "trustCrm";

	@Override
	public TrustCrm queryById(Integer id) {
		try {
			return (TrustCrm) getSqlMapClient2().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
		} catch (SQLException e) {
		}
		return null;
	}

	@Override
	public TrustCrm queryByCompanyId(Integer companyId) {
		try {
			return (TrustCrm) getSqlMapClient2().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByCompanyId"),
					companyId);
		} catch (SQLException e) {
		}
		return null;
	}

	@Override
	public Integer insert(TrustCrm trustCrm) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), trustCrm);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustCrm> queryByCondition(TrustCrmSearchDto searchDto, PageDto<TrustCrmDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		try {
			return getSqlMapClient2().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<TrustCrm>();
	}

	@Override
	public Integer queryCountByCondition(TrustCrmSearchDto searchDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		try {
			return (Integer) getSqlMapClient2().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
		} catch (SQLException e) {
		}
		return null;
	}

	@Override
	public Integer updateStar(Integer companyId, Integer star) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("star", star);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateStar"), map);
	}

	@Override
	public Integer updateContact(Integer companyId, Date gmtNextVisit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("gmtNextVisit", gmtNextVisit);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateContact"), map);
	}

	@Override
	public Integer updateStatus(Integer companyId, Integer isPublic, Integer isRubbish) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("isPublic", isPublic);
		map.put("isRubbish", isRubbish);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateStatus"), map);
	}

	@Override
	public Integer updateTrustAccount(Integer companyId, String crmAccount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("crmAccount", crmAccount);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateTrustAccount"), map);
	}

	@Override
	public Integer selectDayLog(Map<String, Object> map) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "selectDayLog"), map);
	}

}
