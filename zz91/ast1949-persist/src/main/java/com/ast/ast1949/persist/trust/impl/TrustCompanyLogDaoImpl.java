package com.ast.ast1949.persist.trust.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustCsLogDto;
import com.ast.ast1949.dto.trust.TrustCsLogSearchDto;
import com.ast.ast1949.persist.BaseDaoSupportMultipleDataSource;
import com.ast.ast1949.persist.trust.TrustCompanyLogDao;

@Component("trustCompanyLogDao")
public class TrustCompanyLogDaoImpl extends BaseDaoSupportMultipleDataSource implements TrustCompanyLogDao{

	final static String  SQL_FIX = "trustCompanyLog";
	
	@Override
	public TrustCompanyLog queryById(Integer id) {
		return (TrustCompanyLog) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer insert(TrustCompanyLog trustCompanyLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), trustCompanyLog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustCompanyLog> queryByCondition(TrustCompanyLog trustCompanyLog, PageDto<TrustCompanyLog> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustCompanyLog", trustCompanyLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(TrustCompanyLog trustCompanyLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trustCompanyLog", trustCompanyLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@Override
	public Integer countByCompanyIdForOneMonth(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyIdForOneMonth"),companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrustCompanyLog> queryByConditionByAdmin(TrustCsLogSearchDto searchDto, PageDto<TrustCsLogDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByConditionByAdmin"), map);
	}

	@Override
	public Integer queryCountByConditionByAdmin(TrustCsLogSearchDto searchDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByConditionByAdmin"), map);
	}

	@Override
	public Integer queryRecentStar(Integer companyId, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("id", id);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryRecentStar"), map);
	}

}
