package com.ast.ast1949.dto.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CompanyPriceDtoForMyrc extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyId;// 报价的公司ID
	private String companyPriceCategoryCode;// 报价分类编码
	private String companyPriceCategory;// 报价分类名称
	private String title;// 标题
	private String price;// 报价
	private String priceUnit;// 报价单位
	private String isChecked;// 是否审核
	private Date checkTime;// 审核时间
	private Date refreshTime;// 刷新时间
	private Date expiredTime;// 截止有效日期
	private Integer validTime;// 有效天数
	private String areaCode;// 地区编码
	private String countryName;// 地区名 国家名
	private String province;// 省
	private String city;// 市
	private String minPrice;
	private String maxPrice;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyPriceCategoryCode() {
		return companyPriceCategoryCode;
	}

	public void setCompanyPriceCategoryCode(String companyPriceCategoryCode) {
		this.companyPriceCategoryCode = companyPriceCategoryCode;
	}

	public String getCompanyPriceCategory() {
		return companyPriceCategory;
	}

	public void setCompanyPriceCategory(String companyPriceCategory) {
		this.companyPriceCategory = companyPriceCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
