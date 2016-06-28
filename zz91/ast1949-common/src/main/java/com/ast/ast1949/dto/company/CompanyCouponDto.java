package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.company.CompanyCoupon;

public class CompanyCouponDto {
	private CompanyCoupon companyCoupon;
	private  String companyName;
	private String email;
	private String mobile;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public CompanyCoupon getCompanyCoupon() {
		return companyCoupon;
	}
	public void setCompanyCoupon(CompanyCoupon companyCoupon) {
		this.companyCoupon = companyCoupon;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
