/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-30 下午04:25:30
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.dto.company.SubscribeDTO;
import com.ast.ast1949.dto.company.SubscribeForMyrcDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.SubscribeDAO;
import com.ast.ast1949.util.Assert;

/**
 * 
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Component("subscribeDAO")
public class SubscribeDAOImpl extends BaseDaoSupport implements SubscribeDAO {

	final static int DEFAULT_BATCH_SIZE = 20;
	// private SqlMapClientTemplate sqlMapClientTemplate =
	// getSqlMapClientTemplate();

	final static String SQL_PREFIX = "subscribe";

	public Integer deleteSubscribeById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update(
							"subscribe.deleteSubscribeById", entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.",
					e);
		}
		return impacted;
	}

	public Integer insertSubscribe(SubscribeDO subscribeDO) {
		return (Integer) getSqlMapClientTemplate().insert(
				"subscribe.insertSubscribe", subscribeDO);
	}

	@SuppressWarnings("unchecked")
	public List<SubscribeDO> querySubscribeByCompanyIdAndSubscribeType(
			Integer companyId, String subscribeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("subscribeType", subscribeType);
		return getSqlMapClientTemplate().queryForList(
				"subscribe.querySubscribeByCompanyIdAndSubscribeType", map);
	}

	public Integer deleteSubscribeByCompanyIdAndSubscribeType(
			Integer companyId, String subscribeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("subscribeType", subscribeType);
		return getSqlMapClientTemplate().delete(
				"subscribe.deleteSubscribeByCompanyIdAndSubscribeType", map);
	}

	public SubscribeDO selectSubscribeById(Integer id) {
		return (SubscribeDO) getSqlMapClientTemplate().queryForObject(
				"subscribe.selectSubscribeById", id);
	}

	public Integer updateSubscribe(SubscribeDO subscribeDO) {
		return getSqlMapClientTemplate().update("subscribe.updateSubscribe",
				subscribeDO);
	}

	@SuppressWarnings("unchecked")
	public List<SubscribeDTO> selectSubscribeByCondition(
			SubscribeDTO subscribeDto) {
		return getSqlMapClientTemplate().queryForList(
				"subscribe.selectSubscribeByCondition", subscribeDto);
	}

	public Integer selectCountSubscribeByCondition(SubscribeDTO subscribeDto) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"subscribe.selectCountSubscribeByCondition", subscribeDto);
	}

	public SubscribeDTO selectByIdSubscribe(Integer id) {
		return (SubscribeDTO) getSqlMapClientTemplate().queryForObject(
				"subscribe.selectByIdSubscribe", id);
	}

	public Integer updateByIdSubscribe(SubscribeDTO subscribeDto) {
		return getSqlMapClientTemplate().update(
				"subscribe.updateByIdSubscribe", subscribeDto);
	}

	public Integer deleteSubscribeById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete(
				"subscribe.deleteSubscribeById", id);
	}

	@Override
	public Integer countSubscribeForMycrByCondition(
			SubscribeForMyrcDTO subscribeForMyrcDTO) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"subscribe.countSubscribeForMycrByCondition",
				subscribeForMyrcDTO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubscribeForMyrcDTO> querySubscribeForMycrByCondition(
			SubscribeForMyrcDTO subscribeForMyrcDTO) {
		return getSqlMapClientTemplate().queryForList(
				"subscribe.querySubscribeForMycrByCondition",
				subscribeForMyrcDTO);
	}

	@Override
	public SubscribeDO queryKeywordsByAccount(String account) {

		return (SubscribeDO) getSqlMapClientTemplate().queryForObject(
				"subscribe.queryKeywordsByAccount", account);
	}

	@Override
	public SubscribeDO querySubscribeById(Integer id) {

		return (SubscribeDO) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "querySubscribeById"), id);
	}

	@Override
	public Integer querySubscribeByCompanyIdAndSubscribeTypeCount(
			Integer companyId, String subscribeType) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("companyId", companyId);
		root.put("subscribeType", subscribeType);
		return (Integer) getSqlMapClientTemplate()
				.queryForObject(
						addSqlKeyPreFix(SQL_PREFIX,
								"querySubscribeByCompanyIdAndSubscribeTypeCount"),
						root);
	}

}
