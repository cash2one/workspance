package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustCrm;

public class TrustCrmDto {
	private TrustCrm trustCrm;
	private String csAccount;
	private Integer numVisitMonth; // 本月联系次数
	private String companyName; // 公司名
	private Integer buyNum; // 采购单数
	private Integer sellNum; // 供货单数
	private CompanyAccount account;
	private String membershipCode;

	public TrustCrm getTrustCrm() {
		return trustCrm;
	}

	public void setTrustCrm(TrustCrm trustCrm) {
		this.trustCrm = trustCrm;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public String getCsAccount() {
		return csAccount;
	}

	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}

	public Integer getNumVisitMonth() {
		return numVisitMonth;
	}

	public void setNumVisitMonth(Integer numVisitMonth) {
		this.numVisitMonth = numVisitMonth;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public CompanyAccount getAccount() {
		return account;
	}

	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

}
