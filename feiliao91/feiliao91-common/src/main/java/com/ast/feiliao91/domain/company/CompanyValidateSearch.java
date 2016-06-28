package com.ast.feiliao91.domain.company;

public class CompanyValidateSearch {
	private Integer isGroupBy;//是否分组，默认0，不分组，1分组
	private String targetName;
	private String companyName;

	public Integer getIsGroupBy() {
		return isGroupBy;
	}

	public void setIsGroupBy(Integer isGroupBy) {
		this.isGroupBy = isGroupBy;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
