/**
 * 
 */
package com.zz91.ep.dto.data;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.net)
 *
 * created by 2011-10-25
 */
public class TradeSupplyMakeMap implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer title;
	private Integer details;
	private Integer property;
	private Integer photoUrl;
	private Integer price;
	private Integer totalNum;
	private Integer areaName;
	private String categoryCode;
	private Integer randomPublishDay;
	private Integer mainBrand;
	
	
	/**
	 * @return the title
	 */
	public Integer getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(Integer title) {
		this.title = title;
	}
	/**
	 * @return the details
	 */
	public Integer getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(Integer details) {
		this.details = details;
	}
	/**
	 * @return the property
	 */
	public Integer getProperty() {
		return property;
	}
	/**
	 * @param property the property to set
	 */
	public void setProperty(Integer property) {
		this.property = property;
	}
	/**
	 * @return the photoUrl
	 */
	public Integer getPhotoUrl() {
		return photoUrl;
	}
	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(Integer photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}
	/**
	 * @return the totalNum
	 */
	public Integer getTotalNum() {
		return totalNum;
	}
	/**
	 * @param totalNum the totalNum to set
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	/**
	 * @return the areaName
	 */
	public Integer getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(Integer areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * @return the randomPublishDay
	 */
	public Integer getRandomPublishDay() {
		return randomPublishDay;
	}
	/**
	 * @param randomPublishDay the randomPublishDay to set
	 */
	public void setRandomPublishDay(Integer randomPublishDay) {
		this.randomPublishDay = randomPublishDay;
	}
	/**
	 * @return the mainBrand
	 */
	public Integer getMainBrand() {
		return mainBrand;
	}
	/**
	 * @param mainBrand the mainBrand to set
	 */
	public void setMainBrand(Integer mainBrand) {
		this.mainBrand = mainBrand;
	}
	
}
