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
public class AdLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer adId;
	private Integer positionId;
	private Integer hitType;  //0:广告拉取，1:广告点击
	private Integer clickType;  //0:第一次点击，1:重复点击 广告点击时才有
	private String ip;  //拉取或点击的IP地址
	private Long time;  //点击时间截
	
	
	/**
	 * @param adId
	 * @param positionId
	 * @param hitType
	 * @param clickType
	 * @param ip
	 * @param time
	 */
	public AdLog(Integer adId, Integer positionId, Integer hitType,
			Integer clickType, String ip, Long time) {
		super();
		this.adId = adId;
		this.positionId = positionId;
		this.hitType = hitType;
		this.clickType = clickType;
		this.ip = ip;
		this.time = time;
	}
	/**
	 * 
	 */
	public AdLog() {
		super();
	}
	/**
	 * @return the adId
	 */
	public Integer getAdId() {
		return adId;
	}
	/**
	 * @param adId the adId to set
	 */
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	/**
	 * @return the positionId
	 */
	public Integer getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	/**
	 * @return the hitType
	 */
	public Integer getHitType() {
		return hitType;
	}
	/**
	 * @param hitType the hitType to set
	 */
	public void setHitType(Integer hitType) {
		this.hitType = hitType;
	}
	/**
	 * @return the clickType
	 */
	public Integer getClickType() {
		return clickType;
	}
	/**
	 * @param clickType the clickType to set
	 */
	public void setClickType(Integer clickType) {
		this.clickType = clickType;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}
	
}
