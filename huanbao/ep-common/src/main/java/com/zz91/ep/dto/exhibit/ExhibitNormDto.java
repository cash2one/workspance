package com.zz91.ep.dto.exhibit;

import java.io.Serializable;

import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.AreaDto;

public class ExhibitNormDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Exhibit exhibit;
	
	private AreaDto area;
	
	private String plateCategoryName;// 板块名称

	private String industryName;// 行业类别名称


	public Exhibit getExhibit() {
		return exhibit;
	}

	public void setExhibit(Exhibit exhibit) {
		this.exhibit = exhibit;
	}

	public AreaDto getArea() {
		return area;
	}

	public void setArea(AreaDto area) {
		this.area = area;
	}

	public String getPlateCategoryName() {
		return plateCategoryName;
	}

	public void setPlateCategoryName(String plateCategoryName) {
		this.plateCategoryName = plateCategoryName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

}
