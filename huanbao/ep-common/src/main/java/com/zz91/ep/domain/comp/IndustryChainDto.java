package com.zz91.ep.domain.comp;

import java.io.Serializable;

public class IndustryChainDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IndustryChain industryChain;
	
	private String checked;

	public IndustryChainDto() {
		super();
		
	}

	public IndustryChainDto(IndustryChain industryChain, String checked) {
		super();
		this.industryChain = industryChain;
		this.checked = checked;
	}

	public IndustryChain getIndustryChain() {
		return industryChain;
	}

	public void setIndustryChain(IndustryChain industryChain) {
		this.industryChain = industryChain;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}
