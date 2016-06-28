package com.ast.ast1949.domain.credit;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CreditReferenceDo extends DomainSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyId;
	private String account;
	private String name;
	private String companyName;
	private String address;
	private String tel;
	private String fax;
	private String email;
	private String details;
	private String checkStatus;
	private String checkPerson;
	private Date gmtCreated;
	private Date gmtModified;
	
	/**
	 * 
	 */
	public CreditReferenceDo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param companyId
	 * @param account
	 * @param name
	 * @param companyName
	 * @param address
	 * @param tel
	 * @param fax
	 * @param email
	 * @param details
	 * @param checkStatus
	 * @param checkPerson
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CreditReferenceDo(Integer companyId, String account, String name,
			String companyName, String address, String tel, String fax,
			String email, String details, String checkStatus,
			String checkPerson, Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.account = account;
		this.name = name;
		this.companyName = companyName;
		this.address = address;
		this.tel = tel;
		this.fax = fax;
		this.email = email;
		this.details = details;
		this.checkStatus = checkStatus;
		this.checkPerson = checkPerson;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	/**
	 * @return the checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return the checkPerson
	 */
	public String getCheckPerson() {
		return checkPerson;
	}
	/**
	 * @param checkPerson the checkPerson to set
	 */
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

}
