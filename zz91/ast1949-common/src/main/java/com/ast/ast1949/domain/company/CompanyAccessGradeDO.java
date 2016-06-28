/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-22 by liulei.
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * @author liulei
 *
 */
public class CompanyAccessGradeDO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;  				//主键
	private Integer companyId;			//公司ID
	private String account;			//登录帐号
	private String reason;				//加入黑名单原因
	private String accessGradCode;		//用户访问等级
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
	
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
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the accessGradCode
	 */
	public String getAccessGradCode() {
		return accessGradCode;
	}
	/**
	 * @param accessGradCode the accessGradCode to set
	 */
	public void setAccessGradCode(String accessGradCode) {
		this.accessGradCode = accessGradCode;
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