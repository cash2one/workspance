/**
 * @author shiqp
 * @date 2016-01--31
 */
package com.ast.feiliao91.domain.goods;

import java.util.Date;

public class Shopping {
	private Integer id;
	private  Integer buyCompanyId;
	private Integer sellCompanyId;
	private Integer goodId;
	private String title;
	private String attributes;
	private String price;
	private Integer hasTax;
	private String number;
	private String money;
	private String picAddress;
	private String serviceCodeList;
	private Integer isDel;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBuyCompanyId() {
		return buyCompanyId;
	}

	public void setBuyCompanyId(Integer buyCompanyId) {
		this.buyCompanyId = buyCompanyId;
	}

	public Integer getSellCompanyId() {
		return sellCompanyId;
	}

	public void setSellCompanyId(Integer sellCompanyId) {
		this.sellCompanyId = sellCompanyId;
	}

	public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getHasTax() {
		return hasTax;
	}

	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public String getServiceCodeList() {
		return serviceCodeList;
	}

	public void setServiceCodeList(String serviceCodeList) {
		this.serviceCodeList = serviceCodeList;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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
