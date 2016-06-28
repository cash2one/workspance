/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-5-19
 */
public class CrmCompanySvrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Company company;
	private CrmCompanySvr crmCompanySvr;
	private CompanyAccount account;
	
	private String svrName;
	private Integer svrPrice;
	private String svrRemark;
	private String keywords;
	private String amount;
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getSvrPrice() {
		return svrPrice;
	}

	public void setSvrPrice(Integer svrPrice) {
		this.svrPrice = svrPrice;
	}

	public String getSvrRemark() {
		return svrRemark;
	}

	public void setSvrRemark(String svrRemark) {
		this.svrRemark = svrRemark;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CrmCompanySvr getCrmCompanySvr() {
		return crmCompanySvr;
	}

	public void setCrmCompanySvr(CrmCompanySvr crmCompanySvr) {
		this.crmCompanySvr = crmCompanySvr;
	}

	public String getSvrName() {
		return svrName;
	}

	public void setSvrName(String svrName) {
		this.svrName = svrName;
	}

	/**
	 * @return the account
	 */
	public CompanyAccount getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
