package com.ast.ast1949.persist.phone.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.LdbLevel;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.phone.LdbLevelDao;
@Component("ldbLevelDao")
public class LdbLevelDaoImpl extends BaseDaoSupport implements LdbLevelDao {
	
	final static String SQL_FIX = "ldbLevel";
	
	@Override
	public Integer insertLdbLevel(LdbLevel ldbLevel) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertLdbLevel"), ldbLevel);
	}

	@Override
	public LdbLevel queryLdbLevelByCompanyId(Integer companyId) {
		return (LdbLevel) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryLdbLevelByCompanyId"), companyId);
	}

	@Override
	public Integer updateLevelByCompanyId(LdbLevel ldbLevel) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("companyId", ldbLevel.getCompanyId());
        map.put("level", ldbLevel.getLevel());
        map.put("phoneCost", ldbLevel.getPhoneCost());
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateLevelByCompanyId"), map);
	}

}
