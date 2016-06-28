/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010 下午05:39:48
 */
package com.ast.ast1949.dto.company;

import java.util.List;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.products.ProductsDO;

/**
 * 公司详细信息对象
 * 
 * @author Ryan
 * 
 */

public class CompanyDto extends DomainSupport {

	private static final long serialVersionUID = 1L;
	/** 公司基本信息 **/
	private Company company;
	/** 公司联系信息 **/
	private List<CompanyAccount> accountList;
	private CompanyAccount account;

	private String categoryGardenName;
	private String categoryGardenShorterName;
	private String industryLabel;
	private String serviceLabel;
	private String areaLabel;
	private String businessLabel;
	private String membershipLabel;
	private String classifiedLabel;
	private String regfromLabel;

	// 关于地区信息
	private String country; // 国家
	private String province;
	private String city;
	
	private ProductsDO buyPro;
	private ProductsDO offerPro;

	private Boolean isLDB; // 来电宝 用户标志
	
	
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the accountList
	 */
	public List<CompanyAccount> getAccountList() {
		return accountList;
	}

	/**
	 * @param accountList
	 *            the accountList to set
	 */
	public void setAccountList(List<CompanyAccount> accountList) {
		this.accountList = accountList;
	}

	/**
	 * @return the categoryGardenName
	 */
	public String getCategoryGardenName() {
		return categoryGardenName;
	}

	/**
	 * @param categoryGardenName
	 *            the categoryGardenName to set
	 */
	public void setCategoryGardenName(String categoryGardenName) {
		this.categoryGardenName = categoryGardenName;
	}

	/**
	 * @return the categoryGardenShorterName
	 */
	public String getCategoryGardenShorterName() {
		return categoryGardenShorterName;
	}

	/**
	 * @param categoryGardenShorterName
	 *            the categoryGardenShorterName to set
	 */
	public void setCategoryGardenShorterName(String categoryGardenShorterName) {
		this.categoryGardenShorterName = categoryGardenShorterName;
	}

	/**
	 * @return the industryLabel
	 */
	public String getIndustryLabel() {
		return industryLabel;
	}

	/**
	 * @param industryLabel
	 *            the industryLabel to set
	 */
	public void setIndustryLabel(String industryLabel) {
		this.industryLabel = industryLabel;
	}

	/**
	 * @return the serviceLabel
	 */
	public String getServiceLabel() {
		return serviceLabel;
	}

	/**
	 * @param serviceLabel
	 *            the serviceLabel to set
	 */
	public void setServiceLabel(String serviceLabel) {
		this.serviceLabel = serviceLabel;
	}

	/**
	 * @return the areaLabel
	 */
	public String getAreaLabel() {
		return areaLabel;
	}

	/**
	 * @param areaLabel
	 *            the areaLabel to set
	 */
	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}

	/**
	 * @return the businessLabel
	 */
	public String getBusinessLabel() {
		return businessLabel;
	}

	/**
	 * @param businessLabel
	 *            the businessLabel to set
	 */
	public void setBusinessLabel(String businessLabel) {
		this.businessLabel = businessLabel;
	}

	/**
	 * @return the membershipLabel
	 */
	public String getMembershipLabel() {
		return membershipLabel;
	}

	/**
	 * @param membershipLabel
	 *            the membershipLabel to set
	 */
	public void setMembershipLabel(String membershipLabel) {
		this.membershipLabel = membershipLabel;
	}

	/**
	 * @return the classifiedLabel
	 */
	public String getClassifiedLabel() {
		return classifiedLabel;
	}

	/**
	 * @param classifiedLabel
	 *            the classifiedLabel to set
	 */
	public void setClassifiedLabel(String classifiedLabel) {
		this.classifiedLabel = classifiedLabel;
	}

	/**
	 * @return the regfromLabel
	 */
	public String getRegfromLabel() {
		return regfromLabel;
	}

	/**
	 * @param regfromLabel
	 *            the regfromLabel to set
	 */
	public void setRegfromLabel(String regfromLabel) {
		this.regfromLabel = regfromLabel;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the account
	 */
	public CompanyAccount getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public ProductsDO getBuyPro() {
		return buyPro;
	}

	public ProductsDO getOfferPro() {
		return offerPro;
	}

	public void setBuyPro(ProductsDO buyPro) {
		this.buyPro = buyPro;
	}

	public void setOfferPro(ProductsDO offerPro) {
		this.offerPro = offerPro;
	}

	public Boolean getIsLDB() {
		return isLDB;
	}

	public void setIsLDB(Boolean isLDB) {
		this.isLDB = isLDB;
	}
	

}
