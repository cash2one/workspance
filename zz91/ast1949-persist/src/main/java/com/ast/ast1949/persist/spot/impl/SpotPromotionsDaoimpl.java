package com.ast.ast1949.persist.spot.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotPromotionsDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotPromotionsDao;

/**
 * author:kongsj date:2013-3-6
 */
@Component("spotPromotionsDao")
public class SpotPromotionsDaoimpl extends BaseDaoSupport implements
		SpotPromotionsDao {

	final static String SQL_FIX = "spotPromotions";

	@Override
	public Integer insert(SpotPromotions spotPromotions) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), spotPromotions);
	}

	@Override
	public SpotPromotions queryBySpotId(Integer spotId) {
		return (SpotPromotions) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryBySpotId"), spotId);
	}

	@Override
	public Integer update(SpotPromotions spotPromotions) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "update"), spotPromotions);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpotPromotions> queryByCondition(SpotPromotions spotPromotions, PageDto<SpotPromotionsDto> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotPromotions", spotPromotions);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}
	
	@Override
	public Integer queryCountByCondition(SpotPromotions spotPromotions){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotPromotions", spotPromotions);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotPromotions> queryPromotionsBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryPromotionsBySize"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotPromotions> queryExpiredPromotionsBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryExpiredPromotionsBySize"), size);
	}

	@Override
	public SpotPromotions queryByIdAndCompanyId(Integer id, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("companyId", companyId);
		return (SpotPromotions) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByIdAndCompanyId"), map);
	}

}
