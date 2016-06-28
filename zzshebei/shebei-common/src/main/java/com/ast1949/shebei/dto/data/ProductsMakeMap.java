/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-24 下午01:03:45
 */
package com.ast1949.shebei.dto.data;

import java.io.Serializable;

public class ProductsMakeMap implements Serializable{

	/**
	 * zz91二手设备(供求信息导入类)
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer account;
	private String categoryCode;
	private Short productType;
	private Integer title;
	private Integer details;
	private Integer price;
	private Integer priceMax;
	private Integer priceUnit;
	private Integer quantity;
	private Integer quantityUnit;
	private Integer source;
	private Integer tags;
	private Integer area;
	private Integer quality;
	private Integer gmtPublish;
	private Integer gmtExpired;
	
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public Short getProductType() {
		return productType;
	}
	public void setProductType(Short productType) {
		this.productType = productType;
	}
	public Integer getTitle() {
		return title;
	}
	public void setTitle(Integer title) {
		this.title = title;
	}
	public Integer getDetails() {
		return details;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(Integer priceMax) {
		this.priceMax = priceMax;
	}
	public Integer getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(Integer quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getTags() {
		return tags;
	}
	public void setTags(Integer tags) {
		this.tags = tags;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getQuality() {
		return quality;
	}
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	public Integer getGmtPublish() {
		return gmtPublish;
	}
	public void setGmtPublish(Integer gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public Integer getGmtExpired() {
		return gmtExpired;
	}
	public void setGmtExpired(Integer gmtExpired) {
		this.gmtExpired = gmtExpired;
	}
	
}
