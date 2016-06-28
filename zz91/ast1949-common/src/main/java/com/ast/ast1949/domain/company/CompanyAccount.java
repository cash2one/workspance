/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:42:17
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 公司联系方式领域模型
 * 
 * @author Ryan
 * 
 */
public class CompanyAccount extends DomainSupport {

	private static final long serialVersionUID = 1L;

	private String account; // 登录帐号ID
	private Integer companyId; // 公司ID
	private String contact; // 联系人
	private String isAdmin;
	private String telCountryCode; // 电话国家代码
	private String telAreaCode; // 电话区号
	private String tel; // 电话号码
	private String mobile; // 手机号码
	private String faxCountryCode; // 传真国家代码
	private String faxAreaCode; // 传真区号
	private String fax; // 传真
	private String email; // 邮箱
	private String sex; // 性别(M,F)
	private String position; // 职位
	private String qq; // qq号码
	private String msn; // MSN号码
	private String weixin;//微信号码
	private String backEmail;// 备用邮箱
	private String isUseBackEmail;// 是否使用备用邮箱
	private String password; // 用户密码明文
	private Integer numLogin;
	private Date gmtLastLogin;
	private Date gmtCreated; // 创建时间
	private Date gmtModified; // 修改时间
	private String regiestType;//注册类型

	public String getRegiestType() {
		return regiestType;
	}

	public void setRegiestType(String regiestType) {
		this.regiestType = regiestType;
	}

	public CompanyAccount() {
		super();
	}

	/**
	 * @param account
	 * @param companyId
	 * @param contact
	 * @param isAdmin
	 * @param telCountryCode
	 * @param telAreaCode
	 * @param tel
	 * @param mobile
	 * @param faxCountryCode
	 * @param faxAreaCode
	 * @param fax
	 * @param email
	 * @param sex
	 * @param position
	 * @param qq
	 * @param msn
	 * @param weixin
	 * @param backEmail
	 * @param isUseBackEmail
	 * @param password
	 * @param numLogin
	 * @param gmtLastLogin
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CompanyAccount(String account, Integer companyId, String contact,
			String isAdmin, String telCountryCode, String telAreaCode,
			String tel, String mobile, String faxCountryCode,
			String faxAreaCode, String fax, String email, String sex,
			String position, String qq, String msn,String weixin, String backEmail,
			String isUseBackEmail, String password, Integer numLogin,
			Date gmtLastLogin, Date gmtCreated, Date gmtModified) {
		super();
		this.account = account;
		this.companyId = companyId;
		this.contact = contact;
		this.isAdmin = isAdmin;
		this.telCountryCode = telCountryCode;
		this.telAreaCode = telAreaCode;
		this.tel = tel;
		this.mobile = mobile;
		this.faxCountryCode = faxCountryCode;
		this.faxAreaCode = faxAreaCode;
		this.fax = fax;
		this.email = email;
		this.sex = sex;
		this.position = position;
		this.qq = qq;
		this.msn = msn;
		this.weixin = weixin;
		this.backEmail = backEmail;
		this.isUseBackEmail = isUseBackEmail;
		this.password = password;
		this.numLogin = numLogin;
		this.gmtLastLogin = gmtLastLogin;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

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

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the isAdmin
	 */
	public String getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the telCountryCode
	 */
	public String getTelCountryCode() {
		return telCountryCode;
	}

	/**
	 * @param telCountryCode
	 *            the telCountryCode to set
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
	 * @param telAreaCode
	 *            the telAreaCode to set
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
	 * @param tel
	 *            the tel to set
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
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the faxCountryCode
	 */
	public String getFaxCountryCode() {
		return faxCountryCode;
	}

	/**
	 * @param faxCountryCode
	 *            the faxCountryCode to set
	 */
	public void setFaxCountryCode(String faxCountryCode) {
		this.faxCountryCode = faxCountryCode;
	}

	/**
	 * @return the faxAreaCode
	 */
	public String getFaxAreaCode() {
		return faxAreaCode;
	}

	/**
	 * @param faxAreaCode
	 *            the faxAreaCode to set
	 */
	public void setFaxAreaCode(String faxAreaCode) {
		this.faxAreaCode = faxAreaCode;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the msn
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * @param msn
	 *            the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}
	/**
	 * @return the weixin
	 */
	public String getWeixin() {
		return weixin;
	}
	/**
	 * @param msn
	 *            the weixin to set
	 */
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	/**
	 * @return the backEmail
	 */
	public String getBackEmail() {
		return backEmail;
	}

	/**
	 * @param backEmail
	 *            the backEmail to set
	 */
	public void setBackEmail(String backEmail) {
		this.backEmail = backEmail;
	}

	/**
	 * @return the isUseBackEmail
	 */
	public String getIsUseBackEmail() {
		return isUseBackEmail;
	}

	/**
	 * @param isUseBackEmail
	 *            the isUseBackEmail to set
	 */
	public void setIsUseBackEmail(String isUseBackEmail) {
		this.isUseBackEmail = isUseBackEmail;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the numLogin
	 */
	public Integer getNumLogin() {
		return numLogin;
	}

	/**
	 * @param numLogin
	 *            the numLogin to set
	 */
	public void setNumLogin(Integer numLogin) {
		this.numLogin = numLogin;
	}

	/**
	 * @return the gmtLastLogin
	 */
	public Date getGmtLastLogin() {
		return gmtLastLogin;
	}

	/**
	 * @param gmtLastLogin
	 *            the gmtLastLogin to set
	 */
	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * 返回正在使用的邮箱
	 * @return
	 */
	public String getUseEmail() {
		String useEmail = null;
		if (this.getIsUseBackEmail() != null && this.getIsUseBackEmail().equals("1")) {
			useEmail = this.getBackEmail();
		} else {
			useEmail = this.getEmail();
		}
		return useEmail;
	}
	
}
