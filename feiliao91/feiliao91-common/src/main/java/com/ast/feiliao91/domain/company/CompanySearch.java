/**
 * @author shiqp
 * @date 2016-02-25
 */
package com.ast.feiliao91.domain.company;

public class CompanySearch {
	private String companyName;
	private Integer creditType;//认证类型:0个人1公司
	private Integer creditStatus;//认证状态，默认为0未认证1认证中2已认证3审核未通过
	private String account;
	private String mobile;
	private String email;
	private String area;
	private String isDel;
	private String companyCode;
	private String dateType;// 时间类型
	private String from;//开始时间
	private String to;//结束时间
	
	public String getcompanyName() {
		return companyName;
	}

	public void setcompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Integer getcreditStatus() {
		return creditStatus;
	}

	public void setcreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Integer getCreditType() {
		return creditType;
	}

	public void setCreditType(Integer creditType) {
		this.creditType = creditType;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
