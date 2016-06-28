/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-9.
 */
package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.bbs.ReportsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("reportsDAO")
public class ReportsDAOImpl extends SqlMapClientDaoSupport implements ReportsDAO {
	public final static int DEFAULT_BATCH_SIZE = 20;
	public Integer insertReportsDO(ReportsDO reportsDO) {
		Assert.notNull(reportsDO, "The reportsDO can not be null");
		
		Assert.notNull(reportsDO.getReportId(),"");
		return (Integer)(getSqlMapClientTemplate().insert("reports.insertReportsDO",
				reportsDO));
	}

	public Integer deleteReportsDOById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return Integer.valueOf(getSqlMapClientTemplate().delete("reports.deleteReportsDOById",id));
	}

	public Integer countReportsDO(PageDto pageDto,ReportsDO reportsDO){
		Assert.notNull(reportsDO, "the reportsDO can not be null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportsDO", reportsDO);
		return (Integer)getSqlMapClientTemplate().queryForObject("reports.queryReportsDOCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportsDO> queryReportsDO(PageDto pageDto, ReportsDO reportsDO) {
		Assert.notNull(pageDto, "the pageDto can not be null");
		Assert.notNull(reportsDO, "the reportsDO can not be null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageDto", pageDto);
		map.put("reportsDO", reportsDO);
		return getSqlMapClientTemplate().queryForList("reports.queryReportsDO", map);
	}

	public Integer updateReportsDOCheckstateById(String checkstate,Integer ids[]) {
		Assert.notNull(ids, "The ids can not be null");
		int impacted = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		int batchNum = (ids.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > ids.length ? ids.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					map.put("id", ids[i]);
					map.put("checkstate", checkstate);
					impacted += getSqlMapClientTemplate().update("reports.updateReportsDOCheckstateById", map);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch updateReportsDOCheckstateById failed.", e);
		}
		return impacted;
	}
}
