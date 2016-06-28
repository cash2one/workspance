package com.ast.ast1949.domain;

public class SearchSupport extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String keywords;
	private Integer limitDays;
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}
	
}
