/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.service.information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.WeeklyDTO;
import com.ast.ast1949.persist.information.WeeklyPageDAO;
import com.ast.ast1949.service.information.WeeklyPageService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("weeklyPageService")
public class WeeklyPageServiceImpl implements WeeklyPageService{

	@Autowired
	private WeeklyPageDAO weeklyPageDAO;
	
//	public List<WeeklyPageDO> listWeeklyPageByPage(PageDto page) {
//        Assert.notNull(page, "page is not null");
//		return weeklyPageDAO.listWeeklyPageByPage(page);
//	}

	public List<WeeklyPageDO> listWeeklyPageByPeriodicalId(Integer periodicalId) {
        Assert.notNull(periodicalId, "periodicalId is not null");
		return weeklyPageDAO.listWeeklyPageByPeriodicalId(periodicalId);
	}

	public Integer insertWeeklyPage(WeeklyPageDO weeklyPageDO) {
		Assert.notNull(weeklyPageDO, "weeklyPageDO is not null");
		return weeklyPageDAO.insertWeeklyPage(weeklyPageDO);
	}

	public Integer updateWeeklyPage(WeeklyPageDO weeklyPageDO) {
		Assert.notNull(weeklyPageDO, "weeklyPageDO is not null");
		return weeklyPageDAO.updateWeeklyPage(weeklyPageDO);
	}

	public WeeklyPageDO listWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return weeklyPageDAO.listWeeklyPageById(id);
	}

	public Integer batchDeleteWeeklyPageById(int[] entities) {
		Assert.notNull(entities, "entities is not null");
		return weeklyPageDAO.deleteWeeklyPageById(entities);
	}

	public WeeklyPageDO listDownWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return weeklyPageDAO.listDownWeeklyPageById(id);
	}

	public WeeklyPageDO listOnWeeklyPageById(Integer id) {
		Assert.notNull(id, "id is not null");
		return weeklyPageDAO.listOnWeeklyPageById(id);
	}

	public WeeklyDTO listPerdicalAndPageById(Integer pageId) {
		Assert.notNull(pageId, "pageId is not null");
		return weeklyPageDAO.listPerdicalAndPageById(pageId);
	}

}
