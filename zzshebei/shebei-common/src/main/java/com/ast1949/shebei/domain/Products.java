/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-6 下午05:57:05
 */
package com.ast1949.shebei.domain;

import java.io.Serializable;
import java.util.Date;

public class Products implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer cid;//公司iid
	private Short productsType;//供求类型
	private String categoryCode;//供应类别
	private String title;//产品信息标题
	private String details;//产品详细信息
	private String price;//价格
	private String priceMax;//最大价格
	private String priceUnit;//单位
	private String quantity;//数量
	private String quantityUnit;//数量单位（如个，台）
	private String source;//信息来源
	private String tags;// 标签
	private String area;//地区
	private String quality;//新旧程度
	private Date gmtPublish;//发布时间
	private Date gmtRefresh;//刷新时间
	private Date gmtExpired;//过期时间
	private Date gmtShow;//展示时间
	private String expiredDay;//过期天数
	private Date gmtCreated;//创建时间
	private Date gmtModified;//最后修改时间
	public Products(){
		
	}
	public Products(Integer id, Integer cid, Short productsType,
			String categoryCode, String title, String details,
			String price, String priceMax, String priceUnit,
			String quantity, String quantityUnit, String source, String tags,
			String area, String quality, Date gmtPublish, Date gmtRefresh,
			Date gmtExpired, Date gmtShow, String expiredDay, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.productsType = productsType;
		this.categoryCode = categoryCode;
		this.title = title;
		this.details = details;
		this.price = price;
		this.priceMax = priceMax;
		this.priceUnit = priceUnit;
		this.quantity = quantity;
		this.quantityUnit = quantityUnit;
		this.source = source;
		this.tags = tags;
		this.area = area;
		this.quality = quality;
		this.gmtPublish = gmtPublish;
		this.gmtRefresh = gmtRefresh;
		this.gmtExpired = gmtExpired;
		this.gmtShow = gmtShow;
		this.expiredDay = expiredDay;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Short getProductsType() {
		return productsType;
	}
	public void setProductsType(Short productsType) {
		this.productsType = productsType;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getQuantityUnit() {
		return quantityUnit;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public Date getGmtPublish() {
		return gmtPublish;
	}
	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public Date getGmtRefresh() {
		return gmtRefresh;
	}
	public void setGmtRefresh(Date gmtRefresh) {
		this.gmtRefresh = gmtRefresh;
	}
	public Date getGmtExpired() {
		return gmtExpired;
	}
	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}
	public Date getGmtShow() {
		return gmtShow;
	}
	public void setGmtShow(Date gmtShow) {
		this.gmtShow = gmtShow;
	}
	public String getExpiredDay() {
		return expiredDay;
	}
	public void setExpiredDay(String expiredDay) {
		this.expiredDay = expiredDay;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
