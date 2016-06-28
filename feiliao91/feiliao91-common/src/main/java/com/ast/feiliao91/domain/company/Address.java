package com.ast.feiliao91.domain.company;

import java.util.Date;

/**
 * Address表
 * 
 * @author liujx
 *
 */
public class Address {
	private Integer id;
	private Integer companyId; // 公司编号
	private String name; // 发货人姓名或者收货人姓名
	private String tel;// 电话号码
	private String mobile;// 手机号码
	private String areaCode;// 所在地区编码
	private String address; // 详细地址
	private String zipCode;// 邮编
	private Integer isDefault;// 是否为默认地址：不是 0 ；是 1
	private Integer isDel;// 删除状态：未删除 0 ；删除 1
	private Integer addressType;// 地址类型：收货 0； 发货 1
	private String companyName;// 公司名称
	private String detail; // 订单详情
	private Date gmtCreated;
	private Date gmtModified;

	public Address() {
	}

	public Address(Integer id, Integer isDefault) {
		this.id = id;
		this.isDefault = isDefault;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getAddressType() {
		return addressType;
	}

	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

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

}
