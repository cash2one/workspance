/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-28
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-4-28
 */
public class CompanyAccountContact extends DomainSupport {

	private static final long serialVersionUID = 1L;

	private String account;
	private String name;
	private String sex;
	private String tel;
	private String mobile;
	private String isHidden;
	private String qq;
	private String email;
	private Date gmtCreated;
	private Date gmtModified;

	/**
	 * 
	 */
	public CompanyAccountContact() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param account
	 * @param name
	 * @param sex
	 * @param tel
	 * @param mobile
	 * @param isHidden
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CompanyAccountContact(String account, String name, String sex,
			String tel, String mobile, String isHidden,String qq,String email, Date gmtCreated,
			Date gmtModified) {
		super();
		this.account = account;
		this.name = name;
		this.sex = sex;
		this.tel = tel;
		this.mobile = mobile;
		this.tel = qq;
		this.mobile = email;
		this.isHidden = isHidden;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the isHidden
	 */
	public String getIsHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden
	 *            the isHidden to set
	 */
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
