package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PhoneCsBs extends DomainSupport {
	private static final long serialVersionUID = -3666850824213771364L;
	private Integer companyId;
	private Integer targetId;
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
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
