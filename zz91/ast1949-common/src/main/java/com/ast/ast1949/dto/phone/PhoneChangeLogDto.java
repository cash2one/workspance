package com.ast.ast1949.dto.phone;

import java.io.Serializable;

import com.ast.ast1949.domain.phone.PhoneChangeLog;

public class PhoneChangeLogDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private PhoneChangeLog phoneChangeLog;
	private String account;  //公司帐号
	private String companyName; //公司名称
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public PhoneChangeLog getPhoneChangeLog() {
		return phoneChangeLog;
	}
	public void setPhoneChangeLog(PhoneChangeLog phoneChangeLog) {
		this.phoneChangeLog = phoneChangeLog;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
