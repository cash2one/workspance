package com.ast.feiliao91.auth;

import com.ast.feiliao91.domain.DomainSupport;

public class SsoUser extends DomainSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1509878837675223942L;

	public final static String TICKET_KEY="feiliao91ssotoken";
	public final static String SESSION_KEY="feiliao91authorizesession";
	
	private Integer accountId;
	private Integer companyId;
	private String account;
	private String mobile;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
