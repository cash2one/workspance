package com.ast.ast1949.domain.phone;

import com.ast.ast1949.domain.DomainSupport;

public class PhoneLostCus extends DomainSupport {

	private static final long serialVersionUID = -3666850824213771364L;
	private Integer companyId;
	private String  gmtTarget;
	private String  gmtCreated;
	private String gmtModified;
	public String getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(String gmtTarget) {
		this.gmtTarget = gmtTarget;
	}
	public String getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(String gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public String getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	

}
