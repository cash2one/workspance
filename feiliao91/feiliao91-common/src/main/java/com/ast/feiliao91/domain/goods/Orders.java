/**
 * @author shiqp
 * @date 2016-01-30
 */
package com.ast.feiliao91.domain.goods;

import java.util.Date;

public class Orders {
	private Integer id;
	private Integer goodsId;
	private Integer sellCompanyId;
	private String orderNo;
	private String details;
	private String buyMessage;
	private float buyPricePay;
	private String logisticsNo;
	private float buyPriceLogistics;
	private String buyInvoiceTitle;
	private Float buyQuantity;
	private Integer buyCompanyId;
	private Integer isDel;
	private Integer status;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public Integer getSellCompanyId() {
		return sellCompanyId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getDetails() {
		return details;
	}

	public String getBuyMessage() {
		return buyMessage;
	}

	public float getBuyPricePay() {
		return buyPricePay;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public float getBuyPriceLogistics() {
		return buyPriceLogistics;
	}

	public String getBuyInvoiceTitle() {
		return buyInvoiceTitle;
	}

	public Float getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Float buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Integer getBuyCompanyId() {
		return buyCompanyId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public Integer getStatus() {
		return status;
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

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public void setSellCompanyId(Integer sellCompanyId) {
		this.sellCompanyId = sellCompanyId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setBuyMessage(String buyMessage) {
		this.buyMessage = buyMessage;
	}

	public void setBuyPricePay(float buyPricePay) {
		this.buyPricePay = buyPricePay;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public void setBuyPriceLogistics(float buyPriceLogistics) {
		this.buyPriceLogistics = buyPriceLogistics;
	}

	public void setBuyInvoiceTitle(String buyInvoiceTitle) {
		this.buyInvoiceTitle = buyInvoiceTitle;
	}

	public void setBuyCompanyId(Integer buyCompanyId) {
		this.buyCompanyId = buyCompanyId;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
