package com.ast.ast1949.domain.phone;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 *	author:kongsj
 *	date:2013-7-3
 */
public class Phone extends DomainSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3666850824213771364L;
	
	public Integer companyId;
	public String account;
	public String tel; //400号码
	public String keywords;
	public Integer customerId;
	public String accessToken;
	public String amount;
	public String balance;
	public Date gmtOpen;
	public Date gmtCreated;
	public Date gmtModified;
	public String frontTel;
	
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
	

}
