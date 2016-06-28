package com.ast.ast1949.dto.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustCsLogDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5395672721148869094L;

	private Integer companyId;
	private String companyName;
	private Date gmtVisit;
	private Integer star;
	private Date gmtLastLogin;
	private String content;
	private String trustAccount;
	private Integer targetId;
	private String targetName;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getGmtVisit() {
		return gmtVisit;
	}

	public void setGmtVisit(Date gmtVisit) {
		this.gmtVisit = gmtVisit;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Date getGmtLastLogin() {
		return gmtLastLogin;
	}

	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTrustAccount() {
		return trustAccount;
	}

	public void setTrustAccount(String trustAccount) {
		this.trustAccount = trustAccount;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}
