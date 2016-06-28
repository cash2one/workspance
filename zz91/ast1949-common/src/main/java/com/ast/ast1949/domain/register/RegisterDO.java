/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-13
 */
package com.ast.ast1949.domain.register;

/**
 * 注册信息
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class RegisterDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String membershipCode;
	private String account;
	private String password;
	private String telCountryCode;		//电话国家代码
	private String telAreaCode;			//电话区号
	private String tel;					//电话号码
	private String mobile;
	private String email;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the telCountryCode
	 */
	public String getTelCountryCode() {
		return telCountryCode;
	}
	/**
	 * @param telCountryCode the telCountryCode to set
	 */
	public void setTelCountryCode(String telCountryCode) {
		this.telCountryCode = telCountryCode;
	}
	/**
	 * @return the telAreaCode
	 */
	public String getTelAreaCode() {
		return telAreaCode;
	}
	/**
	 * @param telAreaCode the telAreaCode to set
	 */
	public void setTelAreaCode(String telAreaCode) {
		this.telAreaCode = telAreaCode;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
