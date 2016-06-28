package com.ast.ast1949.persist.spot.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotAuctionDao;

/**
 *	author:kongsj
 *	date:2013-3-12
 */
@Component("spotAuctionDao")
public class SpotAuctionDaoImpl extends BaseDaoSupport implements SpotAuctionDao{

	final static String SQL_FIX = "spotAuction";
	
	@Override
	public Integer insert(SpotAuction spotAuction) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), spotAuction);
	}

	@Override
	public SpotAuction queryBySpotId(Integer spotId) {
		return (SpotAuction) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryBySpotId"), spotId);
	}

	@Override
	public Integer update(SpotAuction spotAuction) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "update"), spotAuction);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpotAuction> queryByCondition(SpotAuction spotAuction,
			PageDto<SpotAuctionDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotAuction", spotAuction);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}
	
	@Override
	public Integer queryCountByCondition(SpotAuction spotAuction) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotAuction", spotAuction);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@Override
	public Integer updateStatusById(Integer id, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("checkStatus", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateStatusById"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotAuction> queryAuctionBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAuctionBySize"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotAuction> queryExpiredAuctionBySize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryExpiredAuctionBySize"), size);
	}

	@Override
	public SpotAuction queryById(Integer id) {
		return (SpotAuction) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

}
