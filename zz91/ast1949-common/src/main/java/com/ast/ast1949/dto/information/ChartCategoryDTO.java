/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-1 by Rolyer.
 */
package com.ast.ast1949.dto.information;

import java.util.Map;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
public class ChartCategoryDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private ChartCategoryDO chartCategory;
	private String relevanceName;
	private Map<String, Float> colMap;
	
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public ChartCategoryDO getChartCategory() {
		return chartCategory;
	}
	public void setChartCategory(ChartCategoryDO chartCategory) {
		this.chartCategory = chartCategory;
	}
	public String getRelevanceName() {
		return relevanceName;
	}
	public void setRelevanceName(String relevanceName) {
		this.relevanceName = relevanceName;
	}
	/**
	 * @return the colMap
	 */
	public Map<String, Float> getColMap() {
		return colMap;
	}
	/**
	 * @param colMap the colMap to set
	 */
	public void setColMap(Map<String, Float> colMap) {
		this.colMap = colMap;
	}
	
}
