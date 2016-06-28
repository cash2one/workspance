/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-24 下午01:03:58
 */
package com.ast1949.shebei.dto.data;

import java.io.Serializable;

public class CompanyMakeMap implements Serializable {
	
	/**
	 * zz91二手设备(公司信息导入类)
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryCode;
	private Integer name;
	private Integer contact;
	private Short sex;
	private Integer mobile;
	private Integer phone;
	private Integer fax;
	private Integer address;
	private Integer addressZip;
	private Integer details;
	private Short mainBuy;
	private Short mainSupply;
	private Integer mainProductBuy;
	private Integer mainProductSupply;
	private Integer account;
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public Integer getName() {
		return name;
	}
	public void setName(Integer name) {
		this.name = name;
	}
	public Integer getContact() {
		return contact;
	}
	public void setContact(Integer contact) {
		this.contact = contact;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Integer getMobile() {
		return mobile;
	}
	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public Integer getFax() {
		return fax;
	}
	public void setFax(Integer fax) {
		this.fax = fax;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	public Integer getAddressZip() {
		return addressZip;
	}
	public void setAddressZip(Integer addressZip) {
		this.addressZip = addressZip;
	}
	public Integer getDetails() {
		return details;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Short getMainBuy() {
		return mainBuy;
	}
	public void setMainBuy(Short mainBuy) {
		this.mainBuy = mainBuy;
	}
	public Short getMainSupply() {
		return mainSupply;
	}
	public void setMainSupply(Short mainSupply) {
		this.mainSupply = mainSupply;
	}
	public Integer getMainProductBuy() {
		return mainProductBuy;
	}
	public void setMainProductBuy(Integer mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public Integer getMainProductSupply() {
		return mainProductSupply;
	}
	public void setMainProductSupply(Integer mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	
}
