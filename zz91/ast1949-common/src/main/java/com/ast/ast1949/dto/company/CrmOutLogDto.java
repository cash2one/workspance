package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.company.CrmOutLog;

/**
 * author:kongsj date:2013-5-16
 */
public class CrmOutLogDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1708498723012316231L;
	private CrmOutLog crmOutLog;

	private String email;
	private String mobile;
	private String account;
	private Integer companyId;

	public CrmOutLog getCrmOutLog() {
		return crmOutLog;
	}


	public String getMobile() {
		return mobile;
	}

	public String getAccount() {
		return account;
	}

	public void setCrmOutLog(CrmOutLog crmOutLog) {
		this.crmOutLog = crmOutLog;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAccount(String account) {
		this.account = account;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	

}
