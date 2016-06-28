/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.domain.score;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-1
 */
public class ScoreConversionHistoryDo extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyId;
	private Integer scoreGoodsId;
	private String conversionCategory;
	private String companyName;
	private String contactName;
	private String phone;
	private String tel;
	private String email;
	private String keywords;
	private String status;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	/**
	 * 
	 */
	public ScoreConversionHistoryDo() {
	}
	/**
	 * @param companyId
	 * @param scoreGoodsId
	 * @param conversionCategory
	 * @param companyName
	 * @param contactName
	 * @param phone
	 * @param tel
	 * @param email
	 * @param keywords
	 * @param status
	 * @param remark
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public ScoreConversionHistoryDo(Integer companyId, Integer scoreGoodsId,
			String conversionCategory, String companyName, String contactName,
			String phone, String tel, String email, String keywords,
			String status, String remark, Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.scoreGoodsId = scoreGoodsId;
		this.conversionCategory = conversionCategory;
		this.companyName = companyName;
		this.contactName = contactName;
		this.phone = phone;
		this.tel = tel;
		this.email = email;
		this.keywords = keywords;
		this.status = status;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the scoreGoodsId
	 */
	public Integer getScoreGoodsId() {
		return scoreGoodsId;
	}
	/**
	 * @param scoreGoodsId the scoreGoodsId to set
	 */
	public void setScoreGoodsId(Integer scoreGoodsId) {
		this.scoreGoodsId = scoreGoodsId;
	}
	/**
	 * @return the conversionCategory
	 */
	public String getConversionCategory() {
		return conversionCategory;
	}
	/**
	 * @param conversionCategory the conversionCategory to set
	 */
	public void setConversionCategory(String conversionCategory) {
		this.conversionCategory = conversionCategory;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
