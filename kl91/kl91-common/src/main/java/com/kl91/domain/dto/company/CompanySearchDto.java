package com.kl91.domain.dto.company;

import com.kl91.domain.DomainSupport;

public class CompanySearchDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7865678453757562421L;
	private String keywords; // 搜索关键字
	private String industryCode; // 主营类别

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
}
