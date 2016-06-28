package com.ast.ast1949.persist.analysis.impl;

import org.springframework.stereotype.Component;

import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisVipPvDao;

@Component("AnalysisVipPvDao")
public class AnalysisVipPvDaoImpl extends BaseDaoSupport implements AnalysisVipPvDao {
	final static String SQL_PREFIX = "analysisVipPv";

	public Integer queryAllPvByCid(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAllPvByCid"), cid);
	} 
		
	
}
