package com.zz91.ep.dto.comp;

import java.io.Serializable;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.dto.AreaDto;

public class CompanyNormDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CompProfile company;
	
	private AreaDto area;
	
	private String compCategoryName;
	

	public CompProfile getCompany() {
		return company;
	}

	public void setCompany(CompProfile company) {
		this.company = company;
	}

	public AreaDto getArea() {
		return area;
	}

	public void setArea(AreaDto area) {
		this.area = area;
	}

	public String getCompCategoryName() {
		return compCategoryName;
	}

	public void setCompCategoryName(String compCategoryName) {
		this.compCategoryName = compCategoryName;
	}



}
