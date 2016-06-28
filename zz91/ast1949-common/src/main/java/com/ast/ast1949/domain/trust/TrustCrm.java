package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustCrm extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1928292371046253899L;

	private Integer id;
	private Integer companyId;
	private Date gmtNextVisit;
	private Integer star;
	private Date gmtLastVisit;
	private Integer isPublic;
	private Integer isRubbish;
	private String crmAccount;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getGmtNextVisit() {
		return gmtNextVisit;
	}

	public void setGmtNextVisit(Date gmtNextVisit) {
		this.gmtNextVisit = gmtNextVisit;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Date getGmtLastVisit() {
		return gmtLastVisit;
	}

	public void setGmtLastVisit(Date gmtLastVisit) {
		this.gmtLastVisit = gmtLastVisit;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getIsRubbish() {
		return isRubbish;
	}

	public void setIsRubbish(Integer isRubbish) {
		this.isRubbish = isRubbish;
	}

	public String getCrmAccount() {
		return crmAccount;
	}

	public void setCrmAccount(String crmAccount) {
		this.crmAccount = crmAccount;
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
