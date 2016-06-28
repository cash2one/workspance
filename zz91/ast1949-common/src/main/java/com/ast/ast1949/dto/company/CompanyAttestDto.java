package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.company.CompanyAttest;
/**
 * 
 * @author zhozuk
 * 2014-1-8
 *
 */
public class CompanyAttestDto extends DomainSupport {

	private static final long serialVersionUID = 1L;
	//认证信息
	private CompanyAttest companyAttest;
	//公司名称
	private String name;
	private String membershipCode;
	
	public CompanyAttest getCompanyAttest() {
		return companyAttest;
	}
	public void setCompanyAttest(CompanyAttest companyAttest) {
		this.companyAttest = companyAttest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMembershipCode() {
		return membershipCode;
	}
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}
}