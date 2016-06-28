/**
 * 
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.Date;

/**
 * @author root
 * 
 */
public class CrmSearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String csFlag;
	private String contact;
	private String email;
	private String companyName;
	private String mobile;
	private String svrCode;
	private Date svrStartFrom;
	private Date svrStartTo;
	private Date svrEndFrom;
	private Date svrEndTo;
	private Date nextVisitPhoneFrom;
	private Date nextVisitPhoneTo;
	private Date visitFrom;
	private Date visitTo;
	private String csAccount;
	private String areaCode;
	private String industryCode;
	private Long unvisitFlag;
	private Date logDayFrom;
	private Date logDayTo;
	private String killFlag;

	private Date svrBSFromTo; // 必杀期 已过期三个月内客户
	private Date svrBSEndTo; // 必杀期 已过期三个月内客户
	private Date oneMonthBS; // 必杀期 过期且一个月未产生有效联系小记

	private Integer noExpired; // 未过期
	private Integer expired; // 过期
	private Integer expiredFlag; //来电宝客户已过期标志 1:已过期
	private String pptCode; // 品牌通
	private String outbusiness;
	private Integer allCustomerFlag; //来电宝所有客户查询是用到的标志 1:表示需要用到phone表
	private Integer phoneCsBsFlag;   //来电宝必杀期客户查询是用到的标志 1:表示需要用到phone_cs_bs表
	private Integer lostCustomerFlag;////来电宝必杀期客户查询是用到的标志 1:表示需要用到phone_cs_bs表
	
	

	public Integer getAllCustomerFlag() {
		return allCustomerFlag;
	}

	public void setAllCustomerFlag(Integer allCustomerFlag) {
		this.allCustomerFlag = allCustomerFlag;
	}

	public Integer getPhoneCsBsFlag() {
		return phoneCsBsFlag;
	}

	public void setPhoneCsBsFlag(Integer phoneCsBsFlag) {
		this.phoneCsBsFlag = phoneCsBsFlag;
	}

	public Integer getLostCustomerFlag() {
		return lostCustomerFlag;
	}

	public void setLostCustomerFlag(Integer lostCustomerFlag) {
		this.lostCustomerFlag = lostCustomerFlag;
	}
	
	public Integer getExpiredFlag() {
		return expiredFlag;
	}
	
	public void setExpiredFlag(Integer expiredFlag) {
		this.expiredFlag = expiredFlag;
	}

	/**
	 * @return the csFlag
	 */
	public String getCsFlag() {
		return csFlag;
	}

	/**
	 * @param csFlag
	 *            the csFlag to set
	 */
	public void setCsFlag(String csFlag) {
		this.csFlag = csFlag;
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	 * @return the svrCode
	 */
	public String getSvrCode() {
		return svrCode;
	}

	/**
	 * @param svrCode
	 *            the svrCode to set
	 */
	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	/**
	 * @return the svrStartFrom
	 */
	public Date getSvrStartFrom() {
		return svrStartFrom;
	}

	/**
	 * @param svrStartFrom
	 *            the svrStartFrom to set
	 */
	public void setSvrStartFrom(Date svrStartFrom) {
		this.svrStartFrom = svrStartFrom;
	}

	/**
	 * @return the svrStartTo
	 */
	public Date getSvrStartTo() {
		return svrStartTo;
	}

	/**
	 * @param svrStartTo
	 *            the svrStartTo to set
	 */
	public void setSvrStartTo(Date svrStartTo) {
		this.svrStartTo = svrStartTo;
	}

	/**
	 * @return the svrEndFrom
	 */
	public Date getSvrEndFrom() {
		return svrEndFrom;
	}

	/**
	 * @param svrEndFrom
	 *            the svrEndFrom to set
	 */
	public void setSvrEndFrom(Date svrEndFrom) {
		this.svrEndFrom = svrEndFrom;
	}

	/**
	 * @return the svrEndTo
	 */
	public Date getSvrEndTo() {
		return svrEndTo;
	}

	/**
	 * @param svrEndTo
	 *            the svrEndTo to set
	 */
	public void setSvrEndTo(Date svrEndTo) {
		this.svrEndTo = svrEndTo;
	}

	/**
	 * @return the nextVisitPhoneFrom
	 */
	public Date getNextVisitPhoneFrom() {
		return nextVisitPhoneFrom;
	}

	/**
	 * @param nextVisitPhoneFrom
	 *            the nextVisitPhoneFrom to set
	 */
	public void setNextVisitPhoneFrom(Date nextVisitPhoneFrom) {
		this.nextVisitPhoneFrom = nextVisitPhoneFrom;
	}

	/**
	 * @return the nextVisitPhoneTo
	 */
	public Date getNextVisitPhoneTo() {
		return nextVisitPhoneTo;
	}

	/**
	 * @param nextVisitPhoneTo
	 *            the nextVisitPhoneTo to set
	 */
	public void setNextVisitPhoneTo(Date nextVisitPhoneTo) {
		this.nextVisitPhoneTo = nextVisitPhoneTo;
	}

	/**
	 * @return the visitFrom
	 */
	public Date getVisitFrom() {
		return visitFrom;
	}

	/**
	 * @param visitFrom
	 *            the visitFrom to set
	 */
	public void setVisitFrom(Date visitFrom) {
		this.visitFrom = visitFrom;
	}

	/**
	 * @return the visitTo
	 */
	public Date getVisitTo() {
		return visitTo;
	}

	/**
	 * @param visitTo
	 *            the visitTo to set
	 */
	public void setVisitTo(Date visitTo) {
		this.visitTo = visitTo;
	}

	/**
	 * @return the csAccount
	 */
	public String getCsAccount() {
		return csAccount;
	}

	/**
	 * @param csAccount
	 *            the csAccount to set
	 */
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the industryCode
	 */
	public String getIndustryCode() {
		return industryCode;
	}

	/**
	 * @param industryCode
	 *            the industryCode to set
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * @return the unvisitFlag
	 */
	public Long getUnvisitFlag() {
		return unvisitFlag;
	}

	/**
	 * @param unvisitFlag
	 *            the unvisitFlag to set
	 */
	public void setUnvisitFlag(Long unvisitFlag) {
		this.unvisitFlag = unvisitFlag;
	}

	/**
	 * @return the logDayFrom
	 */
	public Date getLogDayFrom() {
		return logDayFrom;
	}

	/**
	 * @param logDayFrom
	 *            the logDayFrom to set
	 */
	public void setLogDayFrom(Date logDayFrom) {
		this.logDayFrom = logDayFrom;
	}

	/**
	 * @return the logDayTo
	 */
	public Date getLogDayTo() {
		return logDayTo;
	}

	/**
	 * @param logDayTo
	 *            the logDayTo to set
	 */
	public void setLogDayTo(Date logDayTo) {
		this.logDayTo = logDayTo;
	}

	public String getKillFlag() {
		return killFlag;
	}

	public void setKillFlag(String killFlag) {
		this.killFlag = killFlag;
	}

	public Date getSvrBSEndTo() {
		return svrBSEndTo;
	}

	public void setSvrBSEndTo(Date svrBSEndTo) {
		this.svrBSEndTo = svrBSEndTo;
	}

	public Date getSvrBSFromTo() {
		return svrBSFromTo;
	}

	public void setSvrBSFromTo(Date svrBSFromTo) {
		this.svrBSFromTo = svrBSFromTo;
	}

	public Date getOneMonthBS() {
		return oneMonthBS;
	}

	public void setOneMonthBS(Date oneMonthBS) {
		this.oneMonthBS = oneMonthBS;
	}

	public Integer getNoExpired() {
		return noExpired;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setNoExpired(Integer noExpired) {
		this.noExpired = noExpired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}

	public String getPptCode() {
		return pptCode;
	}

	public void setPptCode(String pptCode) {
		this.pptCode = pptCode;
	}

	public String getOutbusiness() {
		return outbusiness;
	}

	public void setOutbusiness(String outbusiness) {
		this.outbusiness = outbusiness;
	}

	
}
