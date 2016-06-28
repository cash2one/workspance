package com.ast.feiliao91.dto.company;

import java.util.Date;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanyValidate;

public class CompanyValidateDto {
	private CompanyValidate companyValidate;
	private CompanyInfo companyInfo;
	private String companyName;
	private Integer isRegisterSucc;//是否注册成功
	private Date registerSuccTime;//注册成功时间
	private Integer registerUsedTime;//注册使用时间
	public CompanyValidate getCompanyValidate() {
		return companyValidate;
	}
	public void setCompanyValidate(CompanyValidate companyValidate) {
		this.companyValidate = companyValidate;
	}
	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	public Integer getIsRegisterSucc() {
		return isRegisterSucc;
	}
	public void setIsRegisterSucc(Integer isRegisterSucc) {
		this.isRegisterSucc = isRegisterSucc;
	}
	public Date getRegisterSuccTime() {
		return registerSuccTime;
	}
	public void setRegisterSuccTime(Date registerSuccTime) {
		this.registerSuccTime = registerSuccTime;
	}
	public Integer getRegisterUsedTime() {
		return registerUsedTime;
	}
	public void setRegisterUsedTime(Integer registerUsedTime) {
		this.registerUsedTime = registerUsedTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
