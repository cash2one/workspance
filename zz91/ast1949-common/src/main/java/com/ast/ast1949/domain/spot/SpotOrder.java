package com.ast.ast1949.domain.spot;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-3-25
 */
public class SpotOrder extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7263130736585113014L;
	private Integer companyId; // 订单公司id
	private Integer spotId; // 现货id
	private String orderProductTitle;  //订单标题
	private String orderCompanyName; // 订单公司名
	private String detail; //订单详细
	private String orderStatus; // 订单状态
	private String isDel; // 是否删除
	private Integer price; // 价格
	private Integer quantity; // 数量
	private Integer total; // 总价
	private String priceUnit; //价格单位
	private String quantityUnit; //质量单位
	private String buyMessage; // 买家留言
	private Date expiredTime; //过期时间
	private Date gmtCreated;
	private Date gmtModified;
	public Integer getCompanyId() {
		return companyId;
	}
	public Integer getSpotId() {
		return spotId;
	}
	public String getOrderProductTitle() {
		return orderProductTitle;
	}
	public String getOrderCompanyName() {
		return orderCompanyName;
	}
	public String getDetail() {
		return detail;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getIsDel() {
		return isDel;
	}
	public Integer getPrice() {
		return price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public Integer getTotal() {
		return total;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public String getQuantityUnit() {
		return quantityUnit;
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
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public void setSpotId(Integer spotId) {
		this.spotId = spotId;
	}
	public void setOrderProductTitle(String orderProductTitle) {
		this.orderProductTitle = orderProductTitle;
	}
	public void setOrderCompanyName(String orderCompanyName) {
		this.orderCompanyName = orderCompanyName;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
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
	public String getBuyMessage() {
		return buyMessage;
	}
	public void setBuyMessage(String buyMessage) {
		this.buyMessage = buyMessage;
	}
	
}
