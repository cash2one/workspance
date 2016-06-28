/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-2
 */
package com.ast.ast1949.dto;

import java.io.Serializable;

/**
 * 用来保存登录用户的信息
 * @author Mays (x03570227@gmail.com)
 *
 */
@Deprecated
public class SessionUser implements Serializable{

	private static final long serialVersionUID = 1818320470168987092L;

	private Integer accountId;
	private String account;
	private String email;
	private Integer companyId;
	private String membershipCode;
	private String zstFlag;
	private String areaCode;
	private String serviceCode;
	
	
	
	/**
	 * @param accountId
	 * @param account
	 * @param email
	 * @param companyId
	 * @param membershipCode
	 * @param zstFlag
	 * @param areaCode
	 * @param serviceCode
	 */
	public SessionUser(Integer accountId, String account, String email,
			Integer companyId, String membershipCode, String zstFlag,
			String areaCode, String serviceCode) {
		super();
		this.accountId = accountId;
		this.account = account;
		this.email = email;
		this.companyId = companyId;
		this.membershipCode = membershipCode;
		this.zstFlag = zstFlag;
		this.areaCode = areaCode;
		this.serviceCode = serviceCode;
	}
	/**
	 * 
	 */
	public SessionUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}
	/**
	 * @param membershipCode the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
	/**
	 * @return the zstFlag
	 */
	public String getZstFlag() {
		return zstFlag;
	}
	/**
	 * @param zstFlag the zstFlag to set
	 */
	public void setZstFlag(String zstFlag) {
		this.zstFlag = zstFlag;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
