/**
 * 
 */
package com.zz91.ep.dto.data;

import java.io.Serializable;

/**
 * @author (qizj@zz91.net)
 *
 * created by 2012-3-9
 */
public class TradeBuyMakeMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer title;
	private Integer details;
	private Integer quantity;
	private Integer quantityUntis;
	private Integer require;
	private String categoryCode;
	private Integer photoUrl;
	private Integer randomPublishDay;
	private Integer gmtExpired;
	
	public void setTitle(Integer title) {
		this.title = title;
	}
	public Integer getTitle() {
		return title;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Integer getDetails() {
		return details;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setRequire(Integer require) {
		this.require = require;
	}
	public Integer getRequire() {
		return require;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setPhotoUrl(Integer photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Integer getPhotoUrl() {
		return photoUrl;
	}
	public void setRandomPublishDay(Integer randomPublishDay) {
		this.randomPublishDay = randomPublishDay;
	}
	public Integer getRandomPublishDay() {
		return randomPublishDay;
	}
	public void setQuantityUntis(Integer quantityUntis) {
		this.quantityUntis = quantityUntis;
	}
	public Integer getQuantityUntis() {
		return quantityUntis;
	}
	public void setGmtExpired(Integer gmtExpired) {
		this.gmtExpired = gmtExpired;
	}
	public Integer getGmtExpired() {
		return gmtExpired;
	}
	
}
