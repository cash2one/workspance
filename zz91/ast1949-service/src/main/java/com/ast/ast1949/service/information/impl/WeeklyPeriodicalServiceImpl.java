/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.service.information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyPeriodicalDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.information.WeeklyPeriodicalDAO;
import com.ast.ast1949.service.information.WeeklyPeriodicalService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("weeklyPeriodicalService")
public class WeeklyPeriodicalServiceImpl implements WeeklyPeriodicalService{

	@Autowired
	private WeeklyPeriodicalDAO weeklyPeriodicalDAO;
	
	public List<WeeklyPeriodicalDO> listWeeklyPeriodicalByPage(PageDto page) {
        Assert.notNull(page, "page is not null");
		return weeklyPeriodicalDAO.listWeeklyPeriodicalByPage(page);
	}

	public Integer countWeeklyPeriodical() {
		return weeklyPeriodicalDAO.countWeeklyPeriodical();
	}

	public Integer insertWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO) {
        Assert.notNull(weeklyPeriodicalDO, "weeklyPeriodicalDO is not null");
		return weeklyPeriodicalDAO.insertWeeklyPeriodical(weeklyPeriodicalDO);
	}

	public Integer updateWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO) {
       Assert.notNull(weeklyPeriodicalDO, "weeklyPeriodicalDO is not null");
		return weeklyPeriodicalDAO.updateWeeklyPeriodical(weeklyPeriodicalDO);
	}

	public WeeklyPeriodicalDO listWeeklyPeriodicalById(Integer id) {
		Assert.notNull(id, "id is not null");
		return weeklyPeriodicalDAO.listWeeklyPeriodicalById(id);
	}

	public List<WeeklyPeriodicalDO> listAllWeeklyPeriodical() {
		return weeklyPeriodicalDAO.listAllWeeklyPeriodical();
	}

	public Integer batchDeleteWeeklyPeriodical(int[] entities) {
		Assert.notNull(entities, "entities is not null");
		return weeklyPeriodicalDAO.deleteWeeklyPeriodicalById(entities);
	}

	public WeeklyPeriodicalDO listFirstWeeklyPeriodical() {
		return weeklyPeriodicalDAO.listFirstWeeklyPeriodical();
	}

	public WeeklyPeriodicalDO listDownWeeklyPeriodical(Integer id) {
        Assert.notNull(id, "id is not null");  
		return weeklyPeriodicalDAO.listDownWeeklyPeriodical(id);
	}


	public WeeklyPeriodicalDO listOnWeeklyPeriodical(Integer id) {
		Assert.notNull(id, "id is not null");
		return weeklyPeriodicalDAO.listOnWeeklyPeriodical(id);
	}

}
