/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-30 下午04:17:21
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * 定制信息
 *
 * @author Ryan(rxm1025@gmail.com)  
 *
 */
public class SubscribeDO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer companyId;
	private String account;
	private String keywords;//定制关键字，订制供求使用
	private Boolean isSearchByArea;	//是否按地区筛选，订制供求使用
	private String areaCode;//订制供求使用
	private String productsTypeCode;//供求类型,来自category表,，订制供求使用
	private Integer priceTypeId;//主类别，订制报价使用,来自price_category表
	private Integer priceAssistTypeId;//辅助类别，来自price_category
	private Boolean isSendByEmail;//是否邮件提醒
	private String subscribeType;//0:供求,1:报价
	private Date gmtCreated;
	private Date gmtModified;
    private String isMustSee;
    private String province;
    private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Boolean getIsSearchByArea() {
		return isSearchByArea;
	}

	public void setIsSearchByArea(Boolean isSearchByArea) {
		this.isSearchByArea = isSearchByArea;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public Integer getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Integer priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public Boolean getIsSendByEmail() {
		return isSendByEmail;
	}

	public void setIsSendByEmail(Boolean isSendByEmail) {
		this.isSendByEmail = isSendByEmail;
	}

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
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

	public void setPriceAssistTypeId(Integer priceAssistTypeId) {
		this.priceAssistTypeId = priceAssistTypeId;
	}

	public Integer getPriceAssistTypeId() {
		return priceAssistTypeId;
	}

	/**
	 * @return the isMustSee
	 */
	public String getIsMustSee() {
		return isMustSee;
	}

	/**
	 * @param isMustSee the isMustSee to set
	 */
	public void setIsMustSee(String isMustSee) {
		this.isMustSee = isMustSee;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
