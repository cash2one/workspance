package com.ast.ast1949.domain.log;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class AuthAdmin extends DomainSupport {
	private static final long serialVersionUID = -5848694845942241736L;
	private String account;
	private Date gmtCreated;
	private Date gmtModified;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
