/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:19:59
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 公司基本信息
 * 
 * @author Ryan
 * 
 */
public class Company extends DomainSupport {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name; // 客户名称
	private String industryCode; // 主营行业编号
	private String business; // 客户主营业务信息
	private String serviceCode; // 客户服务类型编号
	private String areaCode; // 地区编号
	private String foreignCity;// 地区选外国时的对应城市
	private Integer categoryGardenId; // 园区集散地编号
	private String address;
	private String addressZip;
	private String businessType;// 主营方向,0:两者,1:销售,2:采购
	private String saleDetails;// 当主营方向为销售时填的详细内容
	private String buyDetails;// 当主营方向为采购时填的详细内容
	private String website;
	private String introduction; // 公司简介
	private String tags;
	private String zstFlag;
	private Integer zstYear;
	private String membershipCode; // 客户会员类型编号
	private Integer starSys;
	private Integer star;
	private Integer numVisitMonth;
	private Date gmtVisit;
	private String domain;
	private String domainZz91;
	private String classifiedCode; // 公司归类
	private String regfromCode; // 注册来源编号
	private String isBlock; // 是否禁用
	private Date regtime; // 注册时间
	private Date gmtCreated; // 创建时间
	private Date gmtModified; // 修改时间
	
	private String activeFlag;
	private String title;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 */
	public Company() {
		super();
	}

	/**
	 * @param name
	 * @param industryCode
	 * @param business
	 * @param serviceCode
	 * @param areaCode
	 * @param foreignCity
	 * @param categoryGardenId
	 * @param address
	 * @param addressZip
	 * @param businessType
	 * @param saleDetails
	 * @param buyDetails
	 * @param website
	 * @param introduction
	 * @param tags
	 * @param zstFlag
	 * @param zstYear
	 * @param membershipCode
	 * @param starSys
	 * @param star
	 * @param numVisitMonth
	 * @param gmtVisit
	 * @param domain
	 * @param domainZz91
	 * @param classifiedCode
	 * @param regfromCode
	 * @param isBlock
	 * @param regtime
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public Company(String name, String industryCode, String business,
			String serviceCode, String areaCode, String foreignCity,
			Integer categoryGardenId, String address, String addressZip,
			String businessType, String saleDetails, String buyDetails,
			String website, String introduction, String tags, String zstFlag,
			Integer zstYear, String membershipCode, Integer starSys,
			Integer star, Integer numVisitMonth, Date gmtVisit, String domain,
			String domainZz91, String classifiedCode, String regfromCode,
			String isBlock, Date regtime, Date gmtCreated, Date gmtModified) {
		super();
		this.name = name;
		this.industryCode = industryCode;
		this.business = business;
		this.serviceCode = serviceCode;
		this.areaCode = areaCode;
		this.foreignCity = foreignCity;
		this.categoryGardenId = categoryGardenId;
		this.address = address;
		this.addressZip = addressZip;
		this.businessType = businessType;
		this.saleDetails = saleDetails;
		this.buyDetails = buyDetails;
		this.website = website;
		this.introduction = introduction;
		this.tags = tags;
		this.zstFlag = zstFlag;
		this.zstYear = zstYear;
		this.membershipCode = membershipCode;
		this.starSys = starSys;
		this.star = star;
		this.numVisitMonth = numVisitMonth;
		this.gmtVisit = gmtVisit;
		this.domain = domain;
		this.domainZz91 = domainZz91;
		this.classifiedCode = classifiedCode;
		this.regfromCode = regfromCode;
		this.isBlock = isBlock;
		this.regtime = regtime;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the industryCode
	 */
	public String getIndustryCode() {
		return industryCode;
	}

	/**
	 * @param industryCode
	 *            the industryCode to set
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * @param business
	 *            the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode
	 *            the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the foreignCity
	 */
	public String getForeignCity() {
		return foreignCity;
	}

	/**
	 * @param foreignCity
	 *            the foreignCity to set
	 */
	public void setForeignCity(String foreignCity) {
		this.foreignCity = foreignCity;
	}

	/**
	 * @return the categoryGardenId
	 */
	public Integer getCategoryGardenId() {
		return categoryGardenId;
	}

	/**
	 * @param categoryGardenId
	 *            the categoryGardenId to set
	 */
	public void setCategoryGardenId(Integer categoryGardenId) {
		this.categoryGardenId = categoryGardenId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the addressZip
	 */
	public String getAddressZip() {
		return addressZip;
	}

	/**
	 * @param addressZip
	 *            the addressZip to set
	 */
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType
	 *            the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the saleDetails
	 */
	public String getSaleDetails() {
		return saleDetails;
	}

	/**
	 * @param saleDetails
	 *            the saleDetails to set
	 */
	public void setSaleDetails(String saleDetails) {
		this.saleDetails = saleDetails;
	}

	/**
	 * @return the buyDetails
	 */
	public String getBuyDetails() {
		return buyDetails;
	}

	/**
	 * @param buyDetails
	 *            the buyDetails to set
	 */
	public void setBuyDetails(String buyDetails) {
		this.buyDetails = buyDetails;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction
	 *            the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the zstFlag
	 */
	public String getZstFlag() {
		return zstFlag;
	}

	/**
	 * @param zstFlag
	 *            the zstFlag to set
	 */
	public void setZstFlag(String zstFlag) {
		this.zstFlag = zstFlag;
	}

	/**
	 * @return the zstYear
	 */
	public Integer getZstYear() {
		return zstYear;
	}

	/**
	 * @param zstYear
	 *            the zstYear to set
	 */
	public void setZstYear(Integer zstYear) {
		this.zstYear = zstYear;
	}

	/**
	 * @return the membershipCode
	 */
	public String getMembershipCode() {
		return membershipCode;
	}

	/**
	 * @param membershipCode
	 *            the membershipCode to set
	 */
	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	/**
	 * @return the starSys
	 */
	public Integer getStarSys() {
		return starSys;
	}

	/**
	 * @param starSys
	 *            the starSys to set
	 */
	public void setStarSys(Integer starSys) {
		this.starSys = starSys;
	}

	/**
	 * @return the star
	 */
	public Integer getStar() {
		return star;
	}

	/**
	 * @param star
	 *            the star to set
	 */
	public void setStar(Integer star) {
		this.star = star;
	}

	/**
	 * @return the numVisitMonth
	 */
	public Integer getNumVisitMonth() {
		return numVisitMonth;
	}

	/**
	 * @param numVisitMonth
	 *            the numVisitMonth to set
	 */
	public void setNumVisitMonth(Integer numVisitMonth) {
		this.numVisitMonth = numVisitMonth;
	}

	/**
	 * @return the gmtVisit
	 */
	public Date getGmtVisit() {
		return gmtVisit;
	}

	/**
	 * @param gmtVisit
	 *            the gmtVisit to set
	 */
	public void setGmtVisit(Date gmtVisit) {
		this.gmtVisit = gmtVisit;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the domainZz91
	 */
	public String getDomainZz91() {
		return domainZz91;
	}

	/**
	 * @param domainZz91
	 *            the domainZz91 to set
	 */
	public void setDomainZz91(String domainZz91) {
		this.domainZz91 = domainZz91;
	}

	/**
	 * @return the classifiedCode
	 */
	public String getClassifiedCode() {
		return classifiedCode;
	}

	/**
	 * @param classifiedCode
	 *            the classifiedCode to set
	 */
	public void setClassifiedCode(String classifiedCode) {
		this.classifiedCode = classifiedCode;
	}

	/**
	 * @return the regfromCode
	 */
	public String getRegfromCode() {
		return regfromCode;
	}

	/**
	 * @param regfromCode
	 *            the regfromCode to set
	 */
	public void setRegfromCode(String regfromCode) {
		this.regfromCode = regfromCode;
	}

	/**
	 * @return the isBlock
	 */
	public String getIsBlock() {
		return isBlock;
	}

	/**
	 * @param isBlock
	 *            the isBlock to set
	 */
	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}

	/**
	 * @return the regtime
	 */
	public Date getRegtime() {
		return regtime;
	}

	/**
	 * @param regtime
	 *            the regtime to set
	 */
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}
