package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-7-3
 */
public class Phone extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3666850824213771364L;

	private Integer companyId;
	private String account;
	private String tel; // 400号码
	private String keywords;
	private Integer customerId;
	private String accessToken;
	private String amount;
	private String balance;
	private Date gmtOpen;
	private Date gmtCreated;
	private Date gmtModified;
	private String frontTel;
	private Integer phoneSort;
	private String mobile;
	private Integer expireFlag;

	private String monthFeeFrom; // 月消费始
	private String monthFeeTo; // 月消费末
	private Date monthFrom; // 当前月始
	private Date monthTo; // 当前月末
	
	private String email;
	private String contact;
	
	private String svrEnd;//服务费收取时间
	
	private String photoCover;//首页封面图片


	public String getPhotoCover() {
		return photoCover;
	}

	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}

	public String getSvrEnd() {
		return svrEnd;
	}

	public void setSvrEnd(String svrEnd) {
		this.svrEnd = svrEnd;
	}

	public Integer getExpireFlag() {
		return expireFlag;
	}

	public void setExpireFlag(Integer expireFlag) {
		this.expireFlag = expireFlag;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getAccount() {
		return account;
	}

	public String getTel() {
		return tel;
	}

	public String getKeywords() {
		return keywords;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAmount() {
		return amount;
	}

	public String getBalance() {
		return balance;
	}

	public Date getGmtOpen() {
		return gmtOpen;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setGmtOpen(Date gmtOpen) {
		this.gmtOpen = gmtOpen;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getFrontTel() {
		return frontTel;
	}

	public void setFrontTel(String frontTel) {
		this.frontTel = frontTel;
	}
	
	public Integer getPhoneSort() {
		return phoneSort;
	}

	public void setPhoneSort(Integer phoneSort) {
		this.phoneSort = phoneSort;
	}

	public Date getMonthFrom() {
		return monthFrom;
	}

	public void setMonthFrom(Date monthFrom) {
		this.monthFrom = monthFrom;
	}

	public Date getMonthTo() {
		return monthTo;
	}

	public void setMonthTo(Date monthTo) {
		this.monthTo = monthTo;
	}

	public String getMonthFeeFrom() {
		return monthFeeFrom;
	}

	public void setMonthFeeFrom(String monthFeeFrom) {
		this.monthFeeFrom = monthFeeFrom;
	}

	public String getMonthFeeTo() {
		return monthFeeTo;
	}

	public void setMonthFeeTo(String monthFeeTo) {
		this.monthFeeTo = monthFeeTo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	

}
