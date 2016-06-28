/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class PositionExactType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer adPositionId;
	private Integer exactTypeId;
	private Date gmtCreated;
	private Date gmtModified;
	
	public PositionExactType() {
		super();
	}

	/**
	 * @param id
	 * @param adPositionId
	 * @param exactTypeId
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public PositionExactType(Integer id, Integer adPositionId,
			Integer exactTypeId, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.adPositionId = adPositionId;
		this.exactTypeId = exactTypeId;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
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

	 
}
