/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.domain.analysis;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
public class AnalysisOptNumDaily extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1369848738526941604L;
	
	private Integer companyId;
	private String account;
	private String categoryCode;
	private Integer optNum;
	private Date gmtDaily;
	private Date gmtCreated;
	private Date gmtModified;
	
	/**
	 * @param companyId
	 * @param account
	 * @param categoryCode
	 * @param optNum
	 * @param gmtDaily
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public AnalysisOptNumDaily(Integer companyId, String account,
			String categoryCode, Integer optNum, Date gmtDaily,
			Date gmtCreated, Date gmtModified) {
		super();
		this.companyId = companyId;
		this.account = account;
		this.categoryCode = categoryCode;
		this.optNum = optNum;
		this.gmtDaily = gmtDaily;
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
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the optNum
	 */
	public Integer getOptNum() {
		return optNum;
	}

	/**
	 * @param optNum the optNum to set
	 */
	public void setOptNum(Integer optNum) {
		this.optNum = optNum;
	}

	/**
	 * @return the gmtDaily
	 */
	public Date getGmtDaily() {
		return gmtDaily;
	}

	/**
	 * @param gmtDaily the gmtDaily to set
	 */
	public void setGmtDaily(Date gmtDaily) {
		this.gmtDaily = gmtDaily;
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
