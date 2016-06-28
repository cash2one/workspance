package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.dto.PageDto;

public class CategoryGardenDTO implements java.io.Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String	searchName;
	private String areaName;
	private String industryName;
	private String gardenTypeName;

	private CategoryGardenDO categoryGardenDO;

	private PageDto pageDto;

	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public CategoryGardenDO getCategoryGardenDO() {
		return categoryGardenDO;
	}
	public void setCategoryGardenDO(CategoryGardenDO categoryGardenDO) {
		this.categoryGardenDO = categoryGardenDO;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getGardenTypeName() {
		return gardenTypeName;
	}

	public void setGardenTypeName(String gardenTypeName) {
		this.gardenTypeName = gardenTypeName;
	}


}
