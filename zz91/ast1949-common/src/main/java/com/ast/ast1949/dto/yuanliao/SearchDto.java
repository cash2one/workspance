package com.ast.ast1949.dto.yuanliao;

public class SearchDto {
	private Integer companyId;
	private String categoryYuanliaoCode;
	private String title;
	public Integer getCompanyId() {
		return companyId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCategoryYuanliaoCode() {
		return categoryYuanliaoCode;
	}
	public void setCategoryYuanliaoCode(String categoryYuanliaoCode) {
		this.categoryYuanliaoCode = categoryYuanliaoCode;
	}
	
}
