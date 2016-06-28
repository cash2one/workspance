/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-14
 */
package com.zz91.ads.count.domain;

import java.io.Serializable;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-14
 */
public class AdPosition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer parentId;
	private String requestUrl;
	private Integer deliveryStyleId;
	private Integer sequence;
	private Integer paymentType;
	private Integer width;
	private Integer height;
	private Integer maxAd;
	private Integer hasExactAd;
	private String deleted;
	
	/**
	 * @param id
	 * @param name
	 * @param parentId
	 * @param requestUrl
	 * @param deliveryStyleId
	 * @param sequence
	 * @param paymentType
	 * @param width
	 * @param height
	 * @param maxAd
	 * @param hasExactAd
	 * @param deleted
	 */
	public AdPosition(Integer id, String name, Integer parentId,
			String requestUrl, Integer deliveryStyleId, Integer sequence,
			Integer paymentType, Integer width, Integer height, Integer maxAd,
			Integer hasExactAd, String deleted) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.requestUrl = requestUrl;
		this.deliveryStyleId = deliveryStyleId;
		this.sequence = sequence;
		this.paymentType = paymentType;
		this.width = width;
		this.height = height;
		this.maxAd = maxAd;
		this.hasExactAd = hasExactAd;
		this.deleted = deleted;
	}
	/**
	 * 
	 */
	public AdPosition() {
		super();
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	/**
	 * @return the deliveryStyleId
	 */
	public Integer getDeliveryStyleId() {
		return deliveryStyleId;
	}
	/**
	 * @param deliveryStyleId the deliveryStyleId to set
	 */
	public void setDeliveryStyleId(Integer deliveryStyleId) {
		this.deliveryStyleId = deliveryStyleId;
	}
	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the paymentType
	 */
	public Integer getPaymentType() {
		return paymentType;
	}
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * @return the maxAd
	 */
	public Integer getMaxAd() {
		return maxAd;
	}
	/**
	 * @param maxAd the maxAd to set
	 */
	public void setMaxAd(Integer maxAd) {
		this.maxAd = maxAd;
	}
	/**
	 * @return the hasExactAd
	 */
	public Integer getHasExactAd() {
		return hasExactAd;
	}
	/**
	 * @param hasExactAd the hasExactAd to set
	 */
	public void setHasExactAd(Integer hasExactAd) {
		this.hasExactAd = hasExactAd;
	}
	/**
	 * @return the deleted
	 */
	public String getDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	
	
}
