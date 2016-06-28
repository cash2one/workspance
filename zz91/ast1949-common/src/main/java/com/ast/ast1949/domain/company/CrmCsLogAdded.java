/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public class CrmCsLogAdded extends DomainSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer crmCsLogId;
	private String csAccount;
	private String content;
	private Date gmtCreated;
	private Date gmtModified;
	
	public CrmCsLogAdded() {
		super();
	}

	public CrmCsLogAdded(Integer crmCsLogId, String csAccount, String content,
			Date gmtCreated, Date gmtModified) {
		super();
		this.crmCsLogId = crmCsLogId;
		this.csAccount = csAccount;
		this.content = content;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Integer getCrmCsLogId() {
		return crmCsLogId;
	}
	public void setCrmCsLogId(Integer crmCsLogId) {
		this.crmCsLogId = crmCsLogId;
	}
	public String getCsAccount() {
		return csAccount;
	}
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
}
