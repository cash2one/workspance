/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-13
 */
package com.kl91.domain.auth;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-6-13
 */
public class Kl91SsoUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2468555611888788563L;

	private String account;
	private String email;
	private Integer companyId;
	private String domain;

	private String key;
	public final static String SESSION_KEY = "KL91authorizesession";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String ticket;

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
