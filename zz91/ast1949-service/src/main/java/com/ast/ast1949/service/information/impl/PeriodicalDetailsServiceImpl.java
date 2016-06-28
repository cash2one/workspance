/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.service.information.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.information.PeriodicalDetailsDAO;
import com.ast.ast1949.service.information.PeriodicalDetailsService;
import com.ast.ast1949.util.Assert;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("periodicalDetailsService")
public class PeriodicalDetailsServiceImpl implements PeriodicalDetailsService {

	@Autowired
	private PeriodicalDetailsDAO periodicalDetailsDAO;

	public Integer deleteDetails(Integer[] detailsIdArray) {
		Assert.notNull(detailsIdArray, "the detailsIdArray must not be null");
		Integer i=periodicalDetailsDAO.deleteDetails(detailsIdArray);
		return i;
	}

	public List<PeriodicalDetails> listPreviewDetailsByPeriodicalId(
			Integer periodicalId) {
		Assert.notNull(periodicalId, "the periodicalId must not be null");
		return periodicalDetailsDAO.listPreviewDetailsByPeriodicalId(periodicalId);
	}

	public PageDto pageDetailsByPeriocidalId(
			Integer periodicalId, PageDto page) {
		Assert.notNull(periodicalId, "the periodicalId must not be null");
		Assert.notNull(page, "the page must not be null");
		page.setTotalRecords(periodicalDetailsDAO.countPageDetailsByPeriodicalId(periodicalId));
		page.setRecords(periodicalDetailsDAO.pageDetailsByPeriodicalId(periodicalId, page));
		return page;
	}

	public Integer updateBaseDetails(PeriodicalDetails details) {
		Assert.notNull(details, "the details must not be null");
		return periodicalDetailsDAO.updateBaseDetails(details);
	}

	public Integer updatePageType(Integer id, Integer pageType) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(pageType, "the pageType must not be null");
		return periodicalDetailsDAO.updatePageType(id, pageType);
	}

	public List<PeriodicalDetails> pageDetailsByPeriodicalId(Integer periodicalId, PageDto page) {
        Assert.notNull(page, "page is not null");
        Assert.notNull(periodicalId, "periodicalId is not null");
		return periodicalDetailsDAO.pageDetailsByPeriodicalId(periodicalId, page);
	}

	public Integer countPageDetailsByPeriodicalId(Integer periodicalId) {
        Assert.notNull(periodicalId, "periodicalId is not null");
		return periodicalDetailsDAO.countPageDetailsByPeriodicalId(periodicalId);
	}

	public PeriodicalDetails selectDetailsById(Integer id) {
		Assert.notNull(id, "id is not null");
		return periodicalDetailsDAO.selectDetailsById(id);
	}
	public List<PeriodicalDetails> pageDetailsByPeriodicalIdAndType(Integer periodicalId,
			Integer type) {
		Assert.notNull(type, "type is not null");
		Assert.notNull(periodicalId, "periodicalId is not null");
		return periodicalDetailsDAO.pageDetailsByPeriodicalIdAndType(periodicalId, type);
	}

}
