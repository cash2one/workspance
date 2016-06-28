/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-27 by Rolyer.
 */
package com.ast.ast1949.service.information.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.domain.information.ChartDataDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.dto.information.ChartDataForIndexDTO;
import com.ast.ast1949.persist.information.ChartCategoryDAO;
import com.ast.ast1949.persist.information.ChartDataDAO;
import com.ast.ast1949.service.information.ChartDataService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("chartDataService")
public class ChartDataServiceImpl implements ChartDataService {
	
	@Autowired
	private ChartDataDAO chartDataDAO;
	@Autowired
	private ChartCategoryDAO chartCategoryDAO;

//	public Integer deleteChartDataById(Integer id) {
//		return chartDataDAO.deleteChartDataById(id);
//	}

//	public Integer deleteChartDataBychartCategoryId(Integer id) {
//		return chartDataDAO.deleteChartDataBychartCategoryId(id);
//	}

//	public Integer insertChartData(ChartDataDO chartData) {
//		return chartDataDAO.insertChartData(chartData);
//	}

//	public ChartDataDO queryChartDataById(Integer id) {
//		return chartDataDAO.queryChartDataById(id);
//	}

//	public Integer updateChartDataById(ChartDataDO chartData) {
//		return chartDataDAO.updateChartDataById(chartData);
//	}

	public ChartDataDO queryChartDataByCondition(Integer chartInfoId,
			Integer chartCategoryId, String name) {
		Assert.notNull(chartInfoId, "the chartInfoId must not be null");
		Assert.notNull(chartCategoryId, "the chartCategoryId must not be null");
		Assert.notNull(name, "the name must not be null");

		Map<String, Object> param =new HashMap<String, Object>();
		param.put("chartInfoId", chartInfoId);
		param.put("chartCategoryId", chartCategoryId);
		param.put("name", name);
		
		return chartDataDAO.queryChartDataByCondition(param);
	}
	
	public ExtResult setChartDataValue(ChartDataDO chartData){
		Assert.notNull(chartData, "the object of chartData must not be null");
		Assert.notNull(chartData.getChartInfoId(), "the chartInfoId must not be null");
		Assert.notNull(chartData.getChartCategoryId(), "the chartCategoryId must not be null");
		Assert.notNull(chartData.getName(), "the name must not be null");
		
		ExtResult result = new ExtResult();
		Integer im=0;

		ChartDataDO old=queryChartDataByCondition(chartData.getChartInfoId(), chartData.getChartCategoryId(), chartData.getName());
		if(old!=null&&old.getId()>0){
			//已存在,更新数据
			chartData.setId(old.getId());
			im =  chartDataDAO.updateChartDataById(chartData);
		} else {
			//不存在，插入新数据
			im = chartDataDAO.insertChartData(chartData);
		}
		
		if(im.intValue()>0){
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		
		return result;
	}

//	public List<ChartDataDTO> queryChartData(Map<String, Object> param) {
//		Assert.notNull(param, "the param must not be null");
//		return chartDataDAO.queryChartData(param);
//	}

	public Map<String, List<ChartDataDTO>> queryChartData(
			Integer chartCategoryId, Date fromDate, Date toDate, String[] name) {
		
		Assert.notNull(chartCategoryId, "the chartCategoryId must not be null");
		Assert.notNull(fromDate, "the fromDate must not be null");
		Assert.notNull(toDate, "the toDate must not be null");
		Assert.notNull(name, "the name must not be null");
		
		Map<String, List<ChartDataDTO>> data=new LinkedHashMap<String, List<ChartDataDTO>>();
		Map<String, Object> param =null;
		for (String n : name) {
			param = new HashMap<String, Object>();
			param.put("chartCategoryId", chartCategoryId);
			param.put("fromDate", fromDate);
			param.put("toDate", toDate);
			param.put("name", n);
			List<ChartDataDTO> list = chartDataDAO.queryChartData(param);
			
			data.put(n, list);
		}
		
		return data;
	}
	
	@Override
	public List<ChartDataDTO> queryChartData(
			Integer chartCategoryId, Date fromDate, Date toDate) {
		
		Assert.notNull(chartCategoryId, "the chartCategoryId must not be null");
		Assert.notNull(fromDate, "the fromDate must not be null");
		Assert.notNull(toDate, "the toDate must not be null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("chartCategoryId", chartCategoryId);
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		return chartDataDAO.queryChartDataDesc(param);
	}

	public Integer deleteChartDataByChartInfoId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return chartDataDAO.deleteChartDataByChartInfoId(id);
	}

	@Override
	public List<ChartDataForIndexDTO> queryChartDataForIndex(Integer max) {
		Assert.notNull(max, "the max must not be null");
		
		List<ChartDataForIndexDTO> list = new ArrayList<ChartDataForIndexDTO>();
		
		List<ChartCategoryDO> category = chartCategoryDAO.queryChartCategoryCanShowInHome(max);
		for (ChartCategoryDO c : category) {
			List<ChartDataDO> data = chartDataDAO.queryChartDataByChartCategoryId(c.getId());
			ChartDataForIndexDTO dto = new ChartDataForIndexDTO();
			if(data!=null){
				if(data.size()==2) {
					dto.setId(c.getId());
					dto.setName(c.getName());
					dto.setValue(data.get(0).getValue());
					dto.setDiff(data.get(0).getValue()-data.get(1).getValue());
					
					list.add(dto);
				} else if(data.size()>0&&data.size()<2) {
					dto.setId(c.getId());
					dto.setName(c.getName());
					dto.setValue(data.get(0).getValue());
					dto.setDiff(Float.parseFloat("0"));
					
					list.add(dto);
				}
			}
		}
		return list;
	}

	@Override
	public List<ChartDataForIndexDTO> queryChartDataForPriceByParentId(Integer id) {
		List<ChartCategoryDO> ccList =chartCategoryDAO.queryChartCategoryByParentId(30);
		List<ChartDataForIndexDTO> list = new ArrayList<ChartDataForIndexDTO>();
		for(ChartCategoryDO obj:ccList){
			ChartDataForIndexDTO dto = new ChartDataForIndexDTO();
			List<ChartDataDO> data = chartDataDAO.queryChartDataByChartCategoryId(obj.getId());
			if(data!=null){
				if(data.size()==2) {
					dto.setId(obj.getId());
					dto.setName(obj.getName());
					dto.setValue(data.get(0).getValue());
					dto.setDiff(data.get(0).getValue()-data.get(1).getValue());
					list.add(dto);
				} else if(data.size()>0&&data.size()<2) {
					dto.setId(obj.getId());
					dto.setName(obj.getName());
					dto.setValue(data.get(0).getValue());
					dto.setDiff(Float.parseFloat("0"));
					list.add(dto);
				}
			}
		}
		return list;
	}
}

