/*
 * 文件名称：RegistDto.java
 * 创建者　：陈庆林
 * 创建时间：2012-7-5 下午03:35:28
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.comp;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层[数据操作DAO层\业务逻辑Service层\业务控制Controller层]
 * 模块描述：[对此类的描述，....................
 * 　　　　　.............可以引用系统设计中的描述]
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　陈庆林　　　　　　　1.0.0　　　　　创建类文件
 */
@SuppressWarnings("serial")
public class RegistDto implements Serializable {
	private Integer userid;
	private String linkname;
	private short sex;
	private String mobile;
	private String phoneCountry;
	private String phoneArea;
	private String phone;
	private String faxCountry;
	private String faxArea;
	private String fax;
	private String email;
	private String linkother;
	private String department;
	private String office;
	private String company;
	private String provinceCode;
	private String areaCode;
	private String address;
	private String industryCode;
	private String details;
	private Short mainSupply;
	private String mainProductSupply;
	private Short mainBuy;
	private String mainProductBuy;
	private String business;
	private String comurl;
	private String regcapital;
	private String legal;
	public RegistDto(){
		
	}
	public Integer getUserid() {
		return userid;
	}
	public String getLinkname() {
		return linkname;
	}
	public short getSex() {
		return sex;
	}
	public String getMobile() {
		return mobile;
	}
	public String getPhoneCountry() {
		return phoneCountry;
	}
	public String getPhoneArea() {
		return phoneArea;
	}
	public String getPhone() {
		return phone;
	}
	public String getFaxCountry() {
		return faxCountry;
	}
	public String getFaxArea() {
		return faxArea;
	}
	public String getFax() {
		return fax;
	}
	public String getEmail() {
		return email;
	}
	public String getLinkother() {
		return linkother;
	}
	public String getDepartment() {
		return department;
	}
	public String getOffice() {
		return office;
	}
	public String getCompany() {
		return company;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public String getAddress() {
		return address;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public String getDetails() {
		return details;
	}
	public Short getMainSupply() {
		return mainSupply;
	}
	public String getMainProductSupply() {
		return mainProductSupply;
	}
	public Short getMainBuy() {
		return mainBuy;
	}
	public String getMainProductBuy() {
		return mainProductBuy;
	}
	public String getBusiness() {
		return business;
	}
	public String getComurl() {
		return comurl;
	}
	public String getRegcapital() {
		return regcapital;
	}
	public String getLegal() {
		return legal;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public void setSex(short sex) {
		this.sex = sex;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setPhoneCountry(String phoneCountry) {
		this.phoneCountry = phoneCountry;
	}
	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setFaxCountry(String faxCountry) {
		this.faxCountry = faxCountry;
	}
	public void setFaxArea(String faxArea) {
		this.faxArea = faxArea;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setLinkother(String linkother) {
		this.linkother = linkother;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setMainSupply(Short mainSupply) {
		this.mainSupply = mainSupply;
	}
	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
	}
	public void setMainBuy(Short mainBuy) {
		this.mainBuy = mainBuy;
	}
	public void setMainProductBuy(String mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public void setComurl(String comurl) {
		this.comurl = comurl;
	}
	public void setRegcapital(String regcapital) {
		this.regcapital = regcapital;
	}
	public void setLegal(String legal) {
		this.legal = legal;
	}
}
