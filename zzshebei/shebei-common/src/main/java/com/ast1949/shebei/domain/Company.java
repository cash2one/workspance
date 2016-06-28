package com.ast1949.shebei.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 陈庆林
 * 2012-7-24 下午3:21:41
 */
public class Company implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;//公司名称
	private String contact;//联系人
	private Short sex;//性别
	private String mobile;//手机
	private String phone;//电话
	private String fax;//传真
	private String address;//地址
	private String addressZip;//邮编
	private String details;//公司详细信息
	private String categoryCode;//供应类别
	private Short mainBuy;//采购商
	private String mainProductBuy;//主营产品或服务(求购)
	private Short mainSupply;//供应商
	private String mainProductSupply;//主营产品或服务(供应)
	private String account;//账户
	private Date gmtShow;//展示时间
	private Date gmtCreated;//创建时间
	private Date gmtModified;//最后修改时间
	public Company(){
		
	}
	public Company(Integer id, String name, String contact, Short sex,
			String mobile, String phone, String fax, String address,
			String addressZip, String details, String categoryCode,
			Short mainBuy, String mainProductBuy, Short mainSupply,
			String mainProductSupply, String account, Date gmtShow,
			Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.sex = sex;
		this.mobile = mobile;
		this.phone = phone;
		this.fax = fax;
		this.address = address;
		this.addressZip = addressZip;
		this.details = details;
		this.categoryCode = categoryCode;
		this.mainBuy = mainBuy;
		this.mainProductBuy = mainProductBuy;
		this.mainSupply = mainSupply;
		this.mainProductSupply = mainProductSupply;
		this.account = account;
		this.gmtShow = gmtShow;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public Short getMainBuy() {
		return mainBuy;
	}
	public void setMainBuy(Short mainBuy) {
		this.mainBuy = mainBuy;
	}
	public String getMainProductBuy() {
		return mainProductBuy;
	}
	public void setMainProductBuy(String mainProductBuy) {
		this.mainProductBuy = mainProductBuy;
	}
	public Short getMainSupply() {
		return mainSupply;
	}
	public void setMainSupply(Short mainSupply) {
		this.mainSupply = mainSupply;
	}
	public String getMainProductSupply() {
		return mainProductSupply;
	}
	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getGmtShow() {
		return gmtShow;
	}
	public void setGmtShow(Date gmtShow) {
		this.gmtShow = gmtShow;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified(){
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
