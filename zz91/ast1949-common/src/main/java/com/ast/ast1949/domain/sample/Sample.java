package com.ast.ast1949.domain.sample;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class Sample extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4065535391949723420L;
	private Integer id;
	private Integer companyId;
	private Integer amount;
	private Float weight;
	private Float takePrice;
	private Float sendPrice;
	private String areaCode;
	private Integer isDel;
	private Date gmtCreated;
	private Date gmtModified;
	private Integer isCashDelivery; // 1:支持货到付款  
	
	private String unpassReason;
	
	//非表字段
	private String companyName;
	private String title;
	private String productId;
	private String picCover;
	
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

	public Float getTakePrice() {
		return takePrice;
	}

	public void setTakePrice(Float takePrice) {
		this.takePrice = takePrice;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public Float getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(Float sendPrice) {
		this.sendPrice = sendPrice;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUnpassReason() {
		return unpassReason;
	}

	public void setUnpassReason(String unpassReason) {
		this.unpassReason = unpassReason;
	}

	public String getPicCover() {
		return picCover;
	}

	public void setPicCover(String picCover) {
		this.picCover = picCover;
	}

	public Integer getIsCashDelivery() {
		return isCashDelivery;
	}

	public void setIsCashDelivery(Integer isCashDelivery) {
		this.isCashDelivery = isCashDelivery;
	}
}
