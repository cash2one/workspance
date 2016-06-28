/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.dto.company;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 *
 */
public class CompanyPriceDTO extends DomainSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CompanyPriceDO companyPriceDO;
	private CategoryDO categoryDO;
	private PageDto page = new PageDto();
	
	private String keywords;
	private String areaCode;
	private String postInDays;
	private String refreshTime;
	private String categoryCompanyPriceCode;
	
	private Integer companyId;
	private String categoryName;
	private String title;
	private String areaName;
	private String provinceName;
	private String province;//省code
	private Integer validTime;
	private String companyName;
	private String domainZz91;
	private String membershipCode;
	
	private Float pricefrom;
	private Float priceto;
	
	private Boolean zhFlag;
	
	

	public String getCategoryCompanyPriceCode() {
		return categoryCompanyPriceCode;
	}

	public void setCategoryCompanyPriceCode(String categoryCompanyPriceCode) {
		this.categoryCompanyPriceCode = categoryCompanyPriceCode;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPostInDays() {
		return postInDays;
	}

	public void setPostInDays(String postInDays) {
		this.postInDays = postInDays;
	}

	/**
	 * @return the categoryDO
	 */
	public CategoryDO getCategoryDO() {
		return categoryDO;
	}

	/**
	 * @param categoryDO the categoryDO to set
	 */
	public void setCategoryDO(CategoryDO categoryDO) {
		this.categoryDO = categoryDO;
	}

	public Float getPricefrom() {
		return pricefrom;
	}

	public void setPricefrom(Float pricefrom) {
		this.pricefrom = pricefrom;
	}

	public Float getPriceto() {
		return priceto;
	}

	public void setPriceto(Float priceto) {
		this.priceto = priceto;
	}

	public CompanyPriceDO getCompanyPriceDO() {
		return companyPriceDO;
	}

	public void setCompanyPriceDO(CompanyPriceDO companyPriceDO) {
		this.companyPriceDO = companyPriceDO;
	}

	public PageDto getPage() {
		return page;
	}

	public void setPage(PageDto page) {
		this.page = page;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Boolean getZhFlag() {
		return zhFlag;
	}

	public void setZhFlag(Boolean zhFlag) {
		this.zhFlag = zhFlag;
	}

	public String getDomainZz91() {
		return domainZz91;
	}

	public void setDomainZz91(String domainZz91) {
		this.domainZz91 = domainZz91;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

}
