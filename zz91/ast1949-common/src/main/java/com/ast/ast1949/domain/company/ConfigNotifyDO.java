package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ConfigNotifyDO extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private String notifyCode;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getNotifyCode() {
		return notifyCode;
	}
	public void setNotifyCode(String notifyCode) {
		this.notifyCode = notifyCode;
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
