package com.ast.ast1949.domain.analysis;

import java.util.Date;

public class AnalysisPhoneLog {
	private String adposition;
	private Integer companyId;
	private Integer telCount;
	private Date gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;

	public String getAdposition() {
		return adposition;
	}

	public void setAdposition(String adposition) {
		this.adposition = adposition;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTelCount() {
		return telCount;
	}

	public void setTelCount(Integer telCount) {
		this.telCount = telCount;
	}

	public Date getGmtTarget() {
		return gmtTarget;
	}

	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
