package com.ast.ast1949.persist.exhibit.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.exhibit.PreviouExhibitorsDao;
@Component("previouExhibitorsDao")
public class PreviouExhibitorsDaoImpl extends BaseDaoSupport implements PreviouExhibitorsDao{
	final static String SQL_FIX = "previouExhibitors";
	@Override
	public Integer insert(PreviouExhibitors previouExhibitors) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), previouExhibitors);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PreviouExhibitors> queryList(PageDto<PreviouExhibitors>page, PreviouExhibitors previouExhibitors){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("previouExhibitors", previouExhibitors);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}
	@Override
	public Integer queryListCount(PreviouExhibitors previouExhibitors){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("previouExhibitors", previouExhibitors);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryListCount"),map);
	}
	@Override
	public PreviouExhibitors queryPreviouExhibitorsById(Integer id) {
		return (PreviouExhibitors) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPreviouExhibitorsById"), id);
	}
	@Override
	public Integer updatePreviouExhibitors(PreviouExhibitors previouExhibitors) {
		
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updatePreviouExhibitors"), previouExhibitors);
	}
	@Override
	public Integer delPreviouExhibitors(Integer id){
		return (Integer) getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "delPreviouExhibitors"),id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PreviouExhibitors> queryAllList(PreviouExhibitors previouExhibitors) {
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("previouExhibit", previouExhibitors);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllList"), map);
		
	}

}
