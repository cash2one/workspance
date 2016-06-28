package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * 现货促销信息
 * domain author:kongsj 
 * date:2013-3-6
 */
public class SpotPromotions extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1502798684354588417L;

	private Integer spotId;// 现货id
	private Integer productId; // NOT NULL DEFAULT 0 COMMENT 供求图片
	private Integer companyId; // 公司id
	private String title;// varchar(200) NOT NULL DEFAULT  COMMENT 标题
	private Integer price;// int(11) NOT NULL DEFAULT 0 COMMENT 现货价格
	private Integer promotionsPrice;// 促销价格
	private String checkStatus;// 审核状态：n0未审核;n1通过;n2审核不通过
	private Integer quantity; // DEFAULT 0 COMMENT 数量
	private String quantityUnit;// DEFAULT 吨 COMMENT 数量单位
	private String priceUnit;// DEFAULT 元
	private Date gmtCreated;// 
	private Date expiredTime;// 过期时间
	private Date gmtModified;
	public Integer getSpotId() {
		return spotId;
	}
	public String getTitle() {
		return title;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getPromotionsPrice() {
		return promotionsPrice;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public String getQuantityUnit() {
		return quantityUnit;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setSpotId(Integer spotId) {
		this.spotId = spotId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setPromotionsPrice(Integer promotionsPrice) {
		this.promotionsPrice = promotionsPrice;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
