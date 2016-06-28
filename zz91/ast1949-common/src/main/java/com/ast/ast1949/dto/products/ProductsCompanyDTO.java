package com.ast.ast1949.dto.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsCompanyDTO extends DomainSupport {

	private static final long serialVersionUID = 1L;
	private String keywordsBuiedTypeCode;//购买关键字类型（标王类型）
	private String keywordsBuiedType;
	private Integer productId;//供求ID
	private String productTypeCode;//供求类型
	private String buyOrSale;
	private String productTitle;//供求标题
	private String productDetails;//供求内容
	private String price;
	private String priceUnit;
	private String productPictrueAddress;//图片地址
	private Date refreshTime;//供求刷新时间
	private Integer companyId;//公司ID号
	private String companyName;//公司名称
	private String membershipCode;//公司会员类类型
	private String membership;
	private String areaCode;
	private String city;
	private String provinceCode;
	private String province;

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

	public String getKeywordsBuiedTypeCode() {
		return keywordsBuiedTypeCode;
	}

	public void setKeywordsBuiedTypeCode(String keywordsBuiedTypeCode) {
		this.keywordsBuiedTypeCode = keywordsBuiedTypeCode;
	}

	public String getKeywordsBuiedType() {
		return keywordsBuiedType;
	}

	public void setKeywordsBuiedType(String keywordsBuiedType) {
		this.keywordsBuiedType = keywordsBuiedType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getBuyOrSale() {
		return buyOrSale;
	}

	public void setBuyOrSale(String buyOrSale) {
		this.buyOrSale = buyOrSale;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getProductPictrueAddress() {
		return productPictrueAddress;
	}

	public void setProductPictrueAddress(String productPictrueAddress) {
		this.productPictrueAddress = productPictrueAddress;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
