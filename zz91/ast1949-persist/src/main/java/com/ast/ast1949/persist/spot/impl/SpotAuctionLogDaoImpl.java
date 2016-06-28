package com.ast.ast1949.persist.spot.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionLogDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotAuctionLogDao;

/**
 *	author:kongsj
 *	date:2013-3-15
 */
@Component("spotAuctionLogDao")
public class SpotAuctionLogDaoImpl extends BaseDaoSupport implements SpotAuctionLogDao{

	final static String SQL_FIX = "spotAuctionLog";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpotAuctionLog> queryByCondition(SpotAuctionLog spotAuctionLog,
			PageDto<SpotAuctionLogDto> page) {
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("spotAuctionLog", spotAuctionLog);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(SpotAuctionLog spotAuctionLog) {
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("spotAuctionLog", spotAuctionLog);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotAuctionLog> queryByAuctionIdAndSize(Integer auctionId,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auctionId", auctionId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByAuctionIdAndSize"), map);
	}

	@Override
	public Integer queryCountByAuctionId(Integer auctionId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByAuctionId"), auctionId);
	}

	@Override
	public Integer insert(SpotAuctionLog spotAuctionLog) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"),spotAuctionLog);
	}
	
	@Override
	public Integer queryCountByCompanyIdAndAuctionId(Integer companyId,Integer spotAuctionId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("spotAuctionId", spotAuctionId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCountByCompanyIdAndAuctionId"),map);
	}

}
