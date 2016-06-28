package com.ast.ast1949.persist.spot.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.spot.SpotOrderDao;

/**
 * author:kongsj date:2013-3-25
 */
@Component("spotOrderDao")
public class SpotOrderDaoImpl extends BaseDaoSupport implements SpotOrderDao {

	final static Integer DEFAULT_BATCH_SIZE = 20;
	final static String SQL_FIX = "spotOrder";

	@Override
	public Integer deleteOrderById(Integer id, Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "deleteOrderById"), map);
	}

	public Integer batchDelete(Integer[] ids, Integer companyId) {
		int impacted = 0;
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					deleteOrderById(ids[currentBatch], companyId);
					impacted++;
				}
				getSqlMapClient().executeBatch();
			}
		} catch (SQLException e) {
			return 0;
		}
		return impacted;
	}

	@Override
	public Integer insert(SpotOrder spotOrder) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insert"), spotOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotOrder> queryByCondition(SpotOrder spotOrder,
			PageDto<SpotOrderDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("spotOrder", spotOrder);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryByCondition"), map);
	}

	@Override
	public Integer queryCountByCondition(SpotOrder spotOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spotOrder", spotOrder);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryCountByCondition"), map);
	}

	@Override
	public SpotOrder queryById(Integer id) {
		return (SpotOrder) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryById"), id);
	}

	@Override
	public Integer updateByStatusAndId(String orderStatus, Integer id,
			Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("orderStatus", orderStatus);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateByStatusAndId"), map);
	}
	
	@Override
	public Integer batchUpdateByStatusAndId(String orderStatus, Integer[] ids,
			Integer companyId){
		int impacted = 0;
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					updateByStatusAndId(orderStatus, ids[currentBatch], companyId);
					impacted++;
				}
				getSqlMapClient().executeBatch();
			}
		} catch (SQLException e) {
			return 0;
		}
		return impacted;
	}

	@Override
	public Integer validateCart(Integer companyId, Integer spotId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("spotId", spotId);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "validateCart"), map);
	}

	@Override
	public Integer countBySpotId(Integer spotId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countBySpotId"), spotId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpotOrder> queryOrder(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryOrder"), size);
	}

}