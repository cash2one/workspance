/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.dto.information;

import java.io.Serializable;

import com.ast.ast1949.domain.information.ExhibitDO;

/**
 * @author yuyonghui
 * 
 */
public class ExhibitDTO implements Serializable {

	/**
	 * 线下展会DTO
	 */
	private static final long serialVersionUID = 1L;

	private ExhibitDO exhibitDO;
	private String exhibitCategoryName;
	private String plateCategoryName;
	private String areaName;

	public ExhibitDO getExhibitDO() {
		return exhibitDO;
	}

	public void setExhibitDO(ExhibitDO exhibitDO) {
		this.exhibitDO = exhibitDO;
	}

	public String getExhibitCategoryName() {
		return exhibitCategoryName;
	}

	public void setExhibitCategoryName(String exhibitCategoryName) {
		this.exhibitCategoryName = exhibitCategoryName;
	}

	public String getPlateCategoryName() {
		return plateCategoryName;
	}

	public void setPlateCategoryName(String plateCategoryName) {
		this.plateCategoryName = plateCategoryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
