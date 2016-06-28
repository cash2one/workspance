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
 *         created on 2011-3-1
 */
public class ScoreChangeDetailsDo extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4007360663336787744L;

	private Integer companyId;
	private String name;
	private String rulesCode;
	private Integer score;
	private Integer relatedId;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;

	/**
	 * 
	 */
	public ScoreChangeDetailsDo() {
	}

	

	/**
	 * @param companyId
	 * @param name
	 * @param rulesCode
	 * @param score
	 * @param relatedId
	 * @param remark
	 */
	public ScoreChangeDetailsDo(Integer companyId, String name,
			String rulesCode, Integer score, Integer relatedId, String remark) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.rulesCode = rulesCode;
		this.score = score;
		this.relatedId = relatedId;
		this.remark = remark;
	}


	/**
	 * @param companyId
	 * @param name
	 * @param ruleCode
	 * @param score
	 * @param relatedId
	 * @param remark
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public ScoreChangeDetailsDo(Integer companyId, String name,
			String rulesCode, Integer score, Integer relatedId, String remark,
			Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.rulesCode = rulesCode;
		this.score = score;
		this.relatedId = relatedId;
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
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @return the rulesCode
	 */
	public String getRulesCode() {
		return rulesCode;
	}

	/**
	 * @param rulesCode
	 *            the rulesCode to set
	 */
	public void setRulesCode(String rulesCode) {
		this.rulesCode = rulesCode;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return the relatedId
	 */
	public Integer getRelatedId() {
		return relatedId;
	}

	/**
	 * @param relatedId
	 *            the relatedId to set
	 */
	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
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
	 * @param gmtCreated
	 *            the gmtCreated to set
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
	 * @param gmtModified
	 *            the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
