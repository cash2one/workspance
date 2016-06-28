package com.ast.ast1949.persist.log.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.log.LogOperationDao;

@Component("logOperationDao")
public class LogOperationDaoImpl extends BaseDaoSupport implements
		LogOperationDao {
	final static String SQL_FIX = "logOperation";

	@Override
	public Integer insert(LogOperation logOperation) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), logOperation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogOperation> queryByTargetIdAndOperation(Integer id,
			String operation, PageDto<LogOperation> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", id);
		map.put("operation", operation);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryByTargetIdAndOperation"), map);
	}

	@Override
	public Integer queryCountByTargetIdAndOperation(Integer id, String operation) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetId", id);
		map.put("operation", operation);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryCountByTargetIdAndOperation"),
				map);
	}

	@Override
	public String queryRemarskByCompanyId(Integer companyId) {
		String str = (String) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryRemarkByCompanyId"), companyId);
		return str;
	}

	@Override
	public String queryRemarkByCId(Integer cid) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryRemarkByCId"), cid);
	}

}
