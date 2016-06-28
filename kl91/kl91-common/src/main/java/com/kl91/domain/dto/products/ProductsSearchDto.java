package com.kl91.domain.dto.products;

import com.kl91.domain.DomainSupport;

public class ProductsSearchDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 681757610905434263L;
	private Integer deletedFlag;
	private Integer checkedFlag;
	private Integer publishFlag;
	private Integer imptFlag;
	private String keywords;
	private String productsTypeCode;
	private String areaCode;
	private String typeCode;
	private Integer minprice;
	private Integer maxPrice;
	private Integer cid;

	public Integer getCheckedFlag() {
		return checkedFlag;
	}

	public void setCheckedFlag(Integer checkedFlag) {
		this.checkedFlag = checkedFlag;
	}

	public Integer getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(Integer publishFlag) {
		this.publishFlag = publishFlag;
	}

	public Integer getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(Integer deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getMinprice() {
		return minprice;
	}

	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getImptFlag() {
		return imptFlag;
	}

	public void setImptFlag(Integer imptFlag) {
		this.imptFlag = imptFlag;
	}

}
