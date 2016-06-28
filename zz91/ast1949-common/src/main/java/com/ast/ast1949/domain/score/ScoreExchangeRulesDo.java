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
public class ScoreExchangeRulesDo extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rulesCode;
	private String name;
	private Integer score;
	private Integer scoreMax;
	private Integer cycleDay;
	private String remark;
	private Date gmtCreated;
	private Date gmtModified;
	/**
	 * 
	 */
	public ScoreExchangeRulesDo() {
	}
	/**
	 * @param rulesCode
	 * @param name
	 * @param score
	 * @param scoreMax
	 * @param cycleDay
	 * @param remark
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public ScoreExchangeRulesDo(String rulesCode, String name, Integer score,
			Integer scoreMax, Integer cycleDay, String remark, Date gmtCreated,
			Date gmtModified) {
		super();
		this.rulesCode = rulesCode;
		this.name = name;
		this.score = score;
		this.scoreMax = scoreMax;
		this.cycleDay = cycleDay;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the rulesCode
	 */
	public String getRulesCode() {
		return rulesCode;
	}
	/**
	 * @param rulesCode the rulesCode to set
	 */
	public void setRulesCode(String rulesCode) {
		this.rulesCode = rulesCode;
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
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * @return the scoreMax
	 */
	public Integer getScoreMax() {
		return scoreMax;
	}
	/**
	 * @param scoreMax the scoreMax to set
	 */
	public void setScoreMax(Integer scoreMax) {
		this.scoreMax = scoreMax;
	}
	/**
	 * @return the cycleDay
	 */
	public Integer getCycleDay() {
		return cycleDay;
	}
	/**
	 * @param cycleDay the cycleDay to set
	 */
	public void setCycleDay(Integer cycleDay) {
		this.cycleDay = cycleDay;
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
