package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CrmOutLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmOutLogDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CrmOutLogDao;

/**
 *	author:kongsj
 *	date:2013-5-17
 */
@Component("crmOutLogDao")
public class CrmOutLogDaoImpl extends BaseDaoSupport implements CrmOutLogDao{
	
	final static String SQL_FIX="crmOutLog";
	
	@Override
	public Integer insert(CrmOutLog crmOutLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), crmOutLog);
	}

	@Override
	public CrmOutLog queryById(Integer id) {
		return (CrmOutLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CrmOutLogDto> queryDtoList(CrmOutLogDto crmOutLogDto,PageDto<CrmOutLogDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crmOutLog", crmOutLogDto);
		map.put("page",page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX,"queryDtoList"), map);
	}
	
	@Override
	public Integer queryDtoCount(CrmOutLogDto crmOutLogDto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crmOutLog", crmOutLogDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX,"queryDtoCount"), map);
	}

	@Override
	public CrmOutLog queryCrmOutLogByCompanyId(Integer companyId) {
		return (CrmOutLog) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCrmOutLogByCompanyId"), companyId);
	}

}
