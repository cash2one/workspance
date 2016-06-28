/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-27 by Rolyer.
 */
package com.ast.ast1949.service.information.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.information.ChartCategoryDTO;
import com.ast.ast1949.dto.information.ChartDataDTO;
import com.ast.ast1949.persist.information.ChartCategoryDAO;
import com.ast.ast1949.persist.information.ChartDataDAO;
import com.ast.ast1949.service.information.ChartCategoryService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("chartCategoryService")
public class ChartCategoryServiceImpl implements ChartCategoryService {

	@Autowired
	private ChartCategoryDAO chartCategoryDAO;
	@Autowired
	private ChartDataDAO chartDataDAO;
	
	public Integer deleteChartCategoryById(Integer id) {
		return chartCategoryDAO.deleteChartCategoryById(id);
	}

	public Integer insertChartCategory(ChartCategoryDO chartCategory) {
		if(chartCategory.getParentId()==null){
			chartCategory.setParentId(0);
		}
		if(chartCategory.getShowInHome()==null){
			chartCategory.setShowInHome("0");
		}
		return chartCategoryDAO.insertChartCategory(chartCategory);
	}

	public ChartCategoryDO queryChartCategoryById(Integer id) {
		return chartCategoryDAO.queryChartCategoryById(id);
	}

	public List<ChartCategoryDO> queryChartCategoryByParentId(Integer id) {
		return chartCategoryDAO.queryChartCategoryByParentId(id);
	}

	public Integer updateChartCategoryById(ChartCategoryDO chartCategory) {
		if(chartCategory.getParentId()==null){
			chartCategory.setParentId(0);
		}
		if(chartCategory.getShowInHome()==null){
			chartCategory.setShowInHome("0");
		}
		return chartCategoryDAO.updateChartCategoryById(chartCategory);
	}

	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id) {
		Assert.notNull(id, "The parent id must not be null.");
		List<ChartCategoryDO> chartCategoryDO = queryChartCategoryByParentId(id);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for(ChartCategoryDO n:chartCategoryDO){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-"+String.valueOf(n.getId()));
			node.setLeaf(false);
			node.setText(n.getName());
			node.setData(n.getId().toString());
			treeList.add(node);
		}
		return treeList;
	}

//	public Integer countChartCategoryListByParentId(
//			ChartCategoryDTO chartCategoryDTO) {
//		return chartCategoryDAO.countChartCategoryListByParentId(chartCategoryDTO);
//	}

	public List<ChartCategoryDTO> queryChartCategoryListByParentId(
			ChartCategoryDTO chartCategoryDTO) {
		return chartCategoryDAO.queryChartCategoryListByParentId(chartCategoryDTO);
	}

	public ChartCategoryDTO queryChartCategoryDtoById(Integer id) {
		return chartCategoryDAO.queryChartCategoryDtoById(id);
	}

	public List<ChartCategoryDTO> initChartData(
			List<ChartCategoryDTO> categoryList, Integer chartInfoId, Integer parentId) {
		ChartCategoryDO parent = chartCategoryDAO.queryChartCategoryById(parentId);
		String[] setting=parent.getSetting().split(";");
		Map<String, String> colnameMap = new HashMap<String, String>();
		
		for(int i=0;i<setting.length;i++){
			colnameMap.put(setting[i], "col"+i);
		}
		for(ChartCategoryDTO category:categoryList){
			Map<String, Float> dataMap = new HashMap<String, Float>();
			for(int i=0;i<setting.length;i++){
				dataMap.put("col"+i, 0f);
			}
			List<ChartDataDTO> list = chartDataDAO.queryChartData(chartInfoId, category.getChartCategory().getId());
			for(ChartDataDTO data:list){
				dataMap.put(colnameMap.get(data.getChartData().getName()), data.getChartData().getValue());
			}
			category.setColMap(dataMap);
		}
		return categoryList;
	}

	@Override
	public LinkedList<ChartCategoryDO> queryChartCategoryTreeByParentId(Integer id) {
		LinkedList<ChartCategoryDO> list = new LinkedList<ChartCategoryDO>();
		List<ChartCategoryDO> chartCategory=queryChartCategoryByParentId(id);
		for (ChartCategoryDO c : chartCategory) {
			list.add(c);
			
			List<ChartCategoryDO> chartCategory2=queryChartCategoryByParentId(c.getId());
			if(chartCategory2.size()>0){
				for (ChartCategoryDO c2 : chartCategory2) {
					list.add(c2);
				}
			}
		}
		
		return list;
	}

}
