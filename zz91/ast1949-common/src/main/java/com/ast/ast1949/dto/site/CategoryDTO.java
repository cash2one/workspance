/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-18 上午10:07:05
 */
package com.ast.ast1949.dto.site;

import java.util.List;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.PageDto;

/**
 *
 * @author Ryan
 *
 */
public class CategoryDTO implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDO category;
	private String searchName;
	private String preCode;
	private PageDto pageDto;
	private List<CategoryDTO> categoryList;


	/**
	 * @return the categoryList
	 */
	public List<CategoryDTO> getCategoryList() {
		return categoryList;
	}
	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
 
	public CategoryDO getCategory() {
		return category;
	}
	public void setCategory(CategoryDO category) {
		this.category = category;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	/**
	 * @param preCode the preCode to set
	 */
	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}
	/**
	 * @return the preCode
	 */
	public String getPreCode() {
		return preCode;
	}
}
