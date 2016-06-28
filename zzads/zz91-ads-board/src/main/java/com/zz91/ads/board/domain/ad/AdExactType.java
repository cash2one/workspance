/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

/**
 * 定位精确广告的详细信息
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdExactType implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer adPositionId;
	private Integer adId;//要精确定位的广告信息
	private Integer exactTypeId;//精确定位的类型
	private String anchorPoint;//锚点（定位条件）
	
	public AdExactType() {
		super();
	}
	
	public AdExactType(Integer id, Integer adId, Integer exactTypeId, String anchorPoint) {
		super();
		this.id = id;
		this.adId = adId;
		this.exactTypeId = exactTypeId;
		this.anchorPoint = anchorPoint;
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
	 * @return the exactTypeId
	 */
	public Integer getExactTypeId() {
		return exactTypeId;
	}
	/**
	 * @param exactTypeId the exactTypeId to set
	 */
	public void setExactTypeId(Integer exactTypeId) {
		this.exactTypeId = exactTypeId;
	}
	/**
	 * @return the anchorPoint
	 */
	public String getAnchorPoint() {
		return anchorPoint;
	}
	/**
	 * @param anchorPoint the anchorPoint to set
	 */
	public void setAnchorPoint(String anchorPoint) {
		this.anchorPoint = anchorPoint;
	}

	/**
	 * @return the adPositionId
	 */
	public Integer getAdPositionId() {
		return adPositionId;
	}

	/**
	 * @param adPositionId the adPositionId to set
	 */
	public void setAdPositionId(Integer adPositionId) {
		this.adPositionId = adPositionId;
	}
	
	
}
