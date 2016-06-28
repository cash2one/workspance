package com.kl91.domain.dto.company;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import com.kl91.domain.DomainSupport;

public class CompanySolrDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 216053570460333812L;

	@Field
	private Integer id;
	@Field
	private String account; // 帐号
	@Field
	private String companyName;// 公司名
	@Field
	private String industryCode;// 主营类型
	@Field
	private String membershipCode;// 会员类型
	@Field
	private Integer sex;// '性别\n0:男\n1:女'
	@Field
	private String contact;// '联系人' ,
	@Field
	private String password;// '加密密码' ,
	@Field
	private String mobile;// '手机' ,
	@Field
	private Integer isActive;// 是否激活
	@Field
	private String qq;// 'QQ号码' ,
	@Field
	private String email;// '邮箱' ,
	@Field
	private String tel;// '电话' ,
	@Field
	private String fax;// '传真' ,
	@Field
	private String areaCode;// '地区' ,
	@Field
	private String zip;// '邮编' ,
	@Field
	private String address;// '公司地址' ,
	@Field
	private String position;// '职位' ,
	@Field
	private String department;// '部门' ,
	@Field
	private String introduction;// '公司简介' ,
	@Field
	private String business;// '主营产品' ,
	@Field
	private String domain;// '二级域名' ,
	@Field
	private String website;// '企业网站' ,
	@Field
	private Integer numLogin;// '登录次数\nn:第n次登录' ,
	@Field
	private Integer numPass;// 发布次数
	@Field
	private Integer registFlag;// '注册来源\n0:客户注册\n1:后台导入' ,
	@Field
	private Date showTime;// '公司前台显示日期' ,
	@Field
	private Date gmtLastLogin;// '最后一次登录时间' ,
	@Field
	private Date gmtCreated;
	@Field
	private Date gmtModified;
	
	private String highLightTitle;
	
	private String highLightIntro;

	public CompanySolrDto() {
		super();
	}

	public Integer getNumPass() {
		return numPass;
	}

	public void setNumPass(Integer numPass) {
		this.numPass = numPass;
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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getHighLightTitle() {
		return highLightTitle;
	}

	public void setHighLightTitle(String highLightTitle) {
		this.highLightTitle = highLightTitle;
	}

	public String getHighLightIntro() {
		return highLightIntro;
	}

	public void setHighLightIntro(String highLightIntro) {
		this.highLightIntro = highLightIntro;
	}

}