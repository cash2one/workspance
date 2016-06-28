package com.ast.ast1949.dto.company;

import java.util.Date;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2011-7-15
 */
public class CompanyPriceSearchDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer companyId;
	private String title;
	private String price;
	private String provinceName;
	private String areaCode;
	private Date refreshTime;
	private Date postTime;
	private String companyName;
	private String categoryCompanyPriceCode;
	private String isChecked;
	private String categoryName;
	private String keywords;

	private String areaName;
	private String countryName; // 国家
	private String province; // 省code
	private String city; // 城市
	private String minPrice;
	private String maxPrice;
	private String priceUnit;
	private String membershipCode;

	private String timeType;
	private String from;
	private String to;

	private Long searchDate;

	private Integer fromPrice;
	private Integer toPrice;

	public Integer getFromPrice() {
		return fromPrice;
	}

	public Integer getToPrice() {
		return toPrice;
	}

	public void setFromPrice(Integer fromPrice) {
		this.fromPrice = fromPrice;
	}

	public void setToPrice(Integer toPrice) {
		this.toPrice = toPrice;
	}

	public Long getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Long searchDate) {
		this.searchDate = searchDate;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	private Float pricefrom;
	private Float priceto;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getCategoryCompanyPriceCode() {
		return categoryCompanyPriceCode;
	}

	public void setCategoryCompanyPriceCode(String categoryCompanyPriceCode) {
		this.categoryCompanyPriceCode = categoryCompanyPriceCode;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the refreshTime
	 */
	public Date getRefreshTime() {
		return refreshTime;
	}

	/**
	 * @param refreshTime
	 *            the refreshTime to set
	 */
	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
