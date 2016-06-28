/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * 广告
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdBooking implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer positionId;
	private Integer crmId;
	private String account;
	private String keywords;
	private String email;
	private Date gmtBooking;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
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
	 * @return the crmId
	 */
	public Integer getCrmId() {
		return crmId;
	}
	/**
	 * @param crmId the crmId to set
	 */
	public void setCrmId(Integer crmId) {
		this.crmId = crmId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the gmtBooking
	 */
	public Date getGmtBooking() {
		return gmtBooking;
	}
	/**
	 * @param gmtBooking the gmtBooking to set
	 */
	public void setGmtBooking(Date gmtBooking) {
		this.gmtBooking = gmtBooking;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
