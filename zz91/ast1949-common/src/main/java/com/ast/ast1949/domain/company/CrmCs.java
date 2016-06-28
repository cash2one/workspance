package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CrmCs extends DomainSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String csAccount;
	private Integer companyId;
	private Date gmtVisit;
	private Date gmtNextVisitPhone;
	private Date gmtNextVisitEmail;
	private String visitTarget;
	private Date gmtCreated;
	private Date gmtModified;
	
	public CrmCs() {
		super();
	}
	public CrmCs(String csAccount, Integer companyId, Date gmtVisit,
			Date gmtNextVisitPhone, Date gmtNextVisitEmail, String visitTarget,
			Date gmtCreated, Date gmtModified) {
		super();
		this.csAccount = csAccount;
		this.companyId = companyId;
		this.gmtVisit = gmtVisit;
		this.gmtNextVisitPhone = gmtNextVisitPhone;
		this.gmtNextVisitEmail = gmtNextVisitEmail;
		this.visitTarget = visitTarget;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public String getCsAccount() {
		return csAccount;
	}
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Date getGmtVisit() {
		return gmtVisit;
	}
	public void setGmtVisit(Date gmtVisit) {
		this.gmtVisit = gmtVisit;
	}
	public Date getGmtNextVisitPhone() {
		return gmtNextVisitPhone;
	}
	public void setGmtNextVisitPhone(Date gmtNextVisitPhone) {
		this.gmtNextVisitPhone = gmtNextVisitPhone;
	}
	public Date getGmtNextVisitEmail() {
		return gmtNextVisitEmail;
	}
	public void setGmtNextVisitEmail(Date gmtNextVisitEmail) {
		this.gmtNextVisitEmail = gmtNextVisitEmail;
	}
	public String getVisitTarget() {
		return visitTarget;
	}
	public void setVisitTarget(String visitTarget) {
		this.visitTarget = visitTarget;
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
