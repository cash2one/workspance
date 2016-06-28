/**
 * Copyright 2010 ASTO
 * All right reserved
 *	Created on 2010-11-12 by luocheng
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.bbs.ReportsDAO;
import com.ast.ast1949.service.bbs.ReportsService;
import com.ast.ast1949.util.Assert;

/**
 * @author luocheng
 * 
 */
@Component("reportsService")
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private ReportsDAO reportsDAO;

	final static String DEFAULT_CHECKSTATE = "0";
	final static String DEFAULT_REPORT_TYPE = "0";

	public Integer insertReportsDO(ReportsDO reportsDO) {
		Assert.notNull(reportsDO, "The reportsDO can not be null");
		reportsDO.setCheckstate(DEFAULT_CHECKSTATE);
		reportsDO.setReportType(DEFAULT_REPORT_TYPE);
		return reportsDAO.insertReportsDO(reportsDO);
	}

	public Integer deleteReportsDOById(Integer id) {
		Assert.notNull(id, "The id can not be null");
		return reportsDAO.deleteReportsDOById(id);
	}

	public Integer countReportsDO(PageDto pageDto, ReportsDO reportsDO) {
		Assert.notNull(pageDto, "The pageDto can not be null");
		Assert.notNull(reportsDO, "The reportsDO can not be null");
		return reportsDAO.countReportsDO(pageDto, reportsDO);
	}

	public List<ReportsDO> queryReportsDO(PageDto pageDto, ReportsDO reportsDO) {
		Assert.notNull(pageDto, "The pageDto can not be null");
		Assert.notNull(reportsDO, "The reportsDO can not be null");
		return reportsDAO.queryReportsDO(pageDto, reportsDO);
	}

	/*
	 * public PageDto getPage(PageDto page, ReportsDO reportsDO){
	 * page.setTotalRecords(reportsDAO.countReportsDO(page, reportsDO));
	 * page.setRecords(reportsDAO.queryReportsDO(page,reportsDO)); return page;
	 * }
	 */
	public Boolean updateReportsDOCheckstateById(String checkstate, String ids) {
		Assert.notNull(ids, "The ids can not be null");
		Assert.notNull(checkstate, "The checkstate can not be null");
		String[] str = ids.split(",");
		Integer[] i = new Integer[str.length];
		for (int m = 0; m < str.length; m++) {
			i[m] = Integer.valueOf(str[m]);
		}
		if (reportsDAO.updateReportsDOCheckstateById(checkstate, i) > 0) {
			return true;
		}
		return false;
	}

}
