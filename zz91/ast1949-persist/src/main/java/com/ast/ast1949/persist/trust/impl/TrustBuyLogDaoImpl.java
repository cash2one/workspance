package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.trust.TrustBuyLogDao;

@Component("trustBuyLogDao")
public class TrustBuyLogDaoImpl extends BaseDaoSupport implements
		TrustBuyLogDao {

	final static String SQL_FIX = "trustBuyLog";

	@Override
	public TrustBuyLog queryById(Integer id) {
		return (TrustBuyLog) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer insert(TrustBuyLog trustBuyLog) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), trustBuyLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustBuyLog> queryByCondition(TrustBuyLog trustBuyLog,
			PageDto<TrustBuyLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("trustBuyLog", trustBuyLog);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(TrustBuyLog trustBuyLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustBuyLog", trustBuyLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@Override
	public Integer countByCompanyIdForOneMonth(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyIdForOneMonth"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustBuyLog> queryByConditionByAdmin(TrustCsLogSearchDto searchDto, PageDto<TrustCsLogDto> page) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByConditionByAdmin"), map);
	}

	@Override
	public Integer queryCountByConditionByAdmin(TrustCsLogSearchDto searchDto) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByConditionByAdmin"), map);
	}

	@Override
	public Integer queryRecentStar(Integer buyId, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("buyId", buyId);
		map.put("id", id);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryRecentStar"), map);
	}
}
