/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.dto.ad;

import java.util.Date;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class AdSearchDto implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;

	private Date planEndFrom;
	private Date planEndTo;
	private Date startFrom;
	private Date startTo;

	private String anchorPoint;
	private Integer exactTypeId;
	private Integer isNoEnd;
	private Integer checkStatus;

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the planEndFrom
	 */
	public Date getPlanEndFrom() {
		return planEndFrom;
	}

	/**
	 * @param planEndFrom
	 *            the planEndFrom to set
	 */
	public void setPlanEndFrom(Date planEndFrom) {
		this.planEndFrom = planEndFrom;
	}

	/**
	 * @return the planEndTo
	 */
	public Date getPlanEndTo() {
		return planEndTo;
	}

	/**
	 * @param planEndTo
	 *            the planEndTo to set
	 */
	public void setPlanEndTo(Date planEndTo) {
		this.planEndTo = planEndTo;
	}

	/**
	 * @return the startFrom
	 */
	public Date getStartFrom() {
		return startFrom;
	}

	/**
	 * @param startFrom
	 *            the startFrom to set
	 */
	public void setStartFrom(Date startFrom) {
		this.startFrom = startFrom;
	}

	/**
	 * @return the startTo
	 */
	public Date getStartTo() {
		return startTo;
	}

	/**
	 * @param startTo
	 *            the startTo to set
	 */
	public void setStartTo(Date startTo) {
		this.startTo = startTo;
	}

	/**
	 * @return the anchorPoint
	 */
	public String getAnchorPoint() {
		return anchorPoint;
	}

	/**
	 * @param anchorPoint
	 *            the anchorPoint to set
	 */
	public void setAnchorPoint(String anchorPoint) {
		this.anchorPoint = anchorPoint;
	}

	/**
	 * @return the exactTypeId
	 */
	public Integer getExactTypeId() {
		return exactTypeId;
	}

	/**
	 * @param exactTypeId
	 *            the exactTypeId to set
	 */
	public void setExactTypeId(Integer exactTypeId) {
		this.exactTypeId = exactTypeId;
	}

	/**
	 * @return the isNoEnd
	 */
	public Integer getIsNoEnd() {
		return isNoEnd;
	}

	/**
	 * @param isNoEnd
	 *            the isNoEnd to set
	 */
	public void setIsNoEnd(Integer isNoEnd) {
		this.isNoEnd = isNoEnd;
	}

}
