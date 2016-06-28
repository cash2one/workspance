/*
 * 文件名称：IbdCompany.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：行业买家库类别实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class IbdCompany implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// ID
	private String categoryCode;// 类别
	private String photoCover;// 图片
	private String name;// 名称
	private String details;// 详细
	private String industry;// 行业
	private String mainProduct;// 主营产品
	private String enterpriseType;// 企业类型
	private String legal;// 法人
	private String funds;// 注册资金
	private String address;// 地址
	private String employeeNum;// 员工人数
	private String registerArea;// 注册地区
	private String registerTime;// 注册时间
	private String businessModel;// 经营模式
	private String contact;// 联系方式
	private String sex;// 性别
	private String position;// 职位
	private String phone;// 座机
	private String mobile;// 手机
	private String fax;// 传真
	private String addressZip;// 地址邮编
	private String email;// 邮箱
	private String domain;// 域名
	private Integer delStatus;// 删除标记
	private Date gmt_created;// 创建时间
	private Date gmt_modified;// 更新时间
	/**
	 * id的Getter方法
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * id的Setter方法
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * categoryCode的Getter方法
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * categoryCode的Setter方法
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * name的Getter方法
	 */
	public String getName() {
		return name;
	}
	/**
	 * name的Setter方法
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * details的Getter方法
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * details的Setter方法
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * industry的Getter方法
	 */
	public String getIndustry() {
		return industry;
	}
	/**
	 * industry的Setter方法
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	/**
	 * mainProduct的Getter方法
	 */
	public String getMainProduct() {
		return mainProduct;
	}
	/**
	 * mainProduct的Setter方法
	 */
	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}
	/**
	 * enterpriseType的Getter方法
	 */
	public String getEnterpriseType() {
		return enterpriseType;
	}
	/**
	 * enterpriseType的Setter方法
	 */
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	/**
	 * legal的Getter方法
	 */
	public String getLegal() {
		return legal;
	}
	/**
	 * legal的Setter方法
	 */
	public void setLegal(String legal) {
		this.legal = legal;
	}
	/**
	 * funds的Getter方法
	 */
	public String getFunds() {
		return funds;
	}
	/**
	 * funds的Setter方法
	 */
	public void setFunds(String funds) {
		this.funds = funds;
	}
	/**
	 * address的Getter方法
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * address的Setter方法
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * employeeNum的Getter方法
	 */
	public String getEmployeeNum() {
		return employeeNum;
	}
	/**
	 * employeeNum的Setter方法
	 */
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	/**
	 * registerArea的Getter方法
	 */
	public String getRegisterArea() {
		return registerArea;
	}
	/**
	 * registerArea的Setter方法
	 */
	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}
	/**
	 * registerTime的Getter方法
	 */
	public String getRegisterTime() {
		return registerTime;
	}
	/**
	 * registerTime的Setter方法
	 */
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * businessModel的Getter方法
	 */
	public String getBusinessModel() {
		return businessModel;
	}
	/**
	 * businessModel的Setter方法
	 */
	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}
	/**
	 * contact的Getter方法
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * contact的Setter方法
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * sex的Getter方法
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * sex的Setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * position的Getter方法
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * position的Setter方法
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * phone的Getter方法
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * phone的Setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * mobile的Getter方法
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * mobile的Setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * fax的Getter方法
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * fax的Setter方法
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * addressZip的Getter方法
	 */
	public String getAddressZip() {
		return addressZip;
	}
	/**
	 * addressZip的Setter方法
	 */
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	/**
	 * email的Getter方法
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * email的Setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * domain的Getter方法
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * domain的Setter方法
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * delStatus的Getter方法
	 */
	public Integer getDelStatus() {
		return delStatus;
	}
	/**
	 * delStatus的Setter方法
	 */
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	/**
	 * gmt_created的Getter方法
	 */
	public Date getGmt_created() {
		return gmt_created;
	}
	/**
	 * gmt_created的Setter方法
	 */
	public void setGmt_created(Date gmt_created) {
		this.gmt_created = gmt_created;
	}
	/**
	 * gmt_modified的Getter方法
	 */
	public Date getGmt_modified() {
		return gmt_modified;
	}
	/**
	 * gmt_modified的Setter方法
	 */
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public String getPhotoCover() {
		return photoCover;
	}
	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}
	
}