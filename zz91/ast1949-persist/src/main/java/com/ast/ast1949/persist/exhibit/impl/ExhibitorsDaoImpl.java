package com.ast.ast1949.persist.exhibit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.exhibit.Exhibitors;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.exhibit.ExhibitorsDao;
@Component("exhibitorsDao")
public class ExhibitorsDaoImpl extends BaseDaoSupport implements ExhibitorsDao {
	final static String SQL_FIX = "exhibitors";
	@Override
	public Integer insert(Exhibitors exhibitors) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), exhibitors);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibitors> queryAllExhibitors(PageDto<Exhibitors> page,Exhibitors exhibitors) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("exhibitors", exhibitors);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllExhibitors"), map);
	}
	@Override
	public Integer queryAllExhibitorsCount(Exhibitors exhibitors) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("exhibitors", exhibitors);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryAllExhibitorsCount"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibitors> queryList(String type) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("type", type);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

}
