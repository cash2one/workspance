package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsRub extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7192176834954389649L;
	private Integer id;
	private Integer productId;
	private Integer minPrice;
	private Integer maxPrice;
	private String productsTypeCode;
	private String tags;
	private String title;
	private String location;
	private String quantity;
	private String quantityUnit;
	private String priceUnit;
	private String source;
	private String specification;
	private String checkPerson;
	private String details;
	private Date refreshTime;
	private Date expiredTime;
	private Date checkTime;
	private Date gmtCreated;
	private Date gmtModified;
	
	private String pic;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getId() {
		return id;
	}

	public Integer getProductId() {
		return productId;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public String getProductsTypeCode() {
		return productsTypeCode;
	}

	public String getTags() {
		return tags;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public String getSource() {
		return source;
	}

	public String getSpecification() {
		return specification;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public String getDetails() {
		return details;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setProductsTypeCode(String productsTypeCode) {
		this.productsTypeCode = productsTypeCode;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
