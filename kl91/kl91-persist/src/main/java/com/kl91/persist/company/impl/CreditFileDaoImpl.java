package com.kl91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kl91.domain.company.CreditFile;
import com.kl91.domain.dto.company.CreditFileSearchDto;
import com.kl91.persist.BaseDaoSupport;
import com.kl91.persist.company.CreditFileDao;

@Component("creditFileDao")
public class CreditFileDaoImpl extends BaseDaoSupport implements CreditFileDao {

	private static String SQL_PREFIX = "creditFile";

	@Override
	public Integer insert(CreditFile creditFile) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_PREFIX, "insert"), creditFile);
	}

	@Override
	public Integer update(CreditFile creditFile) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_PREFIX, "update"), creditFile);
	}

	@Override
	public Integer delete(Integer id) {
		return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"), id);
	}

	@Override
	public CreditFile queryById(Integer id) {
		return (CreditFile) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditFile> queryFile(CreditFileSearchDto searchDto) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryFile"), map);
	}

}
