/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * 广告位
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdPosition implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;//广告位名称
	private Integer parentId;//上级广告位ID
	private String requestUrl;//广告位所在的URL
	private Integer deliveryStyleId;//投放（显示）方式
	private Integer sequence;//排序，同级（同一个parent_id）排序是优先使用排序
	private Integer paymentType;//广告付费类型 0：按时间段付费；1：按点击付费；2：按有效点击付费；3：按印象付费(每千次)
	private Integer width;//广告位长度
	private Integer height;//广告位宽度
	private Integer maxAd;//同时显示的最大广告数,默认为1。
	private Integer  hasExactAd;//是否为精确投放广告位
	private Date gmtCreated;
	private Date gmtModified;
	private String deleted;//标记删除(Y/N) N：正常（默认）;Y：已删除.
	
	public AdPosition() {
		super();
	}
	
	public AdPosition(Integer id, String name, Integer parentId, String requestUrl,
			Integer deliveryStyleId, Integer sequence, Integer paymentType, Integer width,
			Integer height, Integer maxAd, Integer hasExactAd, Date gmtCreated, Date gmtModified,
			String deleted) {
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
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
		this.deleted = deleted;
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
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
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
