package com.ast.ast1949.persist.spot.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotTrustDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotTrustDao;

/**
 * author:kongsj date:2013-5-20
 */
@Component("spotTrustDao")
public class SpotTrustDaoImpl extends BaseDaoSupport implements SpotTrustDao {

	final static String SQL_FIX = "spotTrust";

	@Override
	public Integer insert(SpotTrust spotTrust) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), spotTrust);
	}

	@Override
	public SpotTrust queryById(Integer id) {
		return (SpotTrust) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotTrust> queryList(SpotTrustDto spotTrustDto,
			PageDto<SpotTrustDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotTrust", spotTrustDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}

	@Override
	public Integer queryListCount(SpotTrustDto spotTrustDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotTrust", spotTrustDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryListCount"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotTrust> queryListForFront(Integer start,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryListForFront"), map);
	}

	@Override
	public Integer update(SpotTrust spotTrust) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), spotTrust);
	}
	
	@Override
	public Integer updateForChecked(String isChecked,Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isChecked",isChecked);
		map.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateForChecked"), map);
	}
	
	@Override
	public Integer updateForDelete(String isDelete,Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete",isDelete);
		map.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateForDelete"), map);
	}

}
