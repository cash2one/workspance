package com.ast.ast1949.persist.analysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisPpcLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisPpcLogDao;

@Component("analysisPpcLogDao")
public class AnalysisPpcDaoImpl extends BaseDaoSupport implements AnalysisPpcLogDao{

	final static String SQL_PREFIX = "analysisPpcLog";
	
	@Override
	public Integer queryAllPvByCid(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAllPvByCid"),cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisPpcLog> queryList(AnalysisPpcLog analysisPpcLog,
			PageDto<AnalysisPpcLog> page) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("analysisPpcLog", analysisPpcLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryList"),map);
	}

	@Override
	public Integer queryListCount(AnalysisPpcLog analysisPpcLog) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("analysisPpcLog", analysisPpcLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryListCount"),map);
	}

	@Override
	public Integer sumPvByTimeACid(AnalysisPpcLog analysisPpcLog) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("analysisPpcLog", analysisPpcLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "sumPvByTimeACid"),map);
	}

	
}
