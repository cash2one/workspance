/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.persist.information.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.information.WeeklyPageDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("weeklyPageDAO")
public class WeeklyPageDAOImpl extends SqlMapClientDaoSupport implements WeeklyPageDAO{
	final private int DEFAULT_BATCH_SIZE = 20;
//	@SuppressWarnings("unchecked")
//	public List<WeeklyPageDO> listWeeklyPageByPage(PageDto page) {
//		Assert.notNull(page, "page is not null");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("page", page);
//		return getSqlMapClientTemplate().queryForList("weeklyPage.listWeeklyPageByPage", map);
//	}

	@SuppressWarnings("unchecked")
	public List<WeeklyPageDO> listWeeklyPageByPeriodicalId(Integer periodicalId) {
		Assert.notNull(periodicalId, "periodicalId is not null");
		return getSqlMapClientTemplate().queryForList("weeklyPage.listWeeklyPageByPeriodicalId", periodicalId);
	}

	public Integer insertWeeklyPage(WeeklyPageDO weeklyPageDO) {
		Assert.notNull(weeklyPageDO, "weeklyPageDO is not null");
		return (Integer) getSqlMapClientTemplate().insert("weeklyPage.insertWeeklyPage", weeklyPageDO);
	}

	public Integer updateWeeklyPage(WeeklyPageDO weeklyPageDO) {
       Assert.notNull(weeklyPageDO, "weeklyPageDO is not null");
		return getSqlMapClientTemplate().update("weeklyPage.updateWeeklyPage", weeklyPageDO);
	}

	public WeeklyPageDO listWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPageDO) getSqlMapClientTemplate().queryForObject("weeklyPage.listWeeklyPageById", id);
	}
	public Integer deleteWeeklyPageById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update("weeklyPage.deleteWeeklyPageById",
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;
}

	public WeeklyPageDO listDownWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPageDO) getSqlMapClientTemplate().queryForObject("weeklyPage.listDownWeeklyPageById", id);
	}

	public WeeklyPageDO listOnWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPageDO) getSqlMapClientTemplate().queryForObject("weeklyPage.listOnWeeklyPageById", id);
	}

	public WeeklyDTO listPerdicalAndPageById(Integer pageId) {
		Assert.notNull(pageId, "pageId is not null");
		return (WeeklyDTO) getSqlMapClientTemplate().queryForObject("weeklyPage.listPerdicalAndPageById", pageId);
	}
}
