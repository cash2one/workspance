/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31 by Rolyer.
 */
package com.ast.ast1949.dto.information;

import com.ast.ast1949.domain.information.ChartsInfoDO;
import com.ast.ast1949.dto.PageDto;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public class ChartsInfoDTO implements java.io.Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private ChartsInfoDO chartsInfo;
	private String categoryName;
	
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public ChartsInfoDO getChartsInfo() {
		return chartsInfo;
	}
	public void setChartsInfo(ChartsInfoDO chartsInfo) {
		this.chartsInfo = chartsInfo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
