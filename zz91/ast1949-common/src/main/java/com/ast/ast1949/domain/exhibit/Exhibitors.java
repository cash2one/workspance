package com.ast.ast1949.domain.exhibit;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class Exhibitors extends DomainSupport {
	/**
	 * 展会参展人员表
	 */
	private static final long serialVersionUID = 1L;
	private String companyName;//公司名称
	private String name;	//姓名
	private String sex;//0: 男 1;女
	private String job;
	private String area;
	private String address;
	private String addressZip;//邮编
	private String tel;
	private String fex;//传真
	private String mobile;//手机号码
	private String email;//邮箱
	private String website;//公司网址
	private String type;//0:展商 1:观展
	private Date gmtCreated;
	private Date gmtModified;
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFex() {
		return fex;
	}
	public void setFex(String fex) {
		this.fex = fex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
