/**
 * 
 */
package com.zz91.ep.dto.data;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.net)
 *
 * created by 2011-10-25
 */
public class CompAccountMakeMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer account;
	private Integer email;
	private Integer accountName;
	private Integer position;
	private Integer mobile;
	private Integer phone;
	private Integer phoneArea;
	private Integer phoneCountry;
	private Integer fax;
	private Integer faxArea;
	private Integer faxCountry;
	private Integer contact; //其他联系人
	
	/**
	 * @return the account
	 */
	public Integer getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Integer account) {
		this.account = account;
	}
	/**
	 * @return the email
	 */
	public Integer getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(Integer email) {
		this.email = email;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	/**
	 * @return the mobile
	 */
	public Integer getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the phone
	 */
	public Integer getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	/**
	 * @return the phoneArea
	 */
	public Integer getPhoneArea() {
		return phoneArea;
	}
	/**
	 * @param phoneArea the phoneArea to set
	 */
	public void setPhoneArea(Integer phoneArea) {
		this.phoneArea = phoneArea;
	}
	/**
	 * @return the phoneCountry
	 */
	public Integer getPhoneCountry() {
		return phoneCountry;
	}
	/**
	 * @param phoneCountry the phoneCountry to set
	 */
	public void setPhoneCountry(Integer phoneCountry) {
		this.phoneCountry = phoneCountry;
	}
	/**
	 * @return the fax
	 */
	public Integer getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(Integer fax) {
		this.fax = fax;
	}
	/**
	 * @return the faxArea
	 */
	public Integer getFaxArea() {
		return faxArea;
	}
	/**
	 * @param faxArea the faxArea to set
	 */
	public void setFaxArea(Integer faxArea) {
		this.faxArea = faxArea;
	}
	/**
	 * @return the faxCountry
	 */
	public Integer getFaxCountry() {
		return faxCountry;
	}
	/**
	 * @param faxCountry the faxCountry to set
	 */
	public void setFaxCountry(Integer faxCountry) {
		this.faxCountry = faxCountry;
	}
	/**
	 * @return the contact
	 */
	public Integer getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(Integer contact) {
		this.contact = contact;
	}
	/**
	 * @return the accountName
	 */
	public Integer getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(Integer accountName) {
		this.accountName = accountName;
	}
	
}
