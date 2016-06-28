package com.kl91.domain.company;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class Company extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216053570460333812L;

	private Integer id;
	private String account; // 帐号
	private String companyName;// 公司名
	private String industryCode;// 主营类型
	private String membershipCode;// 会员类型
	private Integer sex;// '性别\n0:男\n1:女'
	private String contact;// '联系人' ,
	private String password;// '加密密码' ,
	private String mobile;// '手机' ,
	private String qq;// 'QQ号码' ,
	private String email;// '邮箱' ,
	private String tel;// '电话' ,
	private String fax;// '传真' ,
	private String areaCode;// '地区' ,
	private String zip;// '邮编' ,
	private String address;// '公司地址' ,
	private String position;// '职位' ,
	private String department;// '部门' ,
	private String introduction;// '公司简介' ,
	private String business;// '主营产品' ,
	private String domain;// '二级域名' ,
	private String website;// '企业网站' ,
	private Integer numLogin;// '登录次数\nn:第n次登录' ,
	private Integer registFlag;// '注册来源\n0:客户注册\n1:后台导入' ,
	private Date showTime;// '公司前台显示日期' ,
	private Date gmtLastLogin;// '最后一次登录时间' ,
	private Date gmtCreated;
	private Date gmtModified;
	
	public Company() {
		super();
	}
	
	public Company(Integer id, String account, String companyName,
			String industryCode, String membershipCode, Integer sex,
			String contact, String password, String mobile, String qq,
			String email, String tel, String fax, String areaCode, String zip,
			String address, String position, String department,
			String introduction, String business, String domain,
			String website, Integer numLogin, Integer registFlag,
			Date showTime, Date gmtLastLogin, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.account = account;
		this.companyName = companyName;
		this.industryCode = industryCode;
		this.membershipCode = membershipCode;
		this.sex = sex;
		this.contact = contact;
		this.password = password;
		this.mobile = mobile;
		this.qq = qq;
		this.email = email;
		this.tel = tel;
		this.fax = fax;
		this.areaCode = areaCode;
		this.zip = zip;
		this.address = address;
		this.position = position;
		this.department = department;
		this.introduction = introduction;
		this.business = business;
		this.domain = domain;
		this.website = website;
		this.numLogin = numLogin;
		this.registFlag = registFlag;
		this.showTime = showTime;
		this.gmtLastLogin = gmtLastLogin;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getNumLogin() {
		return numLogin;
	}

	public void setNumLogin(Integer numLogin) {
		this.numLogin = numLogin;
	}

	public Integer getRegistFlag() {
		return registFlag;
	}

	public void setRegistFlag(Integer registFlag) {
		this.registFlag = registFlag;
	}

	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	public Date getGmtLastLogin() {
		return gmtLastLogin;
	}

	public void setGmtLastLogin(Date gmtLastLogin) {
		this.gmtLastLogin = gmtLastLogin;
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