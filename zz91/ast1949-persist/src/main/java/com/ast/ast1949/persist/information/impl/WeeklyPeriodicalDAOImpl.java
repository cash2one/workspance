/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.persist.information.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyPeriodicalDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.information.WeeklyPeriodicalDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("weeklyPeriodicalDAO")
public class WeeklyPeriodicalDAOImpl extends SqlMapClientDaoSupport implements WeeklyPeriodicalDAO {
	final private int DEFAULT_BATCH_SIZE = 20;
	@SuppressWarnings("unchecked")
	public List<WeeklyPeriodicalDO> listWeeklyPeriodicalByPage(PageDto page) {
		Assert.notNull(page, "page is not null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				"weeklyPeriodical.listWeeklyPeriodicalByPage", map);
	}

	public Integer countWeeklyPeriodical() {
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject("weeklyPeriodical.countWeeklyPeriodical").toString());
	}

	public Integer insertWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO) {
        Assert.notNull(weeklyPeriodicalDO, "weeklyPeriodicalDO is not null");
		return (Integer) getSqlMapClientTemplate().insert("weeklyPeriodical.insertWeeklyPeriodical", weeklyPeriodicalDO);
	}

	public Integer updateWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO) {
        Assert.notNull(weeklyPeriodicalDO, "weeklyPeriodicalDO is not null");
		return getSqlMapClientTemplate().update("weeklyPeriodical.updateWeeklyPeriodical", weeklyPeriodicalDO);
	}

	public WeeklyPeriodicalDO listWeeklyPeriodicalById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPeriodicalDO) getSqlMapClientTemplate().queryForObject("weeklyPeriodical.listWeeklyPeriodicalById", id);
	}

	@SuppressWarnings("unchecked")
	public List<WeeklyPeriodicalDO> listAllWeeklyPeriodical() {
		return getSqlMapClientTemplate().queryForList("weeklyPeriodical.listAllWeeklyPeriodical");
	}

	public Integer deleteWeeklyPeriodicalById(int[] entities) {
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
						impacted += getSqlMapClientTemplate().update("weeklyPeriodical.deleteWeeklyPeriodicalById",
								entities[i]);
					}
					getSqlMapClient().executeBatch();
				}
			} catch (Exception e) {
				throw new PersistLayerException("batch check friend links failed.", e);
			}
			return impacted;
	}

	public WeeklyPeriodicalDO listFirstWeeklyPeriodical() {
		return (WeeklyPeriodicalDO) getSqlMapClientTemplate().queryForObject("weeklyPeriodical.listFirstWeeklyPeriodical");
	}

	public WeeklyPeriodicalDO listOnWeeklyPeriodical(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPeriodicalDO) getSqlMapClientTemplate().queryForObject("weeklyPeriodical.listOnWeeklyPeriodical", id);
	}

	public WeeklyPeriodicalDO listDownWeeklyPeriodical(Integer id) {
		Assert.notNull(id, "id is not null");
		return (WeeklyPeriodicalDO) getSqlMapClientTemplate().queryForObject("weeklyPeriodical.listDownWeeklyPeriodical", id);
	}

}
