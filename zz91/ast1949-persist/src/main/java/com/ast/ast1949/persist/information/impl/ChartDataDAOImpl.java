/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-26 by Rolyer.
 */
package com.ast.ast1949.persist.information.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartDataDO;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.information.ChartDataDAO;
import com.ast.ast1949.util.Assert;

/**
 *
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("chartDataDAO")
public class ChartDataDAOImpl extends BaseDaoSupport implements ChartDataDAO {
	
	final static String SQL_FIX = "chartdata";

//	public Integer deleteChartDataById(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		
//		return getSqlMapClientTemplate().delete("chartdata.deleteChartDataById", id);
//	}

//	public Integer deleteChartDataBychartCategoryId(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		return getSqlMapClientTemplate().delete("chartdata.deleteChartDataBychartCategoryId", id);
//	}

	public Integer insertChartData(ChartDataDO chartData) {
		Assert.notNull(chartData, "the object of chartData must not be null");
		return (Integer) getSqlMapClientTemplate().insert("chartdata.insertChartData", chartData);
	}

//	public ChartDataDO queryChartDataById(Integer id) {
//		Assert.notNull(id, "the id must not be null");
//		return (ChartDataDO) getSqlMapClientTemplate().queryForObject("chartdata.queryChartDataById", id);
//	}

	public Integer updateChartDataById(ChartDataDO chartData) {
		Assert.notNull(chartData, "the object of chartData must not be null");
		return getSqlMapClientTemplate().update("chartdata.updateChartDataById", chartData);
	}

	public ChartDataDO queryChartDataByCondition(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		return (ChartDataDO) getSqlMapClientTemplate().queryForObject("chartdata.queryChartDataByCondition", param);
	}

	@SuppressWarnings("unchecked")
	public List<ChartDataDTO> queryChartData(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		return getSqlMapClientTemplate().queryForList("chartdata.queryChartData", param);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ChartDataDTO> queryChartDataDesc(Map<String, Object> param) {
		Assert.notNull(param, "the param must not be null");
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryChartDataDesc"), param);
	}

	@SuppressWarnings("unchecked")
	public List<ChartDataDTO> queryChartData(Integer chartInfoId,
			Integer chartCategoryId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("chartInfoId", chartInfoId);
		root.put("chartCategoryId",chartCategoryId);
		return getSqlMapClientTemplate().queryForList("chartdata.queryChartData", root);
	}

	public ChartDataDO queryOneChartData(Integer chartInfoId,
			Integer chartCategoryId, String name) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("chartInfoId", chartInfoId);
		root.put("chartCategoryId",chartCategoryId);
		root.put("name", name);
		return (ChartDataDO) getSqlMapClientTemplate().queryForObject("chartdata.queryChartDataByCondition", root);
	}

	public Integer deleteChartDataByChartInfoId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete("chartdata.deleteChartDataByChartInfoId", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChartDataDO> queryChartDataByChartCategoryId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().queryForList("chartdata.queryChartDataByChartCategoryId", id);
	}

}
