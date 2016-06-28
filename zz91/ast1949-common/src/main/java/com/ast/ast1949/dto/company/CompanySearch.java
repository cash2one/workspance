package com.ast.ast1949.dto.company;

public class CompanySearch {
	/**
	 * 默认数据量
	 */
	private final static Integer COUNT_default = 10;
	/**
	 * 产品类别
	 */
	private String industryCode;
	/**
	 * 需要的数据量 默认10条
	 */
	private Integer count;

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public Integer getCount() {
		if (count == null)
			count = COUNT_default;
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
