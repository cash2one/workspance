package com.ast.ast1949.domain.credit;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CreditFileDo extends DomainSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyId;
	private String account;
	private String categoryCode;
	private String fileName;
	private Date startTime;
	private Date endTime;
	private String fileNumber;
	private String organization;
	private String tel;
	private String website;
	private String picName;
	private String introduction;
	private String checkStatus;
	private String checkPerson;
	private Date gmtCreated;
	private Date gmtModified;



	public CreditFileDo() {
	}

	/**
	 * @param companyId
	 * @param account
	 * @param categoryCode
	 * @param fileName
	 * @param startTime
	 * @param endTime
	 * @param fileNumber
	 * @param organization
	 * @param tel
	 * @param website
	 * @param picName
	 * @param introduction
	 * @param checkStatus
	 * @param checkPerson
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CreditFileDo(Integer companyId, String account, String categoryCode,
			String fileName, Date startTime, Date endTime, String fileNumber,
			String organization, String tel, String website, String picName,
			String introduction, String checkStatus, String checkPerson,
			Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.account = account;
		this.categoryCode = categoryCode;
		this.fileName = fileName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.fileNumber = fileNumber;
		this.organization = organization;
		this.tel = tel;
		this.website = website;
		this.picName = picName;
		this.introduction = introduction;
		this.checkStatus = checkStatus;
		this.checkPerson = checkPerson;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
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
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the fileNumber
	 */
	public String getFileNumber() {
		return fileNumber;
	}
	/**
	 * @param fileNumber the fileNumber to set
	 */
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
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
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the picName
	 */
	public String getPicName() {
		return picName;
	}
	/**
	 * @param picName the picName to set
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}
	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
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
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
}
