/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-26
 */
package com.ast.ast1949.domain.credit;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author yuyonghui
 *
 */
public class CreditIntegralDetailsDo extends DomainSupport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String operationKey;
	private Integer relatedId;
	private Integer integral;
	private Integer companyId;
	private String account;
	private Date gmtCreated;
	private Date gmtModified;
	
	/**
	 * 
	 */
	public CreditIntegralDetailsDo() {
		super();
	}
	/**
	 * @param operationKey
	 * @param relatedId
	 * @param integral
	 * @param companyId
	 * @param account
	 * @param gmtCreated
	 * @param gmtModified
	 */
	public CreditIntegralDetailsDo(String operationKey, Integer relatedId,
			Integer integral, Integer companyId, String account,
			Date gmtCreated, Date gmtModified) {
		super();
		this.operationKey = operationKey;
		this.relatedId = relatedId;
		this.integral = integral;
		this.companyId = companyId;
		this.account = account;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	/**
	 * @return the operationKey
	 */
	public String getOperationKey() {
		return operationKey;
	}
	/**
	 * @param operationKey the operationKey to set
	 */
	public void setOperationKey(String operationKey) {
		this.operationKey = operationKey;
	}
	/**
	 * @return the relatedId
	 */
	public Integer getRelatedId() {
		return relatedId;
	}
	/**
	 * @param relatedId the relatedId to set
	 */
	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}
	/**
	 * @return the integral
	 */
	public Integer getIntegral() {
		return integral;
	}
	/**
	 * @param integral the integral to set
	 */
	public void setIntegral(Integer integral) {
		this.integral = integral;
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
