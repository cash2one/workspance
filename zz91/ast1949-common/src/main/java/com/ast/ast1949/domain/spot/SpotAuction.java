package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 现货商城竞拍 供求 author:kongsj date:213-3-12
 */
public class SpotAuction extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7743965874877232974L;
	private Integer spotId;// 现货id
	private Integer companyId;// 公司id
	private Integer productId;// 供求id
	private String checkStatus;
	private Integer price;// 当前价格
	private Integer startPrice;// 起拍价
	private Integer upPrice;// 加价幅度
	private Integer quantity;// 数量
	private String title; // 标题
	private String quantityUnit; // 吨 数量单位
	private String priceUnit; // 元
	private Date expiredTime; // 过期时间
	private Date gmtCreated; // datetime NULL
	private Date gmtModified; // datetime NULL
	public Integer getSpotId() {
		return spotId;
	}
	public Integer getProductId() {
		return productId;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getStartPrice() {
		return startPrice;
	}
	public Integer getUpPrice() {
		return upPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public String getTitle() {
		return title;
	}
	public String getQuantityUnit() {
		return quantityUnit;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setSpotId(Integer spotId) {
		this.spotId = spotId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}
	public void setUpPrice(Integer upPrice) {
		this.upPrice = upPrice;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}
