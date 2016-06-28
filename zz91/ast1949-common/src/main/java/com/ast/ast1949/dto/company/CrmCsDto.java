/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-25
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.company.CrmCs;
import com.ast.ast1949.domain.company.CrmCsProfile;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-25
 */
public class CrmCsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Company company;
	private CrmCompanySvr crmCompanySvr;
	private CompanyAccount account;
	private CrmCs crmCs;
	private CrmCsProfile crmCsProfile;
	private String classifiedLabel;
	private String membershipLabel;
	private String areaLabel;
	private Integer pv; //统计pv
	private String gmtLastLogin;//最近登录时间
	public String getGmtLastLogin() {
		return gmtLastLogin;
	}

	public void setGmtLastLogin(String gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
	}

	public CrmCsProfile getCrmCsProfile() {
		return crmCsProfile;
	}

	public void setCrmCsProfile(CrmCsProfile crmCsProfile) {
		this.crmCsProfile = crmCsProfile;
	}
	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	private String csName;
	
	private String areaProvinceLabel;
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the crmCompanySvr
	 */
	public CrmCompanySvr getCrmCompanySvr() {
		return crmCompanySvr;
	}

	/**
	 * @param crmCompanySvr the crmCompanySvr to set
	 */
	public void setCrmCompanySvr(CrmCompanySvr crmCompanySvr) {
		this.crmCompanySvr = crmCompanySvr;
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

	/**
	 * @return the crmCs
	 */
	public CrmCs getCrmCs() {
		return crmCs;
	}

	/**
	 * @param crmCs the crmCs to set
	 */
	public void setCrmCs(CrmCs crmCs) {
		this.crmCs = crmCs;
	}

	/**
	 * @return the classifiedLabel
	 */
	public String getClassifiedLabel() {
		return classifiedLabel;
	}

	/**
	 * @param classifiedLabel the classifiedLabel to set
	 */
	public void setClassifiedLabel(String classifiedLabel) {
		this.classifiedLabel = classifiedLabel;
	}

	/**
	 * @return the membershipLabel
	 */
	public String getMembershipLabel() {
		return membershipLabel;
	}

	/**
	 * @param membershipLabel the membershipLabel to set
	 */
	public void setMembershipLabel(String membershipLabel) {
		this.membershipLabel = membershipLabel;
	}

	/**
	 * @return the areaLabel
	 */
	public String getAreaLabel() {
		return areaLabel;
	}

	/**
	 * @param areaLabel the areaLabel to set
	 */
	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}

	/**
	 * @return the csName
	 */
	public String getCsName() {
		return csName;
	}

	/**
	 * @param csName the csName to set
	 */
	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getAreaProvinceLabel() {
		return areaProvinceLabel;
	}

	public void setAreaProvinceLabel(String areaProvinceLabel) {
		this.areaProvinceLabel = areaProvinceLabel;
	}
	
	
	
}
